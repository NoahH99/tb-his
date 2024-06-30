package com.noahhendrickson.bot.service;

import com.noahhendrickson.bot.entity.User;
import com.noahhendrickson.bot.repository.UserRepository;
import com.noahhendrickson.bot.util.EmbedUtil;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String discordId, UUID apiId) {
        User user = new User(discordId, apiId);
        userRepository.save(user);
    }

    public boolean isAlreadyRegistered(String discordId, InteractionHook hook) {
        Optional<User> user = findByDiscordUserId(discordId);

        if (user.isPresent()) {
            String message = "You are already registered. If you believe this is a mistake please contact a bot administrator.";
            hook.sendMessageEmbeds(EmbedUtil.error(message, user.get().getHandicapApiUserId().toString())).queue();
            return true;
        }

        return false;
    }

    public Optional<User> findByDiscordUserId(String id) {
        return userRepository.findByDiscordUserId(id);
    }
}
