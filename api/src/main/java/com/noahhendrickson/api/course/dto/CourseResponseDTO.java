package com.noahhendrickson.api.course.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseResponseDTO {

    private final UUID id;
    private final String name;
    private final String location;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CourseResponseDTO(UUID id, String name, String location, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
