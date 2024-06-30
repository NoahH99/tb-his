package com.noahhendrickson.bot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface GolfBotCommand {

    String getName();

    String getDescription();

    OptionData[] getOptions();

    void execute(SlashCommandInteractionEvent event, InteractionHook hook);
}