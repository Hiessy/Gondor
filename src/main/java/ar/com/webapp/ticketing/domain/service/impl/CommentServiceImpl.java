package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import ar.com.webapp.ticketing.domain.service.CommentService;
import ar.com.webapp.ticketing.domain.utils.CommentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment findComment(Long commentId) {
        return commentRepository.findComment(commentId);
    }

    @Override
    public Comment modifyComment(Long commentId, Comment data) {
        return null;
    }

    @Override
    public CommentList findAllCommentsWithText(String text) {
        return new CommentList(commentRepository.findAllCommentsWithText(text),null);
    }

    @Override
    public CommentList findAllComments() {
        return new CommentList(commentRepository.findAllComments(),null);
    }
}
