package ar.com.webapp.ticketing.rest.controller;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.domain.service.TicketService;
import ar.com.webapp.ticketing.domain.service.exception.AreaDoesNotExistException;
import ar.com.webapp.ticketing.domain.service.exception.TicketDoesNotExistException;
import ar.com.webapp.ticketing.domain.service.exception.UserIsNotOwnerException;
import ar.com.webapp.ticketing.domain.utils.CommentList;
import ar.com.webapp.ticketing.domain.utils.TaskList;
import ar.com.webapp.ticketing.domain.utils.TicketList;
import ar.com.webapp.ticketing.rest.exception.NotFoundException;
import ar.com.webapp.ticketing.rest.resources.*;
import ar.com.webapp.ticketing.rest.resources.asm.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle ticket request from the view.
 * The controller will communicate with the service layer and through the appropriate Ticket exception
 * in order to fulfil the request
 *
 * @author Martín Díaz
 */

@RestController
@RequestMapping("/v1/tickets")
public class TicketController {

    private final static Logger LOGGER = LogManager.getLogger(TicketController.class);
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * <p>GET method for getting all tickets that have been created. If parameter, title or description, is sent in request
     * then a filtered search for each of these will done.</p>     *
     *
     * @param String title is optional and will result in a query for the match with the title.
     * @param String description is optional and will result in a query for the match with the description.
     * @param String representing de area.
     * @return List of tickets resource wrapped in a Response entity with a standard HTTP code not paginated.
     * @throws AreaDoesNotExistException if there was a search for an area an its result was null.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TicketListResource> getAllTickets(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "description", required = false) String description, @RequestParam(value = "area", required = false) String area) {
        TicketList list = null;

        if (title != null) {
            list = ticketService.findAllTicketsByTitle(title);
            LOGGER.info("Searching for all the tickets that have the title: " + title + ".");
        } else if (description != null) {
            list = ticketService.findAllTicketsByDescription(description);
            LOGGER.info("Searching for all the tickets that have the description: " + description + ".");
        } else if (area != null) {
            list = ticketService.findAllTicketsByArea(area);
            LOGGER.info("Searching for all the tickets that have the description: " + area + ".");
        } else {
            list = ticketService.findAllTickets();
            LOGGER.info("Searching for all the tickets");
        }

        if (list == null) {
            LOGGER.error("No tickets where found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Tickets where found successfully.");
        TicketListResource res = new TicketListResourceAsm().toResource(list);
        return new ResponseEntity<>(res, HttpStatus.OK);


    }

    /**
     * <p>PUT method modify a ticket. The ticketId in the path is applied just to following the convention. The actual
     * information will be taken from the ticket entity. </p>
     *
     * @param Long   the unique identifier of the ticket object.
     * @param Ticket entity with the new information to overwrite the existing data.
     * @return Ticket resource with newly modified data wrapped in the responseEntity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{ticketId}", method = RequestMethod.PUT)
    public ResponseEntity<TicketResource> modifyTicket(@PathVariable Long ticketId, @RequestBody TicketResource ticket) {
        LOGGER.info("The ticket id: " + ticketId.toString() + ", is being updated with data: " + ticket.toString() + ".");
        try {
            Ticket modifiedTicket = ticketService.updateTicket(ticketId, ticket.toTicket());
            if (modifiedTicket != null) {
                LOGGER.info("The ticket was modified successfully.");
                TicketResource res = new TicketResourceAsm().toResource(modifiedTicket);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                LOGGER.error("The ticket id: " + ticketId.toString() + ", was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (UserIsNotOwnerException e) {
            LOGGER.error("The user is the owner, and is not authorized to make changes.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * <p>GET method to retrieve the ticket searching it by ID.</p>
     *
     * @param Long the unique identifier of the ticket object.
     * @return Ticket resource wrapped in the responseEntity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    public ResponseEntity<TicketResource> getTicketById(@PathVariable Long ticketId) {
        LOGGER.info("Retrieving the ticket id: " + ticketId.toString() + ".");
        Ticket retrievedTicket = ticketService.findTicket(ticketId);
        if (retrievedTicket != null) {
            LOGGER.info("The ticket was found successfully.");
            TicketResource res = new TicketResourceAsm().toResource(retrievedTicket);
            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {
            LOGGER.error("The ticket was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * <p>POST method to create a new Task associated to a specific ticket</p>
     *
     * @param Long             the unique identifier of the ticket object.
     * @param TaskListResource a list of Task resource created
     * @return Task resource wrapped in the responseEntity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{ticketId}/tasks", method = RequestMethod.POST)
    public ResponseEntity<TaskListResource> createNewTask(@PathVariable Long ticketId, @RequestBody TaskListResource tasks) {
        LOGGER.info("Creating a new task for ticket id: " + ticketId.toString() + ", with task data: " + tasks.toString());
        try {
            TaskList taskList = ticketService.addNewTask(ticketId, tasks.toTaskList());
            LOGGER.info("New tasks where created successfully.");
            TaskListResource res = new TaskListResourceAsm().toResource(taskList);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (TicketDoesNotExistException e) {
            LOGGER.info("The ticket does not exist.");
            throw new NotFoundException(e);
        }
    }

    /**
     * <p>GET method all the tasks associated to a specific ticket</p>
     *
     * @param Long the unique identifier of the ticket object.
     * @return List of Task resource wrapped in the responseEntity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{ticketId}/tasks", method = RequestMethod.GET)
    public ResponseEntity<TaskListResource> getAllTask(@PathVariable Long ticketId) {
        LOGGER.info("Retrieving all tasks for ticket id: " + ticketId.toString() + ".");
        Ticket retrievedTicket = ticketService.findTicket(ticketId);
        if (retrievedTicket != null) {
            TaskList taskList = ticketService.findAllTasks(ticketId);
            LOGGER.info("A list of tasks was retrieved successfully.");
            TaskListResource res = new TaskListResourceAsm().toResource(taskList);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            LOGGER.info("The ticket was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * <p>POST method to create a new comment associated to a specific ticket</p>
     *
     * @param Long            the unique identifier of the ticket object.
     * @param CommentResource the data for the new comment
     * @return Comment resource wrapped in the responseEntity with the appropriate HTTP code.
     */
    @RequestMapping(value = "/{ticketId}/comments/{userName}", method = RequestMethod.POST)
    public ResponseEntity<CommentResource> createNewComment(@PathVariable Long ticketId, @RequestBody CommentResource comment, @PathVariable String userName) {
        LOGGER.info("Creating a new comment for ticket id " + ticketId.toString() + ", with comment data " + comment.toString() + ", by userName: " + userName + ".");
        try {
            Comment commentSaved = ticketService.addNewComment(ticketId, comment.toComment(), userName);
            LOGGER.info("Comment was created successfully.");
            CommentResource res = new CommentResourceAsm().toResource(commentSaved);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (TicketDoesNotExistException e) {
            LOGGER.info("The ticket was not found.");
            throw new NotFoundException(e);
        }
    }

    /**
     * <p>Get method to retrie all comments made on a particular ticket</p>
     *
     * @param Long the ticket id.
     * @return List of comments found for the ticket.
     * @throws TicketDoesNotExistException is the ticket was not found in data base.
     */

    @RequestMapping(value = "/{ticketId}/comments", method = RequestMethod.GET)
    public ResponseEntity<CommentListResource> getAllComments(@PathVariable Long ticketId) {
        LOGGER.info("Retrieve all comments for ticket id " + ticketId.toString() + ".");
        try {
            CommentList list = ticketService.findAllComments(ticketId);
            LOGGER.info("All comments where found successfully.");
            CommentListResource res = new CommentListResourceAsm().toResource(list);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (TicketDoesNotExistException e) {
            LOGGER.info("the ticket was not found.");
            throw new NotFoundException(e);
        }

    }
}
