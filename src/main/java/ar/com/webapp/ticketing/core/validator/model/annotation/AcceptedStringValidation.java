package ar.com.webapp.ticketing.core.validator.model.annotation;


import ar.com.webapp.ticketing.core.validator.AcceptedStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = AcceptedStringValidator.class)
@Documented
public @interface AcceptedStringValidation {

    String[] acceptedValues();

    String message() default "{ar.com.webapp.ticketing.core.validator.AcceptedStringValidator" + "message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
