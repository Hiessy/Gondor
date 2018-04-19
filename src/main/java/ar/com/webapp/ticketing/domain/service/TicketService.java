package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.domain.utils.CommentList;
import ar.com.webapp.ticketing.domain.utils.TaskList;
import ar.com.webapp.ticketing.domain.utils.TicketList;

import java.util.List;

public interface TicketService {

    Comment createComment(Long ticketId, Comment data);
    Ticket findTicket(Long ticketId);
    Ticket updateTicket(Long ticketId, Ticket ticket);
    TicketList findAllTickets();
    TicketList findAllTicketsByArea(String area);
    TicketList findAllTicketsByTitle(String title);
    TicketList findAllTicketsByDescription(String description);
    Comment addNewComment(Long ticketId, Comment comment, String userName);
    CommentList findAllComments(Long ticketId);
    TaskList addNewTask(Long ticketId, List<Task> task);
    TaskList findAllTasks(Long ticketId);
}
