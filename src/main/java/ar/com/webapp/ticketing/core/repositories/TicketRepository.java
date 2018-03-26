package ar.com.webapp.ticketing.core.repositories;

import ar.com.webapp.ticketing.core.model.entities.Ticket;

import java.util.List;

public interface TicketRepository {

    Ticket createTicket(Ticket data);
    List<Ticket> findAllTickets();
    Ticket findTicket(String id);
    List<Ticket> findTicketsByArea(String area);
    List<Ticket> getAllTicketsByUserName(String userName);
}
