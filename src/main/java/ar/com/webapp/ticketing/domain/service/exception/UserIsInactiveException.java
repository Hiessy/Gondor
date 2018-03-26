package ar.com.webapp.ticketing.domain.service.exception;

public class UserIsInactiveException extends RuntimeException {

    public UserIsInactiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsInactiveException(String message) {
        super(message);
    }

    public UserIsInactiveException() {
        super();
    }
}
