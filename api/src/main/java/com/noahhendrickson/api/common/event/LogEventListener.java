package com.noahhendrickson.api.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogEventListener implements ApplicationListener<LogEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LogEventListener.class);

    @Override
    @Async("eventExecutor")
    public void onApplicationEvent(LogEvent event) {
        switch (event.getLevel()) {
            case INFO:
                logger.info(event.getMessage(), event.getParams());
                break;
            case WARN:
                logger.warn(event.getMessage(), event.getParams());
                break;
            case ERROR:
                if (event.getThrowable() != null) {
                    logger.error(event.getMessage(), event.getThrowable());
                } else {
                    logger.error(event.getMessage(), event.getParams());
                }
                break;
            case DEBUG:
                logger.debug(event.getMessage(), event.getParams());
                break;
            case TRACE:
                logger.trace(event.getMessage(), event.getParams());
                break;
            default:
                throw new UnsupportedOperationException("Unknown level: " + event.getLevel());
        }
    }
}
