package com.noahhendrickson.api.course.dto;

import jakarta.validation.constraints.NotNull;

public class CourseHoleRequestDTO {

    @NotNull(message = "'holeNumber' is required")
    private final Integer holeNumber;

    public CourseHoleRequestDTO(Integer holeNumber) {
        this.holeNumber = holeNumber;
    }

    public @NotNull Integer getHoleNumber() {
        return holeNumber;
    }
}
