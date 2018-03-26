package ar.com.webapp.ticketing.core.repositories.jpa;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCommentRepository implements CommentRepository{
    @Override
    public Comment createComment(Comment data) {
        return null;
    }
}
