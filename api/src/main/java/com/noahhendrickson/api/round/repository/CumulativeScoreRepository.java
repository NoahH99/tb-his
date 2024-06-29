package com.noahhendrickson.api.round.repository;

import com.noahhendrickson.api.round.entity.CumulativeScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CumulativeScoreRepository extends JpaRepository<CumulativeScore, UUID> {

    List<CumulativeScore> findAllByUserId(UUID userId);

}
