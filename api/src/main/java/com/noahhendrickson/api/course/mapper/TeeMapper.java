package com.noahhendrickson.api.course.mapper;

import com.noahhendrickson.api.course.dto.CourseTeeResponseDTO;
import com.noahhendrickson.api.course.entity.Tee;

public class TeeMapper {

    public static CourseTeeResponseDTO toTeeResponseDTO(Tee tee) {
        return new CourseTeeResponseDTO(
                tee.getId(),
                tee.getName(),
                tee.getCourseRating(),
                tee.getSlopeRating(),
                tee.getCreatedAt(),
                tee.getUpdatedAt()
        );
    }
}
