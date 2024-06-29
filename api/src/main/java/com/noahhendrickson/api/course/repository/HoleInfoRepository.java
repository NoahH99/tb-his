package com.noahhendrickson.api.course.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.entity.HoleInfo;
import com.noahhendrickson.api.course.entity.Tee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HoleInfoRepository extends DeletableEntityRepository<HoleInfo> {

    Optional<HoleInfo> findByTeeAndHoleAndDeletedIsFalse(Tee tee, Hole hole);

    @Query("SELECT hi FROM HoleInfo hi " +
            "JOIN hi.tee tee " +
            "JOIN hi.hole hole " +
            "WHERE tee.course.id = :courseId " +
            "AND hole.course.id = :courseId " +
            "AND hi.deleted = false")
    List<HoleInfo> findAllByCourseIdAndDeletedIsFalse(UUID courseId);

}
