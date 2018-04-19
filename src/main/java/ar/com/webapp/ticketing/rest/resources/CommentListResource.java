package ar.com.webapp.ticketing.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class CommentListResource extends ResourceSupport {

    private List<CommentResource> comments = new ArrayList<CommentResource>();

    public List<CommentResource> getComments() {
        return comments;
    }

    public void setComments(List<CommentResource> comments) {
        this.comments = comments;
    }
}
