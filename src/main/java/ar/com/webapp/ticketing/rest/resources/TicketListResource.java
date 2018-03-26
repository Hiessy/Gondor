package ar.com.webapp.ticketing.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class TicketListResource extends ResourceSupport {

    private List<TicketResource> tickets = new ArrayList<TicketResource>();

    public List<TicketResource> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResource> TicketResource) {
        this.tickets = TicketResource;
    }
}
