package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.core.repositories.TicketRepository;
import ar.com.webapp.ticketing.core.repositories.UserRepository;
import ar.com.webapp.ticketing.domain.service.UserService;
import ar.com.webapp.ticketing.domain.service.exception.UserDoesNotExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserIsInactiveException;
import ar.com.webapp.ticketing.domain.service.utils.TicketList;
import ar.com.webapp.ticketing.domain.service.utils.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public User createUser(User user) {
        User storedUser = userRepository.findUserByUserName(user.getUserName());

        if (storedUser != null)
            throw new UserExistsException();

        user.setStatus("ACTIVE");
        return userRepository.createUser(user);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public User updateUser(String userName, User newUser) {

        User storedUser = userRepository.findUserByUserName(userName);
        if (storedUser == null)
            throw new UserDoesNotExistsException();

        if(("INACTIVE").equals(storedUser.getArea()) && !("ACTIVE").equals(newUser.getArea()))
            throw new UserIsInactiveException();

        return userRepository.updateUser(updateStoredUser(storedUser,newUser));
    }

    @Override
    public User deleteUser(String userName) {
        User storedUser = userRepository.findUserByUserName(userName);

        if (storedUser == null)
            throw new UserDoesNotExistsException();

        if(storedUser.getStatus().equals("INACTIVE"))
            throw new UserIsInactiveException();

        storedUser.setStatus("INACTIVE");
       return userRepository.updateUser(storedUser);
    }

    @Override
    public UserList findAllUsers() {
        return new UserList(userRepository.findAll());
    }

    @Override
    public TicketList getAllTicketsforUser(String userName) {
        return new TicketList(ticketRepository.getAllTicketsByUserName(userName));
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        //userRepository()
        ticket.setOwner("UNASSIGNED");
        ticket.setStatus("UNASSIGNED");
        ticket.setCreated(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        ticket.setModified(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return ticketRepository.createTicket(ticket);
    }

    private User updateStoredUser(User storedUser, User newUser){

        if(newUser.getArea() != null)
            storedUser.setArea(newUser.getArea());
        if(newUser.getName() != null)
            storedUser.setName(newUser.getName());
        if(newUser.getPassword() != null)
            storedUser.setPassword(newUser.getPassword());
        if(newUser.getProfile() != null)
            storedUser.setProfile(newUser.getProfile());

        return storedUser;
    }
}
