package com.noahhendrickson.api.common.event;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class LogEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public LogEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void log(Level level, String message, Throwable throwable, Object... params) {
        LogEvent logEvent = new LogEvent(this, level, message, params, throwable);
        publisher.publishEvent(logEvent);
    }

    public void info(String message, Object... params) {
        log(Level.INFO, message, null, params);
    }

    public void warn(String message, Object... params) {
        log(Level.WARN, message, null, params);
    }

    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    public void debug(String message, Object... params) {
        log(Level.DEBUG, message, null, params);
    }

    public void trace(String message, Object... params) {
        log(Level.TRACE, message, null, params);
    }
}
