package ar.com.webapp.ticketing.domain.service.utils;

import ar.com.webapp.ticketing.core.model.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketList {
    List<Ticket> tickets = new ArrayList<Ticket>();

    public TicketList(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
