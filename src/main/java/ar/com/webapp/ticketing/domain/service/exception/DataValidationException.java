package ar.com.webapp.ticketing.domain.service.exception;

public class DataValidationException extends RuntimeException {

    public DataValidationException() {
    }

    public DataValidationException(String message) {

        super(message);
    }

    public DataValidationException(String message, Throwable cause) {

        super(message, cause);
    }
}
