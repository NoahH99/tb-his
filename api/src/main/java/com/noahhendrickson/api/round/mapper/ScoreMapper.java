package com.noahhendrickson.api.round.mapper;

import com.noahhendrickson.api.round.dto.ScoreResponseDTO;
import com.noahhendrickson.api.round.entity.Score;

public class ScoreMapper {

    public static ScoreResponseDTO toScoreResponseDTO(Score score) {
        return new ScoreResponseDTO(
                score.getId(),
                score.getScore(),
                score.getAdjustedScore(),
                score.getCreatedAt(),
                score.getUpdatedAt()
        );
    }
}
