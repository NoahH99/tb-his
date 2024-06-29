package com.noahhendrickson.api.common.event;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SoftDeleteDeletableEntityEventListener<T extends DeletableEntity> implements ApplicationListener<SoftDeleteDeletableEntityEvent<T>> {

    private final ApplicationContext context;
    private final LogEventPublisher logEventPublisher;

    public SoftDeleteDeletableEntityEventListener(ApplicationContext context, LogEventPublisher logEventPublisher) {
        this.context = context;
        this.logEventPublisher = logEventPublisher;
    }

    @Override
    @Async("eventExecutor")
    public void onApplicationEvent(SoftDeleteDeletableEntityEvent event) {
        DeletableEntity deletableEntity = event.getDeletableEntity();
        DeletableEntityRepository<DeletableEntity> repository = getRepositoryForEntity(deletableEntity);

        logEventPublisher.info("Soft deleting {} with ID: {}", deletableEntity.getClass().getSimpleName(), deletableEntity.getId());

        deletableEntity.delete();
        repository.save(deletableEntity);

        logEventPublisher.info("Soft deleted {} with ID: {}", deletableEntity.getClass().getSimpleName(), deletableEntity.getId());
    }

    @SuppressWarnings("unchecked")
    private DeletableEntityRepository<DeletableEntity> getRepositoryForEntity(DeletableEntity deletableEntity) {
        Class<?> entityClass = deletableEntity.getClass();
        String repositoryBeanName = Character.toLowerCase(entityClass.getSimpleName().charAt(0)) + entityClass.getSimpleName().substring(1) + "Repository";

        return (DeletableEntityRepository<DeletableEntity>) context.getBean(repositoryBeanName);
    }
}
