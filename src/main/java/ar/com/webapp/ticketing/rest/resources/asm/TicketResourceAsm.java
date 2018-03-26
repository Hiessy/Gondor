package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.rest.controller.TicketController;
import ar.com.webapp.ticketing.rest.controller.UserController;
import ar.com.webapp.ticketing.rest.resources.TicketResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TicketResourceAsm extends ResourceAssemblerSupport<Ticket, TicketResource> {

    public TicketResourceAsm(){
        super(TicketController.class, TicketResource.class);
    }

    @Override
    public TicketResource toResource(Ticket ticket) {
        TicketResource resource = new TicketResource();
        resource.setRid(ticket.getId());
        resource.setArea(ticket.getArea());
        resource.setOwner(ticket.getOwner());
        resource.setCreator(ticket.getCreator());
        resource.setCreated(ticket.getCreated());
        resource.setTitle(ticket.getTitle());
        resource.setDescription(ticket.getDescription());
        resource.setStatus(ticket.getStatus());
        resource.setModified(ticket.getModified());
        resource.setPriority(ticket.getPriority());
        resource.add(linkTo(TicketController.class).slash(ticket.getId()).withSelfRel());
        resource.add(linkTo(TicketController.class).slash(ticket.getId()).slash("task").withRel("Task"));
        resource.add(linkTo(UserController.class).slash(ticket.getOwner()).withRel("User-Assigned"));
        resource.add(linkTo(UserController.class).slash(ticket.getOwner()).slash("tickets").withRel("User-Tickets"));
        return resource;
    }
}
