package com.noahhendrickson.api.common.exception;

import java.util.UUID;

public class HoleNotFoundException extends RuntimeException {

    public HoleNotFoundException(UUID holeId) {
        super("Hole with id '" + holeId + "' not found.");
    }
}
