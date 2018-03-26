package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.domain.service.utils.CommentList;

public interface CommentService {

    Comment findComment(String commentId); // Returns the BlogEntry or null if it can't be found
    CommentList findCommentReply(String commentId);
    Comment modifyComment(String commentId, Comment data);
}
