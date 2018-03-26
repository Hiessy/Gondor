package ar.com.webapp.ticketing;

import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTest {
    public static void main(String[] arg){

        LocalDateTime createdDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-10);
        LocalDateTime modifedDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-6);
        //String id, String owner, String creator, String area, String priority, String description, String status, LocalDate createdDate, LocalDate modifiedDate, List<Comment> ticketComment
        List<Comment> workTicketComments = new ArrayList<Comment>();

        //String id, String name, String password
      //  User author = new User("u584110","Martin","test","User");
        //String id, Author author, Date posted, String text

        Comment workTicketComment = new Comment(100110001L, "u584110",modifedDate,"This is a text in a comment");
        workTicketComments.add(workTicketComment);
      //  Ticket workTicket = new Ticket(10011L, "Martin","Juan","Ambientes","Alta","Se solicita limpieza de baños","En Progreso",createdDate, modifedDate,workTicketComments);

      //  System.out.println("localDateTime : " + workTicket);
    }
}
