package ar.com.webapp.ticketing.core.repositories;

import ar.com.webapp.ticketing.core.model.entities.Comment;

public interface CommentRepository {

    Comment createComment(Comment data);
}
