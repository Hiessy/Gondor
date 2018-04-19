package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.domain.utils.TicketList;
import ar.com.webapp.ticketing.rest.controller.TicketController;
import ar.com.webapp.ticketing.rest.resources.TicketListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class TicketListResourceAsm extends ResourceAssemblerSupport<TicketList, TicketListResource> {

    public TicketListResourceAsm() {
        super(TicketController.class, TicketListResource.class);
    }

    @Override
    public TicketListResource toResource(TicketList ticketList) {
        TicketListResource finalResource = new TicketListResource();
        finalResource.setTickets(new TicketResourceAsm().toResources(ticketList.getTickets()));
        return finalResource;
    }
}
