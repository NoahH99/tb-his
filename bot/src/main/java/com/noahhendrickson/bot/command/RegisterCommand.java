package com.noahhendrickson.bot.command;

import com.noahhendrickson.bot.service.RegistrationService;
import com.noahhendrickson.bot.service.UserService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommand implements GolfBotCommand {

    private final UserService userService;
    private final RegistrationService registrationService;

    @Autowired
    public RegisterCommand(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "Register yourself with the bot";
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[]{
                new OptionData(OptionType.STRING, "first-name", "First Name", true),
                new OptionData(OptionType.STRING, "last-name", "Last Name", true),
                new OptionData(OptionType.STRING, "email", "Email", true),
                new OptionData(OptionType.INTEGER, "initial-handicap", "Initial Handicap", true)
        };
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook hook) {
        String firstName = event.getOption("first-name", OptionMapping::getAsString);
        String lastName = event.getOption("last-name", OptionMapping::getAsString);
        String email = event.getOption("email", OptionMapping::getAsString);
        Integer initialHandicap = event.getOption("initial-handicap", OptionMapping::getAsInt);

        if (firstName == null || lastName == null || email == null || initialHandicap == null)
            throw new IllegalArgumentException("Something went very wrong if this ever gets thrown.");

        if (userService.isAlreadyRegistered(event.getUser().getId(), hook)) return;

        MessageEmbed response = registrationService.registerUser(event.getUser(), firstName, lastName, email, initialHandicap);
        hook.sendMessageEmbeds(response).queue();
    }
}
