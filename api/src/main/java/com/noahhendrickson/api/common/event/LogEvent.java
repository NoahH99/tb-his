package com.noahhendrickson.api.common.event;

import org.slf4j.event.Level;
import org.springframework.context.ApplicationEvent;

public class LogEvent extends ApplicationEvent {

    private final Level level;
    private final String message;
    private final Object[] params;
    private final Throwable throwable;

    public LogEvent(Object source, Level level, String message, Object[] params, Throwable throwable) {
        super(source);
        this.level = level;
        this.message = message;
        this.params = params;
        this.throwable = throwable;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getParams() {
        return params;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
