package com.noahhendrickson.bot.dto;

public class UserRegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final int initialHandicap;

    public UserRegistrationRequest(String firstName, String lastName, String email, int initialHandicap) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.initialHandicap = initialHandicap;
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

    public int getInitialHandicap() {
        return initialHandicap;
    }
}
