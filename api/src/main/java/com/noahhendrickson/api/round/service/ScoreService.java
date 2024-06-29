package com.noahhendrickson.api.round.service;

import com.noahhendrickson.api.common.exception.ScoreNotFoundException;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.entity.Score;
import com.noahhendrickson.api.round.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Score getScoreByRoundAndHole(Round round, Hole hole) {
        return scoreRepository.findByRoundAndHoleAndDeletedIsFalse(round, hole)
                .orElseThrow(() -> new ScoreNotFoundException(round.getId(), hole.getId()));
    }

    public List<Score> getScoresByHole(Hole hole) {
        return scoreRepository.findAllByHoleAndDeletedIsFalse(hole);
    }
}
