package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import ar.com.webapp.ticketing.domain.service.CommentService;
import ar.com.webapp.ticketing.domain.service.utils.CommentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment findComment(String commentId) {
        return null;
    }

    @Override
    public CommentList findCommentReply(String commentId) {
        return null;
    }

    @Override
    public Comment modifyComment(String commentId, Comment data) {
        return null;
    }
}
