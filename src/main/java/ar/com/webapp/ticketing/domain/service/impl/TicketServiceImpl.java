package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.repositories.CommentRepository;
import ar.com.webapp.ticketing.core.repositories.TicketRepository;
import ar.com.webapp.ticketing.domain.service.TicketService;
import ar.com.webapp.ticketing.domain.service.exception.AreaDoesNotExistException;
import ar.com.webapp.ticketing.domain.service.utils.TicketList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(String ticketId, Comment data) {
        return null;
    }

    @Override
    public TicketList findAllTickets() {
        return null;
    }

    @Override
    public TicketList findAllTicketsbyArea(String area) {
        //TODO search areas first to see if it exist
        TicketList ticketList = new TicketList(ticketRepository.findTicketsByArea(area));
        if(ticketList == null)
            throw new AreaDoesNotExistException("The area " + area + "does not exist");

        return ticketList;
    }

    @Override
    public Ticket findTicket(String ticketId) {
        return null;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return null;
    }

    @Override
    public Ticket closeTicket(String ticketId) {
        return null;
    }
}
