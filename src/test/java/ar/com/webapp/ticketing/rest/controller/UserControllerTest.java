package ar.com.webapp.ticketing.rest.controller;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.domain.service.UserService;
import ar.com.webapp.ticketing.domain.service.exception.UserDoesNotExistsException;
import ar.com.webapp.ticketing.domain.service.exception.UserExistsException;
import ar.com.webapp.ticketing.domain.utils.TicketList;
import ar.com.webapp.ticketing.domain.utils.UserList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private static Logger logger = LogManager.getLogger(UserControllerTest.class);

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void findAllTicketsForUserTest() throws Exception {
        logger.info("Running find all tickets for user Test");
        List<Ticket> list = new ArrayList<>();

        LocalDateTime createdDateA = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-10);
        LocalDateTime modifiedDateA = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-6);
        LocalDateTime createdDateB = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-9);
        LocalDateTime modifiedDateB = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-5);
        Ticket ticketA = new Ticket(1L, "u584110", "u111222", "Medio Ambiente", "ALTA", "Falta de casco para obreros en la obra de Rosario", "Se detectaron 4 obreros sin casco en la obra de Rosario, los mismos dieron como motivo la falta de equipocamiento.",
                "EN PROGRESO", createdDateA, modifiedDateA);
        Ticket ticketB = new Ticket(2L, "u584110", "u111333", "Medio Ambiente", "BAJA", "Incendio en planta quimica", "Exploto la caldera en la planta quimica de Rosario y murieron 3 trabajadores y hay 19 mas atrapados. Por favor llamar a los bomberos.",
                "EN PROGRESO", createdDateB, modifiedDateB);

        list.add(ticketA);
        list.add(ticketB);

        TicketList ticketList = new TicketList(list);

        when(userService.getAllTicketsForUser("u584110")).thenReturn(ticketList);

        mockMvc.perform(get("/v1/users/u584110/tickets"))
                .andDo(print())
                .andExpect(jsonPath("$.tickets[*].owner", hasItems(endsWith("u584110"),endsWith("u584110"))))
                .andExpect(jsonPath("$.tickets[*].creator", hasItems(endsWith("u111222"), endsWith("u111333"))))
                .andExpect(jsonPath("$.tickets[*].area", hasItems(endsWith("Medio Ambiente"),endsWith("Medio Ambiente"))))
                .andExpect(jsonPath("$.tickets[*].priority", hasItems(endsWith("ALTA"),endsWith("BAJA"))))
                .andExpect(jsonPath("$.tickets[*].title", hasItems(endsWith("Falta de casco para obreros en la obra de Rosario"),endsWith("Incendio en planta quimica"))))
                .andExpect(jsonPath("$.tickets[*].description", hasItems(endsWith("Se detectaron 4 obreros sin casco en la obra de Rosario, los mismos dieron como motivo la falta de equipocamiento."),endsWith("Exploto la caldera en la planta quimica de Rosario y murieron 3 trabajadores y hay 19 mas atrapados. Por favor llamar a los bomberos."))))
                .andExpect(jsonPath("$.tickets[*].status", hasItems(endsWith("EN PROGRESO"),endsWith("EN PROGRESO"))))
                .andExpect(jsonPath("$.tickets[*].created").exists())
                .andExpect(jsonPath("$.tickets[*].modified").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTicketForNonExistingUserTest() throws Exception {
        when(userService.getAllTicketsForUser("u584100")).thenThrow(new UserDoesNotExistsException());

        mockMvc.perform(get("/v1/users/u584100/tickets")).andExpect(status().isNotFound());
    }

    @Test
    public void createTicketExistingUserTest() throws Exception {

        LocalDateTime createdDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-10);
        LocalDateTime modifiedDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(-6);
        Ticket createdTicket = new Ticket(1L, "u584110", "u111222", "Medio Ambiente", "ALTA", "Solicitamos vaciar los tachos", "Se detectaron 4 obreros sin casco en la obra de Rosario, los mismos dieron como motivo la falta de equipocamiento.",
                "EN PROGRESO", createdDate, modifiedDate);

        when(userService.createTicket(any(String.class), any(Ticket.class))).thenReturn(createdTicket);

        mockMvc.perform(post("/v1/users/u584110/tickets")
                .content("{\"area\":\"Medio Ambiente\",\"priority\":\"Alta\",\"title\":\"Solicitamos vaciar los tachos\",\"description\":\"Se detectaron 4 obreros sin casco en la obra de Rosario, los mismos dieron como motivo la falta de equipocamiento.\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.owner", is("u584110")))
                .andExpect(jsonPath("$.creator", is("u111222")))
                .andExpect(jsonPath("$.area", is("Medio Ambiente")))
                .andExpect(jsonPath("$.priority", is("ALTA")))
                .andExpect(jsonPath("$.title", is("Solicitamos vaciar los tachos")))
                .andExpect(jsonPath("$.description", is("Se detectaron 4 obreros sin casco en la obra de Rosario, los mismos dieron como motivo la falta de equipocamiento.")))
                .andExpect(jsonPath("$.status", is("EN PROGRESO")))
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.modified").exists())
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/tickets/1"))))
                .andExpect(header().string("Location", endsWith("/tickets/1")))
                .andExpect(status().isCreated());
    }

    @Test
    public void createTicketNonExistingUserTest() throws Exception {
        when(userService.createTicket(eq("u111222"), any(Ticket.class))).thenThrow(new UserDoesNotExistsException());

        mockMvc.perform(post("/v1/users/u111222/tickets")
                .content("{\"area\":\"Medio Ambiente\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createUserNonExistingUsername() throws Exception {

        User user = new User(1L, "u584110", "Annies Lopez", "1234abcd", "Administrator", "Medio Ambiente", "mdiaz@proveedor.personal.com.ar", "ACTIVE");
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/v1/users")
                .content("{\"userName\" : \"u584109\",\"name\" : \"Annies Lopez\",\"password\" : \"1234abcd\",\"profile\" :\"Administrator\",\"area\": \"Medio Ambiente\",\"email\": \"alopez@mail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", endsWith("v1/users/u584110")))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.userName", is(user.getUserName())))
                .andExpect(jsonPath("$.profile", is(user.getProfile())))
                .andExpect(jsonPath("$.area", is(user.getArea())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.status", is(user.getStatus())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserExistingUsername() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new UserExistsException());

        mockMvc.perform(post("/v1/users")
                .content("{\"userName\" : \"u584109\",\"name\" : \"Annies Lopez\",\"password\" : \"1234abcd\",\"profile\" :\"Administrator\",\"area\": \"Medio Ambiente\",\"email\": \"alopez@mail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void getExistingUserTest() throws Exception {
        User user = new User(1L, "u584110", "Martin Diaz", "test", "Administrator", "Medio Ambiente", "mdiaz@proveedor.personal.com.ar", "ACTIVE");
        when(userService.getUserByUserName(any(String.class))).thenReturn(user);

        mockMvc.perform(get("/v1/users/u584110"))
                .andDo(print())
                .andExpect(jsonPath("$.userName", is(user.getUserName())))
                .andExpect(jsonPath("$.profile", is(user.getProfile())))
                .andExpect(jsonPath("$.area", is(user.getArea())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.status", is(user.getStatus())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.links[*].rel", hasItems(endsWith("self"), endsWith("tickets"))))
                .andExpect(status().isOk());

    }

    @Test
    public void getNonExistingUserTest() throws Exception{
        when(userService.getUserByUserName(any(String.class))).thenReturn(null);
        mockMvc.perform(get("/v1/users/u584110")).andExpect(status().isNotFound());
    }

    @Test
    public void findAllUsersTest() throws Exception {

        List<User> users = new ArrayList<>();
        users.add(new User(1L, "u584110", "Martin Diaz", "simple111", "Administrator", "Desarrollo", "mdiaz@proveedor.personal.com.ar", "ACTIVE"));
        users.add(new User(2L, "u584111", "Juan LaTorre", "simple112", "Analist", "Medio Ambiente", "jtorre@proveedor.personal.com.ar", "ACTIVE"));
        users.add(new User(3L, "u584112", "Jack Ewing", "simple113", "User", "Marketing", "jewing@proveedor.personal.com.ar", "INACTIVE"));

        UserList list = new UserList(users);

        when(userService.getAllUsers()).thenReturn(list);

        mockMvc.perform(get("/v1/users/")).andDo(print())
                .andExpect(jsonPath("$.users[*].userName", containsInAnyOrder("u584110","u584111","u584112")))
                .andExpect(jsonPath("$.users[*].name", containsInAnyOrder("Martin Diaz","Juan LaTorre","Jack Ewing")))
                .andExpect(jsonPath("$.users[*].password").doesNotExist())
                .andExpect(jsonPath("$.users[*].profile", containsInAnyOrder("Administrator","Analist","User")))
                .andExpect(jsonPath("$.users[*].area", containsInAnyOrder("Desarrollo","Medio Ambiente","Marketing")))
                .andExpect(jsonPath("$.users[*].email", containsInAnyOrder("mdiaz@proveedor.personal.com.ar","jtorre@proveedor.personal.com.ar","jewing@proveedor.personal.com.ar")))
                .andExpect(jsonPath("$.users[*].status", containsInAnyOrder("ACTIVE","INACTIVE","ACTIVE")))
                .andExpect(status().isOk());
    }

    @Test
    public void findUserByNameTest() throws Exception{
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "u584110", "Martin Diaz", "simple111", "Administrator", "Desarrollo", "mdiaz@proveedor.personal.com.ar", "ACTIVE"));
        users.add(new User(3L, "u584112", "Martin Ewing", "simple113", "User", "Marketing", "mewing@proveedor.personal.com.ar", "INACTIVE"));

        UserList list = new UserList(users);

        when(userService.getUsersByName(any(String.class))).thenReturn(list);

        mockMvc.perform(get("/v1/users").param("name","Martin"))
                .andDo(print())
                .andExpect(jsonPath("$.users[*].userName", containsInAnyOrder("u584110","u584112")))
                .andExpect(jsonPath("$.users[*].name", containsInAnyOrder("Martin Diaz","Martin Ewing")))
                .andExpect(jsonPath("$.users[*].password").doesNotExist())
                .andExpect(jsonPath("$.users[*].profile", containsInAnyOrder("Administrator","User")))
                .andExpect(jsonPath("$.users[*].area", containsInAnyOrder("Desarrollo","Marketing")))
                .andExpect(jsonPath("$.users[*].email", containsInAnyOrder("mdiaz@proveedor.personal.com.ar","mewing@proveedor.personal.com.ar")))
                .andExpect(jsonPath("$.users[*].status", containsInAnyOrder("ACTIVE","INACTIVE")))
                .andExpect(status().isOk());

    }

    @Test
    public void findUserByNameNotFoundTest() throws Exception{

        when(userService.getUsersByName(any(String.class))).thenReturn(null);
        mockMvc.perform(get("/v1/users").param("name","Martin"))
                .andExpect(status().isNotFound());

    }
}
