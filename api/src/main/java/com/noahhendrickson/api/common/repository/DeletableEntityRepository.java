package com.noahhendrickson.api.common.repository;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface DeletableEntityRepository<T extends DeletableEntity> extends JpaRepository<T, UUID> {

    Optional<T> findByIdAndDeletedIsFalse(UUID id);

}
