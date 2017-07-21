package com.petitpapa.exceptions;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Alseny Cissé
 */
public class CustomerConstraintValidator implements ConstraintValidator<ValidCustomer, JsonObject> {

    @Override
    public void initialize(ValidCustomer constraintAnnotation) {
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        final String name = value.getString("name", null);
        final String company = value.getString("company", null);
        final String phone = value.getString("phone", null);

        boolean valid = true;
        if (name == null) {
            context.buildConstraintViolationWithTemplate("Il nome del cliente non deve essere vuoto").addPropertyNode("name").addConstraintViolation();
            valid = false;
        }
        if (company == null) {
            context.buildConstraintViolationWithTemplate("Il compagnia del cliente non deve essere vuoto").addPropertyNode("company").addConstraintViolation();
            valid = false;
        }
        if (phone == null) {
            context.buildConstraintViolationWithTemplate("Il numero del cliente non deve essere vuoto").addPropertyNode("phone").addConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
