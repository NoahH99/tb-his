package com.noahhendrickson.api.course.mapper;

import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.entity.HoleInfo;
import com.noahhendrickson.api.round.dto.RoundHoleInfoResponseDTO;
import com.noahhendrickson.api.round.dto.RoundHoleResponseDTO;
import com.noahhendrickson.api.round.entity.Score;
import com.noahhendrickson.api.round.mapper.ScoreMapper;

public class HoleInfoMapper {

    public static RoundHoleResponseDTO toHoleResponseDTO(HoleInfo holeInfo, Score score) {
        Hole hole = holeInfo.getHole();

        return new RoundHoleResponseDTO(
                hole.getId(),
                hole.getHoleNumber(),
                HoleInfoMapper.toHoleInfoResponseDTO(holeInfo),
                ScoreMapper.toScoreResponseDTO(score),
                hole.getCreatedAt(),
                hole.getUpdatedAt()
        );
    }

    public static RoundHoleInfoResponseDTO toHoleInfoResponseDTO(HoleInfo holeInfo) {
        return new RoundHoleInfoResponseDTO(
                holeInfo.getId(),
                holeInfo.getYardage(),
                holeInfo.getPar(),
                holeInfo.getHandicap(),
                holeInfo.getCreatedAt(),
                holeInfo.getUpdatedAt()
        );
    }
}
