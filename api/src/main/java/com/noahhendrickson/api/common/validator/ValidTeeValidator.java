package com.noahhendrickson.api.common.validator;

import com.noahhendrickson.api.common.validator.annotation.ValidTee;
import com.noahhendrickson.api.course.repository.TeeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValidTeeValidator implements ConstraintValidator<ValidTee, UUID> {

    private final TeeRepository teeRepository;
    private String message;

    @Autowired
    public ValidTeeValidator(TeeRepository teeRepository) {
        this.teeRepository = teeRepository;
    }

    @Override
    public void initialize(ValidTee constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        if (uuid == null) return false;
        return teeRepository.findByIdAndDeletedIsFalse(uuid).isPresent();
    }
}
