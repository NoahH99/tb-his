package com.noahhendrickson.api.course.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Hole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoleRepository extends DeletableEntityRepository<Hole> {

    List<Hole> findAllByCourseAndDeletedIsFalse(Course course);

}
