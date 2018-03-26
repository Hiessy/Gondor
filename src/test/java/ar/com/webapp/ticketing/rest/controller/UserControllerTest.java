package ar.com.webapp.ticketing.rest.controller;

import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<User> userCaptor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        userCaptor = ArgumentCaptor.forClass(User.class);

    }

    @Test
    public void findAllTicketForUser() throws Exception {

        List<Ticket> workTickets = new ArrayList<Ticket>();
        LocalDateTime createdDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-10);
        LocalDateTime modifedDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-6);

        List<Comment> workTicketComments = new ArrayList<Comment>();
        Comment workTicketComment = new Comment(1001L, "u584110", modifedDate, "This is a text in a comment");
        workTicketComments.add(workTicketComment);
       /* workTickets.add(new Ticket(101L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, modifedDate, workTicketComments));
        workTickets.add(new Ticket(102L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-4), workTicketComments));
        workTickets.add(new Ticket(102L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-4), workTicketComments));
        workTickets.add(new Ticket(102L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-4), workTicketComments));
        workTickets.add(new Ticket(102L, "Martin", "Juan", "Ambientes", "Alta", "Se solicita limpieza de baños", "IN PROGRESS", createdDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-4), workTicketComments));
*/
    }

    //findAllTicketForNonExistingUser

    //createBlogExistingUser

    //createBlogNonExistingUser

    //createBlogExistingBlogName

    //createUserNonExistingUsername

    //createUserExistingUsername

    //getExistingUser

    //getNonExistingUser

    //findAllUsers

    //findUsersByName
}
