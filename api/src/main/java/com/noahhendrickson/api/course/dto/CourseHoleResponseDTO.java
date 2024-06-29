package com.noahhendrickson.api.course.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseHoleResponseDTO {

    private final UUID id;
    private final CourseResponseDTO course;
    private final int holeNumber;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CourseHoleResponseDTO(UUID id, CourseResponseDTO course, int holeNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.course = course;
        this.holeNumber = holeNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public CourseResponseDTO getCourse() {
        return course;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
