package ar.com.webapp.ticketing.domain.service.exception;

public class UserIsNotOwnerException extends RuntimeException {

    public UserIsNotOwnerException() {
    }

    public UserIsNotOwnerException(String message) {

        super(message);
    }

    public UserIsNotOwnerException(String message, Throwable cause) {

        super(message, cause);
    }
}
