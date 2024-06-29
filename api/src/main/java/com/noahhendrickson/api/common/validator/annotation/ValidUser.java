package com.noahhendrickson.api.common.validator.annotation;

import com.noahhendrickson.api.common.validator.ValidUserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidUserValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUser {

    String message() default "User does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
