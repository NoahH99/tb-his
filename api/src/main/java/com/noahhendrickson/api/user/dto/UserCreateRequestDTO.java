package com.noahhendrickson.api.user.dto;

import com.noahhendrickson.api.common.validator.annotation.UniqueEmail;
import com.noahhendrickson.api.user.entity.AccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserCreateRequestDTO {

    @NotBlank(message = "'firstName' is required")
    @Length(max = 75, message = "'firstName' cannot be more than 75 characters")
    private final String firstName;

    @NotBlank(message = "'lastName' is required")
    @Length(max = 75, message = "'lastName' cannot be more than 75 characters")
    private final String lastName;

    @NotBlank(message = "'email' is required")
    @Length(max = 256, message = "'email' cannot be more than 256 characters")
    @Email(message = "'email' must be valid")
    @UniqueEmail
    private final String email;

    private final AccountStatus accountStatus;

    @NotNull(message = "'initialHandicap' is required")
    private final Integer initialHandicap;

    public UserCreateRequestDTO(String firstName, String lastName, String email, AccountStatus accountStatus, Integer initialHandicap) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.initialHandicap = initialHandicap;
        this.accountStatus = accountStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public Integer getInitialHandicap() {
        return initialHandicap;
    }
}
