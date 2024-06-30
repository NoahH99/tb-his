package com.noahhendrickson.bot.util;

import com.noahhendrickson.bot.entity.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.LocalDateTime;

public class EmbedUtil {

    public static final int SUCCESS_COLOR = 0x7CE781;
    public static final int ERROR_COLOR = 0xE77C7E;

    public static MessageEmbed success(String message) {
        return new EmbedBuilder()
                .setColor(SUCCESS_COLOR)
                .setDescription(message)
                .setTimestamp(LocalDateTime.now())
                .build();
    }

    public static MessageEmbed success(String message, String footer) {
        return new EmbedBuilder()
                .setColor(SUCCESS_COLOR)
                .setDescription(message)
                .setFooter(footer)
                .setTimestamp(LocalDateTime.now())
                .build();
    }

    public static MessageEmbed error(String message) {
        return new EmbedBuilder()
                .setColor(ERROR_COLOR)
                .setDescription(message)
                .setTimestamp(LocalDateTime.now())
                .build();
    }

    public static MessageEmbed error(String message, String footer) {
        return new EmbedBuilder()
                .setColor(ERROR_COLOR)
                .setDescription(message)
                .setFooter(footer)
                .setTimestamp(LocalDateTime.now())
                .build();
    }
}
