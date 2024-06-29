package com.noahhendrickson.api.common.validator;

import com.noahhendrickson.api.common.validator.annotation.ValidUser;
import com.noahhendrickson.api.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValidUserValidator implements ConstraintValidator<ValidUser, UUID> {

    private final UserRepository userRepository;
    private String message;

    @Autowired
    public ValidUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidUser constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        if (uuid == null) return false;
        return userRepository.findByIdAndDeletedIsFalse(uuid).isPresent();
    }
}
