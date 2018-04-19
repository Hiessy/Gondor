package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.domain.utils.CommentList;
import ar.com.webapp.ticketing.domain.utils.UserCommentList;
import ar.com.webapp.ticketing.rest.controller.CommentController;
import ar.com.webapp.ticketing.rest.controller.TicketController;
import ar.com.webapp.ticketing.rest.resources.CommentListResource;
import ar.com.webapp.ticketing.rest.resources.CommentResource;
import ar.com.webapp.ticketing.rest.resources.TaskListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CommentListResourceAsm extends ResourceAssemblerSupport<CommentList, CommentListResource>{

    public CommentListResourceAsm() {
        super(CommentController.class, CommentListResource.class);
    }

    @Override
    public CommentListResource toResource(CommentList commentList) {
        List<CommentResource> listCommentResource = new CommentResourceAsm().toResources(commentList.getComments());
        CommentListResource commentListResource = new CommentListResource();
        commentListResource.setComments(listCommentResource);
        commentListResource.add(linkTo(methodOn(TicketController.class).getAllComments(commentList.getTicketId())).withSelfRel());
        return commentListResource;
    }

    public CommentListResource toResource(UserCommentList userCommentList) {
        CommentListResource finalResource = new CommentListResource();
        finalResource.setComments(new CommentResourceAsm().toResources(userCommentList.getComments()));
        return finalResource;
    }
}
