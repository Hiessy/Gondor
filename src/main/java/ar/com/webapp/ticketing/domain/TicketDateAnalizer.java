package ar.com.webapp.ticketing.domain;

import ar.com.webapp.ticketing.core.model.entities.Ticket;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TicketDateAnalizer {

    public static void checkLastUpdate(int ticketMaxLife, List<Ticket> tickets) {
        tickets.stream().forEach(
                t -> {
                    if (ChronoUnit.DAYS.between(t.getModified(), LocalDateTime.now()) > ticketMaxLife)
                        t.setStatus("CLOSED TIMEOUT");
                    });
    }

}
