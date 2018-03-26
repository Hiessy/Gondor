package ar.com.webapp.ticketing.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
