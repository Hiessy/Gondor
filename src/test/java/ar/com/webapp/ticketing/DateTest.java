package ar.com.webapp.ticketing;

import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.core.model.entities.Comment;
import ar.com.webapp.ticketing.core.model.entities.Ticket;
import ar.com.webapp.ticketing.rest.controller.UserControllerTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTest {

    private static Logger logger = LogManager.getLogger(DateTest.class);

    public static void main(String[] args){
        logger.info("This is a test");
        logger.debug("This is a debug");
    }
}
