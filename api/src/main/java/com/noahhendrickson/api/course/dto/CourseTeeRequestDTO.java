package com.noahhendrickson.api.course.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CourseTeeRequestDTO {

    @NotNull(message = "'name' is required")
    @Length(min = 1, max = 15, message = "'name' must be between 1 and 15 characters inclusive")
    private final String name;

    @NotNull(message = "'courseRating' is required")
    private final Double courseRating;

    @NotNull(message = "'slopeRating' is required")
    private final Integer slopeRating;

    public CourseTeeRequestDTO(String name, Double courseRating, Integer slopeRating) {
        this.name = name;
        this.courseRating = courseRating;
        this.slopeRating = slopeRating;
    }

    public String getName() {
        return name;
    }

    public Double getCourseRating() {
        return courseRating;
    }

    public Integer getSlopeRating() {
        return slopeRating;
    }
}
