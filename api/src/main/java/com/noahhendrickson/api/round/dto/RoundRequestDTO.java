package com.noahhendrickson.api.round.dto;

import com.noahhendrickson.api.common.validator.annotation.ValidUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;

public class RoundRequestDTO {

    @NotNull(message = "'userId' is required")
    @ValidUser
    private final UUID userId;

    @NotNull(message = "'date' is required")
    @PastOrPresent
    private final LocalDate date;

    public RoundRequestDTO(UUID userId, LocalDate date) {
        this.userId = userId;
        this.date = date;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }
}
