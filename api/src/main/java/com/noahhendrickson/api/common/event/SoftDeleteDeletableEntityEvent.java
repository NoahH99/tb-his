package com.noahhendrickson.api.common.event;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import org.springframework.context.ApplicationEvent;

public class SoftDeleteDeletableEntityEvent<T extends DeletableEntity> extends ApplicationEvent {

    private final T deletableEntity;

    public SoftDeleteDeletableEntityEvent(Object source, T deletableEntity) {
        super(source);
        this.deletableEntity = deletableEntity;
    }

    public T getDeletableEntity() {
        return deletableEntity;
    }
}
