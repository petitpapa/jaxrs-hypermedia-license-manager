/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.business.customer.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Ufficio
 */
public class LoginValidatorConstraintValidator implements ConstraintValidator<ValidLoggin, JsonObject> {

    @Override
    public void initialize(ValidLoggin constraintAnnotation) {
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        boolean isvalid = true;
        String username = value.getString("userName", null);
        String password = value.getString("password", null);

        if (username == null) {
            context.buildConstraintViolationWithTemplate("The username is required").addPropertyNode("userName").addConstraintViolation();
            Logger.getLogger("").log(Level.INFO, "login failed!");
            isvalid = false;
        }

        if (password == null) {
            context.buildConstraintViolationWithTemplate("The password is required").addPropertyNode("password").addConstraintViolation();
            Logger.getLogger("").log(Level.INFO, "login failed!");
            isvalid = false;
        }
        return isvalid;
    }
}
