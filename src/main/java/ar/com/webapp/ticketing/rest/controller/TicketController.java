package ar.com.webapp.ticketing.rest.controller;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.service.TicketService;
import ar.com.webapp.ticketing.domain.service.exception.AreaDoesNotExistException;
import ar.com.webapp.ticketing.domain.service.exception.UserExistsException;
import ar.com.webapp.ticketing.domain.service.utils.TicketList;
import ar.com.webapp.ticketing.rest.exception.ConflictException;
import ar.com.webapp.ticketing.rest.exception.NotFoundException;
import ar.com.webapp.ticketing.rest.resources.CommentListResource;
import ar.com.webapp.ticketing.rest.resources.TicketListResource;
import ar.com.webapp.ticketing.rest.resources.TicketResource;
import ar.com.webapp.ticketing.rest.resources.UserResource;
import ar.com.webapp.ticketing.rest.resources.asm.TicketListResourceAsm;
import ar.com.webapp.ticketing.rest.resources.asm.UserResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/tickets")
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @RequestMapping(value = "{area}", method = RequestMethod.GET)
    public ResponseEntity<TicketListResource> getAllTicketsByArea(@PathVariable String area) {
        try{
            TicketList list = ticketService.findAllTicketsbyArea(area);
            TicketListResource res = new TicketListResourceAsm().toResource(list);
            return new ResponseEntity<>(res,HttpStatus.OK);
        }catch(AreaDoesNotExistException e){
            throw new NotFoundException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TicketListResource> getAllTickets() {
        return null;
    }

    @RequestMapping(value = "{ticketId}", method = RequestMethod.PUT)
    public ResponseEntity<TicketResource> modifyTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        return null;
    }

    @RequestMapping(value = {"ticketId"}, method = RequestMethod.GET)
    public ResponseEntity<TicketResource> getTicketById(@PathVariable Long ticketId) {
        return null;
    }

    @RequestMapping(value = "{ticketId}/tasks", method = RequestMethod.GET)
    public ResponseEntity<CommentListResource> getAllCommentForTicket(@PathVariable Long ticketId) {
        return null;

    }



}
