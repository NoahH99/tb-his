package com.noahhendrickson.api.common.exception;

import java.util.UUID;

public class RoundNotFoundException extends RuntimeException {

    public RoundNotFoundException(UUID roundId) {
        super("Round with id '" + roundId + "' not found.");
    }
}
