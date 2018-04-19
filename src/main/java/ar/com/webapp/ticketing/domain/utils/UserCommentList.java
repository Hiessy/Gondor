package ar.com.webapp.ticketing.domain.utils;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class UserCommentList {

    private List<Comment> comments = new ArrayList<Comment>();
    private String userName;

    public UserCommentList(List<Comment> comments, String userName) {
        this.comments = comments;
        this.userName = userName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> tasks) {
        this.comments = tasks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
