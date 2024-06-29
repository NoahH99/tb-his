package com.noahhendrickson.api.user.dto;

import java.util.UUID;

public class UserHandicapResponseDTO {

    private final UUID id;
    private final double handicapIndex;
    private final boolean initialHandicap;

    public UserHandicapResponseDTO(UUID id, double handicapIndex, boolean initialHandicap) {
        this.id = id;
        this.handicapIndex = handicapIndex;
        this.initialHandicap = initialHandicap;
    }

    public UUID getId() {
        return id;
    }

    public double getHandicapIndex() {
        return handicapIndex;
    }

    public boolean isInitialHandicap() {
        return initialHandicap;
    }
}
