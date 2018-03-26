package ar.com.webapp.ticketing.rest.controller;


import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.domain.service.CommentService;
import ar.com.webapp.ticketing.rest.resources.CommentListResource;
import ar.com.webapp.ticketing.rest.resources.CommentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @RequestMapping(value = {"commentId"},method = RequestMethod.GET)
    public ResponseEntity<CommentListResource> getCommentbyId(@PathVariable Long commentID){
        return null;
    }

    @RequestMapping(value = "{commentId}/reply", method = RequestMethod.POST)
    public ResponseEntity<CommentResource> postCommentReplies(@PathVariable Long commentId){
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommentResource> postComment(@RequestBody Comment comment) {
        return null;
    }


}
