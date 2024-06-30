CREATE TABLE users
(
    discord_user_id      VARCHAR(255) PRIMARY KEY,
    handicap_api_user_id UUID NOT NULL
);

CREATE TABLE guilds
(
    guild_id                VARCHAR(255) PRIMARY KEY,
    announcement_channel_id VARCHAR(255) NOT NULL
);
