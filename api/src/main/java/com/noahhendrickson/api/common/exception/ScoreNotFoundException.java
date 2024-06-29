package com.noahhendrickson.api.common.exception;

import java.util.UUID;

public class ScoreNotFoundException extends RuntimeException {

    public ScoreNotFoundException(UUID roundId, UUID holeId) {
        super("Score with roundId '" + roundId + "' and holeId '" + holeId + "' not found.");
    }

    public ScoreNotFoundException(UUID holeId) {
        super("Score with holeId '" + holeId + "' not found.");
    }
}
