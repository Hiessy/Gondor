package ar.com.webapp.ticketing.core.repositories.jpa;

import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.core.repositories.TaskRepository;
import ar.com.webapp.ticketing.domain.utils.TaskList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaTaskRepository implements TaskRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Task createTask(Task data) {
         em.persist(data);
        return data;
    }

    @Override
    public List<Task> findAllTasksForTicketId(Long ticketId) {
        Query query = em.createQuery("SELECT b FROM Task b WHERE b.ticket.id=?1");
        query.setParameter(1, ticketId);
        return query.getResultList();
    }

    @Override
    public List<Task> findAllTasksForUser(String userName) {
        Query query = em.createQuery("SELECT b FROM Task b WHERE b.owner=?1");
        query.setParameter(1, userName);
        return query.getResultList();
    }

    @Override
    public Task findTask(Long taskId) {
        return em.find(Task.class, taskId);
    }

    @Override
    public Task deleteTask(Long taskId) {
        Task entry = em.find(Task.class, taskId);
        em.remove(entry);
        return entry;
    }

    @Override
    public Task updateTask(Task data) {
        em.merge(data);
        return data;
    }

    @Override
    public List<Task> findAllTasksByDescription(String description) {
        Query query = em.createQuery("SELECT b from Task b where b.description like ?1");
        query.setParameter(1, "%"+description+"%");
        return query.getResultList();
    }

    @Override
    public List<Task> findAllTasks() {
        Query query = em.createQuery("SELECT b from Task b");
        return query.getResultList();
    }
}
