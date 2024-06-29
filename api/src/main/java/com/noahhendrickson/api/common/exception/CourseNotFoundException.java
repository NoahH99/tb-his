package com.noahhendrickson.api.common.exception;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(UUID courseId) {
        super("Course with id '" + courseId + "' not found.");
    }
}
