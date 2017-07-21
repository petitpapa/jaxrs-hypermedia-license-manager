package com.petitpapa.exceptions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ufficio
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@NotNull
@Constraint(validatedBy = ProductConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProduct {

    String message() default "{com.petitpapa.business.Created}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
