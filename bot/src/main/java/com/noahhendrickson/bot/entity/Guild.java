package com.noahhendrickson.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "guilds")
public class Guild {

    @Id
    @Column(name = "guild_id", nullable = false)
    private String guildId;

    @Column(name = "announcement_channel_id", nullable = false)
    private UUID announcementChannelId;

    protected Guild() {
    }

    public Guild(String guildId, UUID announcementChannelId) {
        this.guildId = guildId;
        this.announcementChannelId = announcementChannelId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public UUID getAnnouncementChannelId() {
        return announcementChannelId;
    }

    public void setAnnouncementChannelId(UUID announcementChannelId) {
        this.announcementChannelId = announcementChannelId;
    }
}
