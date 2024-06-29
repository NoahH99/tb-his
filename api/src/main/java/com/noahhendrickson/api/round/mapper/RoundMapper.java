package com.noahhendrickson.api.round.mapper;

import com.noahhendrickson.api.course.entity.HoleInfo;
import com.noahhendrickson.api.course.mapper.CourseMapper;
import com.noahhendrickson.api.course.mapper.HoleInfoMapper;
import com.noahhendrickson.api.course.mapper.TeeMapper;
import com.noahhendrickson.api.round.dto.RoundResponseDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.entity.Score;

import java.util.Map;

public class RoundMapper {

    public static RoundResponseDTO toRoundResponseDTO(Round round, Map<HoleInfo, Score> holeInfoScoreMap) {
        return new RoundResponseDTO(
                round.getId(),
                CourseMapper.toCourseResponseDTO(round.getCourse()),
                TeeMapper.toTeeResponseDTO(round.getTee()),
                holeInfoScoreMap.entrySet().stream().map(entry -> HoleInfoMapper.toHoleResponseDTO(entry.getKey(), entry.getValue())).toList(),
                round.getCreatedAt(),
                round.getUpdatedAt()
        );
    }
}
