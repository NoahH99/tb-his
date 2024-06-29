package com.noahhendrickson.api.user.dto;

import java.util.UUID;

public class UserHandicapForCourseResponseDTO {

    private final UUID id;
    private final CourseHandicapDTO course;

    public UserHandicapForCourseResponseDTO(UUID id, CourseHandicapDTO course) {
        this.id = id;
        this.course = course;
    }

    public UUID getId() {
        return id;
    }

    public CourseHandicapDTO getCourse() {
        return course;
    }
}
