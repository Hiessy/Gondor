package ar.com.webapp.ticketing.core.validator;

import ar.com.webapp.ticketing.core.validator.model.annotation.AcceptedStringValidation;
import ar.com.webapp.ticketing.domain.service.exception.DataValidationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class AcceptedStringValidator implements ConstraintValidator<AcceptedStringValidation, String> {

    private List<String> valueList;

    @Override
    public void initialize(AcceptedStringValidation constraintAnnotation) {
        valueList = new ArrayList<String>();
        for (String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!valueList.contains(value.toUpperCase())) {
            throw new DataValidationException("Data is incorrect, value entered is: " + value + ", while expected: " + valueList.toString());
        }
        return true;
    }
}
