package com.noahhendrickson.bot.command;

import com.noahhendrickson.bot.service.UserService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CommandManager extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    private final Map<String, GolfBotCommand> commands;
    private final UserService userService;

    @Autowired
    public CommandManager(UserService userService, List<GolfBotCommand> commands) {
        this.commands = new HashMap<>();
        this.userService = userService;

        commands.forEach(command -> this.commands.put(command.getName(), command));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void registerCommands(JDA jda) {
        CommandListUpdateAction updatedCommands = jda.updateCommands();

        for (GolfBotCommand command : commands.values()) {
            SlashCommandData slash = Commands.slash(command.getName(), command.getDescription());

            for (OptionData data : command.getOptions()) {
                slash.addOption(data.getType(), data.getName(), data.getDescription(), data.isRequired(), data.isAutoComplete());
            }

            updatedCommands.addCommands(slash);
        }

        updatedCommands.queue(
                success -> logger.info("Commands successfully registered!"),
                error -> logger.error("Failed to register commands", error)
        );
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (commands.containsKey(commandName)) {
            GolfBotCommand command = commands.get(commandName);

            try {
                event.deferReply().queue();
                command.execute(event, event.getHook());
            } catch (Exception exception) {
                UUID errorId = UUID.randomUUID();
                logger.error("An unexpected error occurred with ID: {}", errorId, exception);

                event.getHook().sendMessageEmbeds(
                        new EmbedBuilder()
                                .setColor(0xE77C7E)
                                .setDescription("An unexpected error occurred. Please contact a bot administrator if this continues to happen.")
                                .setFooter("Error ID: " + errorId, null)
                                .setTimestamp(LocalDateTime.now())
                                .build()
                ).queue();
            }
        }
    }
}
