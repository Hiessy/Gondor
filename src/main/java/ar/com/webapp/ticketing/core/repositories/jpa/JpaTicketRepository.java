package ar.com.webapp.ticketing.core.repositories.jpa;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.repositories.TicketRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaTicketRepository implements TicketRepository{


    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Ticket createTicket(Ticket data) {
        em.persist(data);
        return data;
    }

    @Override
    public List<Ticket> findAllTickets() {
        Query query = em.createQuery("SELECT b from Ticket b");
        return query.getResultList();
    }

    @Override
    public Ticket findTicket(String id) {
        return null;
    }

    @Override
    public List<Ticket> findTicketsByArea(String area) {

        Query query = em.createQuery("SELECT b from Ticket b where b.area=?1");
        query.setParameter(1, area);
        return query.getResultList();

    }

    @Override
    public List<Ticket> getAllTicketsByUserName(String userName) {

        Query query = em.createQuery("SELECT b from Ticket b where b.owner=?1");
        query.setParameter(1, userName);
        return query.getResultList();
    }
}
