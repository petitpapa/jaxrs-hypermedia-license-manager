package com.petitpapa.exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Alseny Cissé
 */
public class LicenseValidatorConstraint implements ConstraintValidator<ValidLicense, JsonObject> {

    @Override
    public void initialize(ValidLicense constraintAnnotation) {
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        String renewDate = value.getString("limitDate", null);
        String productId = value.getString("productId", null);

        boolean isValid = true;

        if (renewDate == null) {
            context.buildConstraintViolationWithTemplate("La data di rinuovo non deve essere vuoto").addPropertyNode("limitDate").addConstraintViolation();
            isValid = false;
        }

        if (productId == null) {
            context.buildConstraintViolationWithTemplate("Il prodotto id non deve essere vuoto").addPropertyNode("id").addConstraintViolation();
            isValid = false;
        }

        if (renewDate != null)
            try {
                LocalDate.parse(renewDate, DateTimeFormatter.ofPattern("d-MM-yyyy"));
            } catch (DateTimeParseException e) {
                context.buildConstraintViolationWithTemplate("The date format accepted: d-MM-yyyy").addPropertyNode(renewDate).addConstraintViolation();
                isValid = false;
            }
        return isValid;
    }
}
