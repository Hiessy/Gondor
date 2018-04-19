package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.domain.utils.CommentList;

public interface CommentService {

    Comment findComment(Long commentId); // Returns the BlogEntry or null if it can't be found
    Comment modifyComment(Long commentId, Comment data);
    CommentList findAllCommentsWithText(String text);
    CommentList findAllComments();
}
