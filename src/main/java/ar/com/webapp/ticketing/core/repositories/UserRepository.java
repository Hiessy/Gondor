package ar.com.webapp.ticketing.core.repositories;


import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository{

    User createUser(User user);
    User findUserByUserName(String userName);
    User findUser(Long id);
    List<User> findAll();
    User updateUser(User user);
}
