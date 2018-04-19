package ar.com.webapp.ticketing.domain.service.exception;

public class TicketDoesNotExistException extends RuntimeException {

    public TicketDoesNotExistException() {
    }

    public TicketDoesNotExistException(String message) {

        super(message);
    }

    public TicketDoesNotExistException(String message, Throwable cause) {

        super(message, cause);
    }
}
