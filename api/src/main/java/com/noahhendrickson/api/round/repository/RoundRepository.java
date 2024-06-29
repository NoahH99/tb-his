package com.noahhendrickson.api.round.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends DeletableEntityRepository<Round> {

    List<Round> findAllByUserAndDeletedIsFalse(User user);

    List<Round> findAllByCourseAndDeletedIsFalse(Course course);

    List<Round> findAllByCourseAndTeeAndDeletedIsFalse(Course course, Tee tee);

}
