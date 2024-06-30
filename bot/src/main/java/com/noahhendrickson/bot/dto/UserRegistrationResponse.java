package com.noahhendrickson.bot.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRegistrationResponse {

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String email;
    private final AccountStatus accountStatus;
    private final int initialHandicap;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserRegistrationResponse(UUID id, String firstName, String lastName, String fullName, String email, AccountStatus accountStatus, int initialHandicap, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.accountStatus = accountStatus;
        this.initialHandicap = initialHandicap;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public int getInitialHandicap() {
        return initialHandicap;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
