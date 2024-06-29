package com.noahhendrickson.api.common.validator;

import com.noahhendrickson.api.common.validator.annotation.ValidCourse;
import com.noahhendrickson.api.course.repository.CourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValidCourseValidator implements ConstraintValidator<ValidCourse, UUID> {

    private final CourseRepository courseRepository;
    private String message;

    @Autowired
    public ValidCourseValidator(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void initialize(ValidCourse constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        if (uuid == null) return false;
        return courseRepository.findByIdAndDeletedIsFalse(uuid).isPresent();
    }
}
