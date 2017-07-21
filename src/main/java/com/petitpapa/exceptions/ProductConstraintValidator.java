/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.exceptions;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Ufficio
 */
public class ProductConstraintValidator implements ConstraintValidator<ValidProduct, JsonObject> {

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        final String productName = value.getString("productName", null);
        String clientId = value.getString("clientId", null);
        boolean valid = true;
        if (productName == null) {
            context.buildConstraintViolationWithTemplate("Il nome del prodotto non deve essere vuoto").addPropertyNode("productName").addConstraintViolation();
            valid = false;
        }
        if (clientId == null) {
            context.buildConstraintViolationWithTemplate("Il nome del cliente non deve essere vuoto").addPropertyNode("clientId").addConstraintViolation();
            valid = false;
        }
        return valid;

    }

    @Override
    public void initialize(ValidProduct constraintAnnotation) {
    }

}
