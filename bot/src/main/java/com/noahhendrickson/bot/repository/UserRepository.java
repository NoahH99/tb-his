package com.noahhendrickson.bot.repository;

import com.noahhendrickson.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByDiscordUserId(String discordUserId);

}
