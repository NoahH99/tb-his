package com.noahhendrickson.api.round.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RoundHoleResponseDTO {

    private final UUID id;
    private final int holeNumber;
    private final RoundHoleInfoResponseDTO info;
    private final ScoreResponseDTO score;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public RoundHoleResponseDTO(UUID id, int holeNumber, RoundHoleInfoResponseDTO info, ScoreResponseDTO score, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.holeNumber = holeNumber;
        this.info = info;
        this.score = score;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public RoundHoleInfoResponseDTO getInfo() {
        return info;
    }

    public ScoreResponseDTO getScore() {
        return score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
