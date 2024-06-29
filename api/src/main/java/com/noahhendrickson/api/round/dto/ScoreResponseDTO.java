package com.noahhendrickson.api.round.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScoreResponseDTO {

    private final UUID id;
    private final int score;
    private final int adjustedScore;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScoreResponseDTO(UUID id, int score, int adjustedScore, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.score = score;
        this.adjustedScore = adjustedScore;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getAdjustedScore() {
        return adjustedScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
