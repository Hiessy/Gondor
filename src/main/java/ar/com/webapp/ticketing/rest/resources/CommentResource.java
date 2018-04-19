package ar.com.webapp.ticketing.rest.resources;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResource extends ResourceSupport {

    private Long rid;
    private LocalDateTime posted;
    private String userName;
    private String text;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Comment toComment(){
        return new Comment(rid,posted,text);
    }
}
