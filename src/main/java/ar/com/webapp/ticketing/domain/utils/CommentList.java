package ar.com.webapp.ticketing.domain.utils;


import ar.com.webapp.ticketing.core.model.entities.Comment;

import java.util.List;

public class CommentList {

    private List<Comment> comments;
    private Long ticketId;

    public CommentList(List<Comment> comments, Long ticketId) {
        this.comments = comments;
        this.ticketId = ticketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
