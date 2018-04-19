package ar.com.webapp.ticketing.core.repositories.jpa;

import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.core.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User findUserByUserName(String userName) {
        Query query = em.createQuery("SELECT a FROM User a WHERE a.userName=?1");
        query.setParameter(1, userName);
        List<User> users = query.getResultList();
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User findUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        Query query = em.createQuery("SELECT a FROM User a");
        return query.getResultList();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        em.merge(user);
        return user;
    }

    @Override
    public List<User> findUsersByName(String name) {
        Query query = em.createQuery("SELECT a FROM User a WHERE a.name=?1");
        query.setParameter(1, name);
        List<User> users = query.getResultList();

        return users;
    }


}
