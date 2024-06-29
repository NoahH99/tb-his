package com.noahhendrickson.api.common.validator.annotation;

import com.noahhendrickson.api.common.validator.ValidTeeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidTeeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTee {

    String message() default "Tee does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
