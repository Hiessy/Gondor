package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.rest.controller.CommentController;
import ar.com.webapp.ticketing.rest.controller.TicketController;
import ar.com.webapp.ticketing.rest.controller.UserController;
import ar.com.webapp.ticketing.rest.resources.CommentResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CommentResourceAsm extends ResourceAssemblerSupport<Comment, CommentResource> {

    public CommentResourceAsm() {
        super(Comment.class, CommentResource.class);
    }

    @Override
    public CommentResource toResource(Comment comment) {

        CommentResource commentResource = new CommentResource();
        commentResource.setRid(comment.getId());
        commentResource.setPosted(comment.getPosted());
        commentResource.setText(comment.getText());
        commentResource.setUserName(comment.getUser().getUserName());
        Link self = linkTo(CommentController.class).slash(comment.getId()).withSelfRel();
        commentResource.add(self);
        if (comment.getTicket() != null)
            commentResource.add(linkTo(TicketController.class).slash(comment.getTicket().getId()).withRel("ticket"));

        if(comment.getUser() != null)
            commentResource.add(linkTo(UserController.class).slash(comment.getUser().getUserName()).withRel("user"));
        return commentResource;
    }
}
