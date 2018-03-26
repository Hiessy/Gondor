package ar.com.webapp.ticketing.rest.controller;


import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.service.UserService;
import ar.com.webapp.ticketing.domain.service.exception.UserExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserIsInactiveException;
import ar.com.webapp.ticketing.domain.service.utils.TicketList;
import ar.com.webapp.ticketing.domain.service.utils.UserList;
import ar.com.webapp.ticketing.rest.exception.ConflictException;
import ar.com.webapp.ticketing.rest.exception.NotAuthorizedException;
import ar.com.webapp.ticketing.rest.resources.TicketListResource;
import ar.com.webapp.ticketing.rest.resources.TicketResource;
import ar.com.webapp.ticketing.rest.resources.UserListResource;
import ar.com.webapp.ticketing.rest.resources.UserResource;
import ar.com.webapp.ticketing.rest.resources.asm.TicketListResourceAsm;
import ar.com.webapp.ticketing.rest.resources.asm.TicketResourceAsm;
import ar.com.webapp.ticketing.rest.resources.asm.UserListResourceAsm;
import ar.com.webapp.ticketing.rest.resources.asm.UserResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createUser(@RequestBody UserResource sentUser) {
        try {
            User newUser = sentUser.toUser();
            User createdUser = userService.createUser(newUser);
            UserResource resource = new UserResourceAsm().toResource(createdUser);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            throw new ConflictException(e);
        }
    }

    @RequestMapping(value = "/multi",method = RequestMethod.POST)
    public ResponseEntity<UserListResource> createMultipleUser(@RequestBody List<UserResource> sentUsers) {
        try {
            UserList list = new UserList(new ArrayList<User>());

            for(UserResource us : sentUsers){

                list.getUsers().add(userService.createUser(us.toUser()));
            }

            UserListResource resource = new UserListResourceAsm().toResource(list);
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            throw new ConflictException(e);
        }
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(@PathVariable String userName) {
        User retrievedUser = userService.findUserByUserName(userName);
        if (retrievedUser != null) {
            UserResource res = new UserResourceAsm().toResource(retrievedUser);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{userName}/tickets", method = RequestMethod.GET)
    public ResponseEntity<TicketListResource> findAllTickets(@PathVariable String userName) {
        TicketList ticketsList = userService.getAllTicketsforUser(userName);
        TicketListResource res = new TicketListResourceAsm().toResource(ticketsList);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.DELETE)
    public ResponseEntity<UserResource> deactivateUser(@PathVariable String userName) {
        try {
            User deletedUser = userService.deleteUser(userName);
            if (deletedUser != null) {
                UserResource res = new UserResourceAsm().toResource(deletedUser);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(UserIsInactiveException e){
            throw new NotAuthorizedException(e);
        }
    }

    @RequestMapping(value= "{userName}",method = RequestMethod.POST)
    public ResponseEntity<TicketResource> createTicket(@PathVariable String userName, @RequestBody TicketResource ticketResource) {

        Ticket newTicket = ticketResource.toTicket();
        newTicket.setCreator(userName);
        Ticket createdTicket = userService.createTicket(newTicket);
        TicketResource resource = new TicketResourceAsm().toResource(createdTicket);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(resource.getLink("self").getHref()));
        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value="/{userName}",method = RequestMethod.PUT)
    public ResponseEntity<UserResource> changeUser(@PathVariable String userName,@RequestBody UserResource user) {
       try{
           User modifiedUser = userService.updateUser(userName, user.toUser());

        if (modifiedUser != null) {
            UserResource res = new UserResourceAsm().toResource(modifiedUser);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       }catch(UserIsInactiveException e){
           throw new NotAuthorizedException(e);
       }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserListResource> findAllUsers() {
        UserList list = userService.findAllUsers();
        UserListResource res = new UserListResourceAsm().toResource(list);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
