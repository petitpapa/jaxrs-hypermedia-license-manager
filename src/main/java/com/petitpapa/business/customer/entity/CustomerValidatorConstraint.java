package com.petitpapa.business.customer.entity;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author petitpapa
 */
public class CustomerValidatorConstraint implements ConstraintValidator<ValidCustomer, JsonObject> {

    @Override
    public void initialize(ValidCustomer constraintAnnotation) {
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        boolean isvalid = true;
        String username = value.getString("userName", null);
        String company = value.getString("company", null);
        String password = value.getString("password", null);

        if (username == null) {
            context.buildConstraintViolationWithTemplate("{javax.validation.constraints.NotNull.message}").addPropertyNode("userName").addConstraintViolation();
            isvalid = false;
        }
        if (company == null) {
            context.buildConstraintViolationWithTemplate("{javax.validation.constraints.NotNull.message}").addPropertyNode("company").addConstraintViolation();
            isvalid = false;
        }
        if (password == null) {
            context.buildConstraintViolationWithTemplate("{javax.validation.constraints.NotNull.message}").addPropertyNode("password").addConstraintViolation();
            isvalid = false;
        }
        return isvalid;
    }
}
