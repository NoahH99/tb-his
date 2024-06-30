package com.noahhendrickson.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "discord_user_id", nullable = false)
    private String discordUserId;

    @Column(name = "handicap_api_user_id", nullable = false)
    private UUID handicapApiUserId;

    protected User() {
    }

    public User(String discordUserId, UUID handicapApiUserId) {
        this.discordUserId = discordUserId;
        this.handicapApiUserId = handicapApiUserId;
    }

    public String getDiscordUserId() {
        return discordUserId;
    }

    public void setDiscordUserId(String discordUserId) {
        this.discordUserId = discordUserId;
    }

    public UUID getHandicapApiUserId() {
        return handicapApiUserId;
    }

    public void setHandicapApiUserId(UUID handicapApiUserId) {
        this.handicapApiUserId = handicapApiUserId;
    }
}