package com.noahhendrickson.api.user.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DeletableEntityRepository<User> {

    boolean existsByEmail(String email);

}
