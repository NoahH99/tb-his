package com.noahhendrickson.api.user.dto;

import com.noahhendrickson.api.common.validator.annotation.UniqueEmail;
import com.noahhendrickson.api.user.entity.AccountStatus;
import jakarta.validation.constraints.Email;

public class UserUpdateRequestDTO {

    private final String firstName;

    private final String lastName;

    @Email(message = "'email' must be valid")
    @UniqueEmail
    private final String email;

    private final AccountStatus accountStatus;

    public UserUpdateRequestDTO(String firstName, String lastName, String email, AccountStatus accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
}
