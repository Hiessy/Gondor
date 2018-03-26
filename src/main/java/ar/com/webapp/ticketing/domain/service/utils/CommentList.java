package ar.com.webapp.ticketing.domain.service.utils;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentList {

    List<Comment> comments = new ArrayList<Comment>();

    public CommentList(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
