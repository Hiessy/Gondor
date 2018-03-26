package ar.com.webapp.ticketing.domain.service.exception;

public class UserExistsException extends RuntimeException{

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException() {
        super();
    }
}
