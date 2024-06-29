package com.noahhendrickson.api;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer {

    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.3-alpine3.20");

    @BeforeAll
    static void startContainer() {
        container.start();

        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> System.getProperty("DB_URL"));
        registry.add("spring.datasource.username", () -> System.getProperty("DB_USERNAME"));
        registry.add("spring.datasource.password", () -> System.getProperty("DB_PASSWORD"));
    }
}
