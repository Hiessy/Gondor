package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.service.utils.TicketList;
import ar.com.webapp.ticketing.domain.service.utils.UserList;

public interface UserService {

    User createUser(User user);
    User findUserByUserName(String userName);
    User updateUser(String username,User user);
    User deleteUser(String userName);
    UserList findAllUsers();
    TicketList getAllTicketsforUser(String UserName);
    Ticket createTicket(Ticket ticket);

}
