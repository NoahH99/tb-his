package com.noahhendrickson.api.user.dto;

import com.noahhendrickson.api.course.dto.CourseResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseHandicapDTO extends CourseResponseDTO {

    private final double handicap;
    private final boolean initialHandicap;

    public CourseHandicapDTO(UUID id, String name, String location, LocalDateTime createdAt, LocalDateTime updatedAt, double handicap, boolean initialHandicap) {
        super(id, name, location, createdAt, updatedAt);
        this.handicap = handicap;
        this.initialHandicap = initialHandicap;
    }

    public double getHandicap() {
        return handicap;
    }

    public boolean isInitialHandicap() {
        return initialHandicap;
    }
}
