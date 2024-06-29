package com.noahhendrickson.api.common.event;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SoftDeleteDeletableEntityEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public SoftDeleteDeletableEntityEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public <T extends DeletableEntity> void deleteDeletableEntity(T deletableEntity) {
        publisher.publishEvent(new SoftDeleteDeletableEntityEvent<>(this, deletableEntity));

        ArrayList<? extends DeletableEntity> deletableEntities = new ArrayList<>(deletableEntity.getRelatedEntitiesToDelete());
        for (DeletableEntity entity : deletableEntities) {
            deleteDeletableEntity(entity);
        }
    }
}
