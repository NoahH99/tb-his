package com.noahhendrickson.api.common.exception;

import java.util.UUID;

public class HoleInfoNotFoundException extends RuntimeException {

    public HoleInfoNotFoundException(UUID teeId, UUID holeId) {
        super("HoleInfo with teeId '" + teeId + "' and holeId '" + holeId + "' not found.");
    }
}
