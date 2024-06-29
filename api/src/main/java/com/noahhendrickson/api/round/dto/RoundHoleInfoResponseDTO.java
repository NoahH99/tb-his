package com.noahhendrickson.api.round.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RoundHoleInfoResponseDTO {

    private final UUID id;
    private final int yardage;
    private final int par;
    private final int handicap;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public RoundHoleInfoResponseDTO(UUID id, int yardage, int par, int handicap, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.yardage = yardage;
        this.par = par;
        this.handicap = handicap;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public int getYardage() {
        return yardage;
    }

    public int getPar() {
        return par;
    }

    public int getHandicap() {
        return handicap;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
