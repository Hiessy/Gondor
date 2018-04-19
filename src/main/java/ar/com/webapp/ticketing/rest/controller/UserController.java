package ar.com.webapp.ticketing.rest.controller;


import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.service.UserService;
import ar.com.webapp.ticketing.domain.service.exception.DataValidationException;
import ar.com.webapp.ticketing.domain.service.exception.UserDoesNotExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserIsInactiveException;
import ar.com.webapp.ticketing.domain.utils.*;
import ar.com.webapp.ticketing.rest.exception.BadRequestException;
import ar.com.webapp.ticketing.rest.exception.ConflictException;
import ar.com.webapp.ticketing.rest.exception.NotAuthorizedException;
import ar.com.webapp.ticketing.rest.exception.NotFoundException;
import ar.com.webapp.ticketing.rest.resources.*;
import ar.com.webapp.ticketing.rest.resources.asm.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to handle user request from the view.
 * The controller will communicate with the service layer through the UserService
 * in order to fulfil the request
 *
 * @author Martín Díaz
 */

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final static Logger LOGGER = LogManager.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>POST method to create a new user, the user will have to be associated to a specific area.
     * Newly created users have two validation instances, they should be validated from the front end.</p>
     *
     * @param UserResource created a by a new user and validated to have all the necessary
     *                     fields.
     * @return UserResource with all the information of the newly created user.
     * @throws ConflictException is the user has already been created.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createUser(@RequestBody UserResource sentUser) {
        LOGGER.info("Creating new user with data: " + sentUser.toString());
        try {
            User newUser = sentUser.toUser();
            User createdUser = userService.createUser(newUser);
            LOGGER.info("User created successfully: " + sentUser.toString());
            UserResource resource = new UserResourceAsm().toResource(createdUser);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            LOGGER.error("Error creating new user, the userName is already taken.");
            throw new ConflictException(e);
        } catch (DataValidationException e) {
            LOGGER.error("Error creating new user, invalid data sent.");
            throw new BadRequestException(e);
        }
    }

    /**
     * <p>POST method to create multiple user, should be used only for testing proposes
     * and will be removed before final release since there is no validation from the front end.
     * Has not been tested since it is only uses to generate a user base for other tests.</p>
     *
     * @param List of UserResource to be added as active users.
     * @return List of added UserResource wrapped in the response entity with the appropriate HTTP code.
     * @throws ConflictException will not add already created user, and after exception all following users will not be created.
     */
    @RequestMapping(value = "/multi", method = RequestMethod.POST)
    public ResponseEntity<UserListResource> createMultipleUser(@RequestBody List<UserResource> sentUsers) {
        LOGGER.info("Creating a list new user with data: " + sentUsers.toString());
        try {
            UserList list = new UserList(new ArrayList<>());
            for (UserResource us : sentUsers) {
                list.getUsers().add(userService.createUser(us.toUser()));
            }
            LOGGER.info("New users where created successfully.");
            UserListResource resource = new UserListResourceAsm().toResource(list);
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            LOGGER.error("New users where not created, at least one user already exists.");
            throw new ConflictException(e);
        }
    }

    /**
     * <p>GET method to retrieve a specific user user the Username created originally.</p>
     *
     * @param String representing the user name that was created originally with the user.
     * @return UserResource wrapped in the response entity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(@PathVariable String userName) {
        LOGGER.info("Retrieving information for username: " + userName);
        User retrievedUser = userService.getUserByUserName(userName);
        if (retrievedUser != null) {
            LOGGER.info("New users where not created, at least one user already exists.");
            UserResource res = new UserResourceAsm().toResource(retrievedUser);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            LOGGER.error("New users where not created, at least one user already exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>GET method to retrieve all the tickets assigned to a specific user by searching with his user name.
     * Logged in user should only be able to search for his tickets, unless profile is admin.</p>
     *
     * @param String representing the user name that was created originally with the user.
     * @return TicketListResource wrapped in the response entity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{userName}/tickets", method = RequestMethod.GET)
    public ResponseEntity<TicketListResource> getAllTicketsForUser(@PathVariable String userName) {
        LOGGER.info("Retrieving all the tickets for user with username: " + userName + ".");
        try {
            TicketList ticketsList = userService.getAllTicketsForUser(userName);
            LOGGER.info("User was found successfully.");
            TicketListResource res = new TicketListResourceAsm().toResource(ticketsList);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserDoesNotExistsException e) {
            LOGGER.error("User was not found.");
            throw new NotFoundException(e);
        }
    }

    /**
     * <p>DELETE method to remove an active user. Should be called only by administrators and will change user active value to false</p>
     *
     * @param String representing the user name that was created originally with the user.
     * @return UserResource with the update value, wrapped in a ResponseEntity with the appropriate HTTP code.
     * @throws NotAuthorizedException if the user is already inactive
     */
    @RequestMapping(value = "/{userName}", method = RequestMethod.DELETE)
    public ResponseEntity<UserResource> deactivateUser(@PathVariable String userName) {
        LOGGER.info("Deleting user with username: " + userName + ".");
        try {
            User deletedUser = userService.deleteUser(userName);
            if (deletedUser != null) {
                LOGGER.info("User deleted successfully.");
                UserResource res = new UserResourceAsm().toResource(deletedUser);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                LOGGER.error("User was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (UserIsInactiveException e) {
            LOGGER.error("You are not authorized to change o delete this user.");
            throw new NotAuthorizedException(e);
        }
    }

    /**
     * <p>POST method to create a new ticket associated to the user that created the ticket the newly created ticket is sent to a queue.</p>
     *
     * @param String         representing the user name of the user creating the ticket.
     * @param TicketResource representing the newly created ticket as the body of the HTTP request
     * @return TicketResource with the complete information wrapped in response entity with the appropriate HTTP code.
     * @throws UserDoesNotExistsException is the user has not been created
     */
    @RequestMapping(value = "{userName}/tickets", method = RequestMethod.POST)
    public ResponseEntity<TicketResource> createTicket(@PathVariable String userName, @RequestBody TicketResource ticketResource) {
        LOGGER.info("UserName, " + userName + ", is creating a new ticket with data : " + ticketResource.toString());
        try {
            Ticket createdTicket = userService.createTicket(userName, ticketResource.toTicket());
            LOGGER.info("Ticket created successfully");
            TicketResource resource = new TicketResourceAsm().toResource(createdTicket);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
        } catch (UserDoesNotExistsException e) {
            LOGGER.error("The user you are creating a ticket for does not exist.");
            throw new NotFoundException(e);
        }


    }

    /**
     * <p>PUT method to modify information about a specific user associated to logged in session. </p>
     *
     * @param String       representing the user name of the user creating the ticket.
     * @param UserResource with the user information to be changed
     * @return UserResource with the updated information wrapped in a response entity with the correct HTTP code.
     * @throws NotAuthorizedException is the modified user is inactive.
     */
    @RequestMapping(value = "/{userName}", method = RequestMethod.PUT)
    public ResponseEntity<UserResource> changeUser(@PathVariable String userName, @RequestBody UserResource user) {
        LOGGER.info("UserName, " + userName + ", is updating user information with the following data " + user.toString() + ".");
        try {
            User modifiedUser = userService.updateUser(userName, user.toUser());
            if (modifiedUser != null) {
                LOGGER.info("User was updated successfully");
                UserResource res = new UserResourceAsm().toResource(modifiedUser);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                LOGGER.error("User not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (UserIsInactiveException e) {
            LOGGER.error("Not authorized to change user information.");
            throw new NotAuthorizedException(e);
        }
    }

    /**
     * <p>GET method to retrive all existing users active or inactive in the system. Used by admin profile, may also search by
     * name, in which case a list of user with specific name will be returns.</p>
     *
     * @param String containing the name of the user to be searched.
     * @return List of UserResources with all existing user wrapped in the response entity with the appropriate HTTP code.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserListResource> getAllUsers(@RequestParam(value = "name", required = false) String name) {

        UserList list = null;
        if (name == null) {
            LOGGER.info("Get all users.");
            list = userService.getAllUsers();

        } else {
            LOGGER.info("Get all users with name: " + name+".");
            list = userService.getUsersByName(name);

        }
        if (list == null){
            LOGGER.error("No users where found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Users where found successfully.");
        UserListResource res = new UserListResourceAsm().toResource(list);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * <p>DELETE method to remove an active user. Should be called only by administrators and will change user active value to false</p>
     *
     * @param String representing the user name that was created originally with the user.
     * @return UserResource with the update value, wrapped in a ResponseEntity with the appropriate HTTP code.
     * @throws NotAuthorizedException if the user is already inactive
     */
    @RequestMapping(value = "/{userName}/tasks", method = RequestMethod.GET)
    public ResponseEntity<TaskListResource> getAllTasksForUser(@PathVariable String userName) {
        LOGGER.info("Retrieve all tasks for userName: "+userName+".");
        try {
            UserTaskList taskList = userService.getAllTasksForUser(userName);
            LOGGER.info("All tasks for userName: "+userName+", where found.");
            TaskListResource res = new TaskListResourceAsm().toResource(taskList);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserDoesNotExistsException e) {
            LOGGER.error("No user where found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>DELETE method to remove an active user. Should be called only by administrators and will change user active value to false</p>
     *
     * @param String representing the user name that was created originally with the user.
     * @return UserResource with the update value, wrapped in a ResponseEntity with the appropriate HTTP code.
     * @throws NotAuthorizedException if the user is already inactive
     */
    @RequestMapping(value = "/{userName}/comments", method = RequestMethod.GET)
    public ResponseEntity<CommentListResource> getAllCommentsForUser(@PathVariable String userName) {
        LOGGER.info("Retrieve all comments for username: " +userName+ ".");
        try {
            UserCommentList commentList = userService.getAllCommentsForUser(userName);
            LOGGER.info("Comments for userName: "+userName+", where found.");
            CommentListResource res = new CommentListResourceAsm().toResource(commentList);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserDoesNotExistsException e) {
            LOGGER.error("No user where found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
