package ar.com.webapp.ticketing.domain.service.exception;

public class AreaDoesNotExistException extends RuntimeException{

    public AreaDoesNotExistException() {
    }

    public AreaDoesNotExistException(String message) {

        super(message);
    }

    public AreaDoesNotExistException(String message, Throwable cause) {

        super(message, cause);
    }
}
