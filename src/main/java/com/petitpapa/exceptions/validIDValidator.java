package com.petitpapa.exceptions;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Alseny Cissé
 */
public class validIDValidator implements ConstraintValidator<validID, JsonObject> {

    @Override
    public void initialize(validID constraintAnnotation) {
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        boolean isValid = true;
        String id = value.getString("id", null);
        if (id == null) {
            context.buildConstraintViolationWithTemplate("You need to provide an id for identification").addPropertyNode("id").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
