package com.noahhendrickson.api.round.service;

import com.noahhendrickson.api.round.entity.CumulativeScore;
import com.noahhendrickson.api.round.repository.CumulativeScoreRepository;
import com.noahhendrickson.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CumulativeScoreService {

    private final CumulativeScoreRepository cumulativeScoreRepository;

    @Autowired
    public CumulativeScoreService(CumulativeScoreRepository cumulativeScoreRepository) {
        this.cumulativeScoreRepository = cumulativeScoreRepository;
    }

    public List<CumulativeScore> getUserCumulativeScores(User user) {
        return cumulativeScoreRepository.findAllByUserId(user.getId());
    }
}
