package com.noahhendrickson.api.course.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CourseRequestDTO {

    @NotBlank(message = "'name' is required")
    @Length(max = 75, message = "'name' cannot be more than 75 characters")
    private final String name;

    @NotBlank(message = "'location' is required")
    @Length(max = 75, message = "'location' cannot be more than 75 characters")
    private final String location;

    public CourseRequestDTO(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
