package ar.com.webapp.ticketing.core.repositories.jpa;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaCommentRepository implements CommentRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Comment createComment(Comment data) {
        em.persist(data);
        return data;
    }

    @Override
    public Comment updateComment(Comment data) {
        return null;
    }

    @Override
    public List<Comment> getAllCommentsForTicketId(Long ticketId) {
        Query query = em.createQuery("SELECT b FROM Comment b WHERE b.ticket.id=?1");
        query.setParameter(1, ticketId);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAllCommentsForUser(String userName) {
        Query query = em.createQuery("SELECT b FROM Comment b WHERE b.user.userName=?1");
        query.setParameter(1, userName);
        return query.getResultList();
    }

    @Override
    public Comment findComment(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public List<Comment> findAllCommentsWithText(String text) {
        Query query = em.createQuery("SELECT b from Ticket b where b.title like ?1");
        query.setParameter(1, "%"+text+"%");
        return query.getResultList();
    }

    @Override
    public List<Comment> findAllComments() {
        Query query = em.createQuery("SELECT b from Comment b");
        return query.getResultList();
    }
}
