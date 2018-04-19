package ar.com.webapp.ticketing.core.repositories;

import ar.com.webapp.ticketing.core.model.entities.Comment;

import java.util.List;

public interface CommentRepository {

    Comment createComment(Comment data);
    Comment updateComment(Comment data);
    List<Comment> getAllCommentsForTicketId(Long ticketId);
    List<Comment> findAllCommentsForUser(String userName);

    Comment findComment(Long commentId);

    List<Comment> findAllCommentsWithText(String text);

    List<Comment> findAllComments();
}
