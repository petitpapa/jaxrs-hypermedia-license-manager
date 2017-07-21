/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.business.customer.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Ufficio
 */
@Documented
@Constraint(validatedBy = CustomerValidatorConstraint.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustomer {

    String message() default "{com.petitpapa.business.customer.entity.ValidCustomer}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
