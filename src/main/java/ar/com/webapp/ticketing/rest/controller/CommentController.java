package ar.com.webapp.ticketing.rest.controller;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.domain.service.CommentService;
import ar.com.webapp.ticketing.domain.utils.CommentList;
import ar.com.webapp.ticketing.rest.resources.CommentListResource;
import ar.com.webapp.ticketing.rest.resources.CommentResource;
import ar.com.webapp.ticketing.rest.resources.asm.CommentListResourceAsm;
import ar.com.webapp.ticketing.rest.resources.asm.CommentResourceAsm;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle comments request from the view.
 * The controller will communicate with the service layer through the CommentService
 * in order to fulfil the request
 *
 * @author Martín Díaz
 */
@RestController
@RequestMapping("/v1/comments")
public class CommentController {

    private final static Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(TaskController.class);
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    /**
     * <p>GET method to find all comments that have been created. There is an option to user a param with a text
     * in which case a search for the tasks with the text will be retrieved.</p>
     *
     * @param String text is not required
     * @return CommentListResource with all the comments or all the comments with a specific text.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CommentListResource> getAllComments(@RequestParam(value = "text", required = false) String text) {

        CommentList list = null;
        if (text != null) {
            list = commentService.findAllCommentsWithText(text);
            LOGGER.info("Searching for list of comments with text: " + text +".");
        }else {
            list = commentService.findAllComments();
            LOGGER.info("Searching for all comments.");
        }
        if (list == null) {
            LOGGER.error("No comments were found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("A list of comments was found successfully.");
        CommentListResource res = new CommentListResourceAsm().toResource(list);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    /**
     * <p>GET method to find a comment that was created with an id. </p>
     *
     * @param Long commentId
     * @return TaskListResource with all the tasks or all the tasks with a description.
     */
    @RequestMapping(value = {"commentId"}, method = RequestMethod.GET)
    public ResponseEntity<CommentResource> getCommentbyId(@PathVariable Long commentId) {
        LOGGER.info("Searching for a specific comment with id " +commentId.toString() + ".");
        Comment comment = commentService.findComment(commentId);
        if (comment != null) {
            LOGGER.info("Comment was found successfully.");
            CommentResource res = new CommentResourceAsm().toResource(comment);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            LOGGER.error("The comment was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>POST method TODO.</p>
     *
     * @param String text is not required
     * @return TaskListResource with all the tasks or all the tasks with a description.
     */
    @RequestMapping(value = "{commentId}/reply", method = RequestMethod.POST)
    public ResponseEntity<CommentResource> postCommentReplies(@PathVariable Long commentId) {
        LOGGER.error("The post comment replie method is not working.");
        return null;
    }


}
