package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.utils.*;

public interface UserService {

    User createUser(User user);
    User getUserByUserName(String userName);
    UserList getUsersByName(String name);
    User updateUser(String userName,User user);
    User deleteUser(String userName);
    UserList getAllUsers();
    TicketList getAllTicketsForUser(String userName);
    Ticket createTicket(String userName, Ticket ticket);

    UserTaskList getAllTasksForUser(String userName);
    UserCommentList getAllCommentsForUser(String userName);
}
