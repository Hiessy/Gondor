package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.domain.service.utils.TicketList;
import ar.com.webapp.ticketing.domain.service.utils.UserList;
import ar.com.webapp.ticketing.rest.controller.TicketController;
import ar.com.webapp.ticketing.rest.controller.UserController;
import ar.com.webapp.ticketing.rest.resources.TicketListResource;
import ar.com.webapp.ticketing.rest.resources.UserListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class TicketListResourceAsm extends ResourceAssemblerSupport<TicketList, TicketListResource> {

    public TicketListResourceAsm() {
        super(TicketController.class, TicketListResource.class);
    }



    @Override
    public TicketListResource toResource(TicketList ticketList) {
        TicketListResource finalResrouce = new TicketListResource();
        finalResrouce.setTickets(new TicketResourceAsm().toResources(ticketList.getTickets()));
        return finalResrouce;
    }
}
