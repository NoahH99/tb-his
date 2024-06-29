package com.noahhendrickson.api.user.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.round.entity.Round;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends DeletableEntity {

    @OneToMany(mappedBy = "user")
    private final List<Round> rounds = new ArrayList<>();
    @Column(name = "first_name", length = 75, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 75, nullable = false)
    private String lastName;
    @Column(name = "full_name", length = 151, nullable = false, insertable = false, updatable = false)
    private String fullName;
    @Column(name = "email", length = 256, unique = true, nullable = false)
    private String email;
    @Column(name = "account_status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @Column(name = "initial_handicap", nullable = false)
    private int initialHandicap;
    @Column(name = "api_key_hash", unique = true)
    private byte[] apiKeyHash;

    protected User() {
    }

    public User(String firstName, String lastName, String email, AccountStatus accountStatus, int initialHandicap) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountStatus = accountStatus;
        this.initialHandicap = initialHandicap;
    }

    @Override
    public List<? extends DeletableEntity> getRelatedEntitiesToDelete() {
        return rounds;
    }

    @Override
    public void delete() {
        this.setAccountStatus(AccountStatus.DELETED);
        super.setDeleted(true);
        super.setDeletedAt(LocalDateTime.now());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getInitialHandicap() {
        return initialHandicap;
    }

    public void setInitialHandicap(int initialHandicap) {
        this.initialHandicap = initialHandicap;
    }

    public byte[] getApiKeyHash() {
        return apiKeyHash;
    }

    public void setApiKeyHash(byte[] apiKeyHash) {
        this.apiKeyHash = apiKeyHash;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
