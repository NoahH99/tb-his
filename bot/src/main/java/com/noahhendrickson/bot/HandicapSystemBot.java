package com.noahhendrickson.bot;

import com.noahhendrickson.bot.command.CommandManager;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class HandicapSystemBot {

    private static final Logger logger = LoggerFactory.getLogger(HandicapSystemBot.class);

    private final CommandManager commandManager;

    public HandicapSystemBot(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @PostConstruct
    public void init() {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        if (token == null) {
            logger.error("DISCORD_BOT_TOKEN environment variable not set!");
            System.exit(1);
        }

        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .addEventListeners(commandManager)
                .build();

        commandManager.registerCommands(jda);
    }
}