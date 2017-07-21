package com.petitpapa.exceptions;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Alseny Cissé
 */
public class validUpdateLicenseValidator implements ConstraintValidator<validUpdateLicense, JsonObject> {

    @Override
    public void initialize(validUpdateLicense constraintAnnotation) {
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        final String licenseId = value.getString("licenseId", null);
        final String numberOfDays = value.getString("numberOfDays", null);

        boolean valid = true;
        if (licenseId == null) {
            context.buildConstraintViolationWithTemplate("L'identificatore id della licenza non deve essere vuoto").addPropertyNode("licenseId").addConstraintViolation();
            valid = false;
        }
        if (numberOfDays == null) {
            context.buildConstraintViolationWithTemplate("Il numero di giorni è obligatorio").addPropertyNode("numberOfdays").addConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
