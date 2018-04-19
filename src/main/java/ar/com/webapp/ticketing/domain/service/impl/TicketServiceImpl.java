package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import ar.com.webapp.ticketing.core.repositories.TaskRepository;
import ar.com.webapp.ticketing.core.repositories.TicketRepository;
import ar.com.webapp.ticketing.core.repositories.UserRepository;
import ar.com.webapp.ticketing.domain.service.TicketService;
import ar.com.webapp.ticketing.domain.service.exception.AreaDoesNotExistException;
import ar.com.webapp.ticketing.domain.service.exception.TicketDoesNotExistException;
import ar.com.webapp.ticketing.domain.service.exception.UserDoesNotExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserIsNotOwnerException;
import ar.com.webapp.ticketing.domain.utils.CommentList;
import ar.com.webapp.ticketing.domain.utils.TaskList;
import ar.com.webapp.ticketing.domain.utils.TicketList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Comment createComment(Long ticketId, Comment data) {
        Ticket storedTicket = ticketRepository.findTicket(ticketId);
        if (storedTicket == null)
            throw new TicketDoesNotExistException();

        return commentRepository.createComment(data);
    }

    @Override
    public TicketList findAllTickets() {
        return new TicketList(ticketRepository.findAllTickets());
    }

    @Override
    public TicketList findAllTicketsByArea(String area) {
        TicketList ticketList = new TicketList(ticketRepository.findTicketsByArea(area));
        if (ticketList == null)
            throw new AreaDoesNotExistException("The area " + area + "does not exist");

        return ticketList;
    }

    @Override
    public Ticket findTicket(Long ticketId) {
        return ticketRepository.findTicket(ticketId);
    }

    @Override
    public Ticket updateTicket(Long ticketId, Ticket newTicket) throws TicketDoesNotExistException {

        Ticket storedTicket = ticketRepository.findTicket(ticketId);
        if (storedTicket == null)
            throw new TicketDoesNotExistException();

        if (storedTicket.getOwner().equals("UNASSIGNED") || storedTicket.getOwner().equals(newTicket.getOwner()))
            return ticketRepository.updateTicket(updateStoredTicket(storedTicket, newTicket));
        else
            throw new UserIsNotOwnerException();
    }

    @Override
    public TaskList addNewTask(Long ticketId, List<Task> tasks) {

        Ticket ticket = ticketRepository.findTicket(ticketId);


        if (ticket == null)
            throw new TicketDoesNotExistException();

        TaskList taskList = new TaskList(new ArrayList<Task>(), ticketId);
        for (Task task : tasks) {
            task.setCreated(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            task.setUpdate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            task.setStatus("OPEN");
            task.setOwner("UNASSIGNED");
            task.setTicket(ticket);
            taskList.getTasks().add(taskRepository.createTask(task));
        }

        return taskList;
    }


    @Override
    public TicketList findAllTicketsByTitle(String title) {
        return new TicketList(ticketRepository.findTicketsByTitle(title));
    }

    @Override
    public TicketList findAllTicketsByDescription(String description) {
        return new TicketList(ticketRepository.findTicketsByDescription(description));
    }

    @Override
    public CommentList findAllComments(Long ticketId) {
        Ticket ticket = ticketRepository.findTicket(ticketId);

        if (ticket == null) {
            throw new TicketDoesNotExistException();
        }
        return new CommentList(commentRepository.getAllCommentsForTicketId(ticketId), ticketId);
    }

    @Override
    public Comment addNewComment(Long ticketId, Comment comment, String userName) {
        Ticket ticket = ticketRepository.findTicket(ticketId);
        if (ticket == null)
            throw new TicketDoesNotExistException();

        User user = userRepository.findUserByUserName(userName);

        if(user == null)
            throw new UserDoesNotExistsException();

        comment.setUser(user);
        comment.setPosted(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        comment.setTicket(ticket);

        Comment entry = commentRepository.createComment(comment);

        return entry;
    }

    @Override
    public TaskList findAllTasks(Long ticketId) {
        Ticket ticket = ticketRepository.findTicket(ticketId);

        if (ticket == null) {
            throw new TicketDoesNotExistException();
        }
        return new TaskList(taskRepository.findAllTasksForTicketId(ticketId), ticketId);
    }
    private Ticket updateStoredTicket(Ticket storedTicket, Ticket newTicket) {

        if (storedTicket.getOwner().equals("UNASSIGNED"))
            storedTicket.setOwner(newTicket.getOwner());

        if (newTicket.getStatus() != null)
            storedTicket.setStatus(newTicket.getStatus());

        if (newTicket.getArea() != null)
            storedTicket.setArea(newTicket.getArea());

        if (newTicket.getPriority() != null)
            storedTicket.setPriority(newTicket.getPriority());

        storedTicket.setModified(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return storedTicket;
    }
}
