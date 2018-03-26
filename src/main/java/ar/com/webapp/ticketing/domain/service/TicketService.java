package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.domain.service.utils.TicketList;

public interface TicketService {

    Comment createComment(String ticketId, Comment data);
    TicketList findAllTickets();
    TicketList findAllTicketsbyArea(String area);
    Ticket findTicket(String ticketId);
    Ticket updateTicket(Ticket ticket);
    Ticket closeTicket(String ticketId);

}
