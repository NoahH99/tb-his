package com.noahhendrickson.api.round.repository;

import com.noahhendrickson.api.common.repository.DeletableEntityRepository;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends DeletableEntityRepository<Score> {

    Optional<Score> findByRoundAndHoleAndDeletedIsFalse(Round round, Hole hole);

    List<Score> findAllByHoleAndDeletedIsFalse(Hole hole);

}
