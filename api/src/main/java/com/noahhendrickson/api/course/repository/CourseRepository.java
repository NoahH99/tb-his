package com.noahhendrickson.api.course.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.course.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends DeletableEntityRepository<Course> {

    List<Course> findAllByDeletedIsFalse();

}
