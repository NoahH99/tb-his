package com.noahhendrickson.api.course.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseTeeResponseDTO {

    private final UUID id;
    private final String name;
    private final double courseRating;
    private final double slopeRating;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CourseTeeResponseDTO(UUID id, String name, double courseRating, double slopeRating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.courseRating = courseRating;
        this.slopeRating = slopeRating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCourseRating() {
        return courseRating;
    }

    public double getSlopeRating() {
        return slopeRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
