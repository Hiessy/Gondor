package ar.com.webapp.ticketing.core.repositories;

import ar.com.webapp.ticketing.core.model.entities.Ticket;

import java.util.List;

public interface TicketRepository {

    Ticket createTicket(Ticket data);
    List<Ticket> findAllTickets();
    Ticket findTicket(Long id);
    List<Ticket> findTicketsByArea(String area);
    List<Ticket> findTicketsByTitle(String title);
    List<Ticket> getAllTicketsByUserName(String userName);
    List<Ticket> findTicketsByDescription(String description);
    Ticket updateTicket(Ticket ticket);
}
