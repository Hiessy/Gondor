package ar.com.webapp.ticketing.rest.resources;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResource extends ResourceSupport {

    private Long rid;
    private String authorUserName;
    private LocalDateTime posted;
    private String text;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
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


    public Comment toComment(){
        return new Comment(rid,authorUserName,posted,text);
    }
}
