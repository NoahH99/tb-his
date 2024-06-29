package com.noahhendrickson.api.course.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeeRepository extends DeletableEntityRepository<Tee> {

    List<Tee> findAllByDeletedIsFalse();

    List<Tee> findAllByCourseAndDeletedIsFalse(Course course);

}
