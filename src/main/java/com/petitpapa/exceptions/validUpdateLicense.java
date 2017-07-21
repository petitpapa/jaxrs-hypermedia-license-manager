package com.petitpapa.exceptions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Alseny Cissé
 */
@Documented
@Constraint(validatedBy = validUpdateLicenseValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface validUpdateLicense {

    String message() default "{com.petitpapa.exceptions.validUpdateLicense}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
