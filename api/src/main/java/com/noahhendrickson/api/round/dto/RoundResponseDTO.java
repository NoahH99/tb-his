package com.noahhendrickson.api.round.dto;

import com.noahhendrickson.api.course.dto.CourseResponseDTO;
import com.noahhendrickson.api.course.dto.CourseTeeResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RoundResponseDTO {

    private final UUID id;
    private final CourseResponseDTO course;
    private final CourseTeeResponseDTO tee;
    private final List<RoundHoleResponseDTO> holes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public RoundResponseDTO(UUID id, CourseResponseDTO course, CourseTeeResponseDTO tee, List<RoundHoleResponseDTO> holes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.course = course;
        this.tee = tee;
        this.holes = holes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public CourseResponseDTO getCourse() {
        return course;
    }

    public CourseTeeResponseDTO getTee() {
        return tee;
    }

    public List<RoundHoleResponseDTO> getHoles() {
        return holes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
