package ar.com.webapp.ticketing.domain.service.exception;

public class TaskDoesNotExistException extends RuntimeException {

    public TaskDoesNotExistException() {
    }

    public TaskDoesNotExistException(String message) {

        super(message);
    }

    public TaskDoesNotExistException(String message, Throwable cause) {

        super(message, cause);
    }
}
