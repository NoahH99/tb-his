package com.noahhendrickson.bot.repository;

import com.noahhendrickson.bot.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuildRepository extends JpaRepository<Guild, String> {

    Optional<Guild> findByGuildId(String guildId);

}
