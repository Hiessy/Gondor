package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import ar.com.webapp.ticketing.core.repositories.TaskRepository;
import ar.com.webapp.ticketing.core.repositories.TicketRepository;
import ar.com.webapp.ticketing.core.repositories.UserRepository;
import ar.com.webapp.ticketing.domain.service.UserService;
import ar.com.webapp.ticketing.domain.service.exception.DataValidationException;
import ar.com.webapp.ticketing.domain.service.exception.UserDoesNotExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserIsInactiveException;
import ar.com.webapp.ticketing.domain.utils.TicketList;
import ar.com.webapp.ticketing.domain.utils.UserCommentList;
import ar.com.webapp.ticketing.domain.utils.UserList;
import ar.com.webapp.ticketing.domain.utils.UserTaskList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    /**
     * <p>Method to create a new system user.</p>
     *
     * @param User user data to be created
     * @return USer the information of the user successfully created
     * @throws UserExistsException is the userName has already been taken
     */
    @Override
    public User createUser(User user) {
        User storedUser = userRepository.findUserByUserName(user.getUserName());
        if (storedUser != null) {
            LOGGER.error("The user already exists, either userName or email already exist.");
            throw new UserExistsException();
        }
        user.setStatus("ACTIVE");
        try {
            LOGGER.info("creating a new user with data: " + user.toString() + ".");
            return userRepository.createUser(user);
        } catch (TransactionException e) {
            LOGGER.error("The user was not created since data " + e.getRootCause().getMessage() + ", was not valid.");
            throw new DataValidationException(e.getRootCause().getMessage());
        }

    }

    /**
     * <p>This method will search for specific users by his userName.</p>
     *
     * @param String userName of the desired user.
     * @return User object with the user data.
     */
    @Override
    public User getUserByUserName(String userName) {
        LOGGER.info("Searching for user with userName " + userName + ".");
        return userRepository.findUserByUserName(userName);
    }

    /**
     * <p>This method will retrieve a list of user that have a particular name.</p>
     *
     * @param String name of the user o users being searched
     * @return UserList with the user matching the searched name
     */
    @Override
    public UserList getUsersByName(String name) {
        LOGGER.info("Searching for users with name " + name + ".");
        return new UserList(userRepository.findUsersByName(name));
    }

    /**
     * <p>Method to update the user information of a specific user.</p>
     *
     * @param String userName representing the user being modified
     * @param User   newUser with the data of the particular user.
     * @return User object with all change persisted.
     */
    @Override
    public User updateUser(String userName, User newUser) {
        LOGGER.info("Updating userName " + userName + ", with data " + newUser.toString() + ".");
        User storedUser = userRepository.findUserByUserName(userName);
        if (storedUser == null) {
            LOGGER.error("The user was not found.");
            throw new UserDoesNotExistsException();
        }
        if (("INACTIVE").equals(storedUser.getArea()) && !("ACTIVE").equals(newUser.getArea())) {
            LOGGER.error("The user is inactive, it should be active before applying changes.");
            throw new UserIsInactiveException();
        }
        User updatedUser = userRepository.updateUser(updateStoredUser(storedUser, newUser));
        LOGGER.info("User was updated successfully");
        return updatedUser;
    }

    /**
     * <p>Method to change a users status to inactive.</p>
     *
     * @param String userName associated with the user to be turned off.
     * @return USer object with updated information
     */
    @Override
    public User deleteUser(String userName) {
        LOGGER.info("Making inactive user with userName " + userName);
        User storedUser = userRepository.findUserByUserName(userName);

        if (storedUser == null) {
            LOGGER.error("User not found.");
            throw new UserDoesNotExistsException();
        }
        if (storedUser.getStatus().equals("INACTIVE")) {
            LOGGER.error("User is already inactive.");
            throw new UserIsInactiveException();
        }
        storedUser.setStatus("INACTIVE");
        User inactiveUser = userRepository.updateUser(storedUser);
        LOGGER.info("User has been rendered inactive.");
        return inactiveUser;
    }

    /**
     * <p>Method to retrieve all users in system</p>
     *
     * @return Returns a list of user found in the system.
     */
    @Override
    public UserList getAllUsers() {
        UserList userList = new UserList(userRepository.findAll());
        LOGGER.info("Searching for a list of users in system.");
        return userList;
    }

    /**
     * <p>Method to find all tickets that assigned to a specific userName.</p>
     *
     * @param String userName of the user that has the tickets assigned to him.
     * @return TicketList a list of tickets with the data.
     */
    @Override
    public TicketList getAllTicketsForUser(String userName) {
        LOGGER.info("Retrieving all tickets assigned to a specific userName " + userName + ".");
        if (!"UNASSIGNED".equals(userName))
            if (userRepository.findUserByUserName(userName) == null) {
                LOGGER.error("User was not found.");
                throw new UserDoesNotExistsException();
            }
        List<Ticket> listOfTickets = ticketRepository.getAllTicketsByUserName(userName);
        LOGGER.info("Tickets for userName " + userName + " where found.");
        return new TicketList(listOfTickets);
    }

    /**
     * <p>Method to create a ticket.</p>
     *
     * @param String userName of the user that created the ticket.
     * @param Ticket ticket data with the ticket information
     * @return Ticket object of the newly created ticket.
     */
    @Override
    public Ticket createTicket(String userName, Ticket ticket) {
        LOGGER.info("The user " + userName + "i creating a new ticket with ticket data " + ticket.toString() + ".");
        if (userRepository.findUserByUserName(userName) == null) {
            LOGGER.error("The user was not found");
            throw new UserDoesNotExistsException();
        }
        ticket.setCreator(userName);
        ticket.setOwner("UNASSIGNED");
        ticket.setStatus("OPEN");
        ticket.setCreated(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        ticket.setModified(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Ticket createdTicket = ticketRepository.createTicket(ticket);
        LOGGER.info("New ticket was created successfully.");
        return createdTicket;
    }

    /**
     * <p>Method to retrieve all tasks assigned to a specific user</p>
     * @param String userName of the user assigned the tasks.
     * @return UserTaskList object with all tasks assigned to a user.
     */
    @Override
    public UserTaskList getAllTasksForUser(String userName) {
        LOGGER.info("Searching for all tasks that belong to userName " +userName + ".");
        List<Task> list = taskRepository.findAllTasksForUser(userName);
        UserTaskList userTaskList = new UserTaskList(list, userName);
        LOGGER.info("List of user tasks where found successfully.");
        return userTaskList;
    }

    /**
     * <p>Method to retrieve all comments created for a specific user.</p>
     * @param String userName of the user that posted the comments.
     * @return UserCommentList
     */
    @Override
    public UserCommentList getAllCommentsForUser(String userName) {

        List<Comment> list = commentRepository.findAllCommentsForUser(userName);

        return new UserCommentList(list, userName);

    }

    /**
     * @param storedUser
     * @param newUser
     * @return
     */
    private User updateStoredUser(User storedUser, User newUser) {

        if (newUser.getArea() != null)
            storedUser.setArea(newUser.getArea());
        if (newUser.getName() != null)
            storedUser.setName(newUser.getName());
        if (newUser.getPassword() != null)
            storedUser.setPassword(newUser.getPassword());
        if (newUser.getProfile() != null)
            storedUser.setProfile(newUser.getProfile());

        return storedUser;
    }
}
