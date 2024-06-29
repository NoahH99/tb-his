package com.noahhendrickson.api.common.exception;

import java.util.UUID;

public class TeeNotFoundException extends RuntimeException {

    public TeeNotFoundException(UUID teeId) {
        super("Tee with id '" + teeId + "' not found.");
    }
}
