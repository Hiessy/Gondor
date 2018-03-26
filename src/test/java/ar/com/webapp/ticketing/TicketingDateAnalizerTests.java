package ar.com.webapp.ticketing;

import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.TicketDateAnalizer;
import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TicketingDateAnalizerTests {

    private final static int MAX_LIFE = 5;
    private List<Ticket> workTickets = new ArrayList<Ticket>();

    @Before
    public void createTicekts() {

        LocalDateTime createdDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-10);
        LocalDateTime modifedDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-6);
        //String id, String owner, String creator, String area, String priority, String description, String status, LocalDate createdDate, LocalDate modifiedDate, List<Comment> ticketComment
        List<Comment> workTicketComments = new ArrayList<Comment>();

        //String id, String name, String password
       // User user = new User("u584110", "Martin","test","User", "ACTIVE");
        //String id, Author author, Date posted, String text

        Comment workTicketComment = new Comment(100110001L, "u584110", modifedDate, "This is a text in a comment");
        workTicketComments.add(workTicketComment);


      //  workTickets.add(new Ticket(10011L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, modifedDate, workTicketComments));
      //  workTickets.add(new Ticket(10011L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-4), workTicketComments));
        /*workTickets.add(new Ticket("AMB10011", "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-4), workTicketComments));
        workTickets.add(new Ticket("AMB10011", "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(6), workTicketComments));
        workTickets.add(new Ticket("AMB10011", "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(6), workTicketComments));
        workTickets.add(new Ticket("AMB10011", "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(6), workTicketComments));
        */

    }

    @Test
    public void testTicketDateAnalizer() {
        TicketDateAnalizer.checkLastUpdate(MAX_LIFE, workTickets);
        Assert.assertEquals("CLOSED TIMEOUT", workTickets.get(0).getStatus());
        Assert.assertEquals("IN PROGRESS", workTickets.get(1).getStatus());
    }

    @Test
    public void testTicketDateLastModifiedGreaterThanToday(){

    }

    @Test
    public void testTicketCreatedDateGreaterGreaterThanToday(){

    }
    @Test
    public void testTicketCreatedDateGreaterThanModifiedDate(){

    }

    @Test
    public void testAddingNewCommentChangesModifiedDate(){

    }

    @Test
    public void testAddingNewReplyCommentChangesModifiedDate(){

    }
    @Test
    public void testAddingNewReplyToReplyCommentChangesModifiedDate(){

    }
}
