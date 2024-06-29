package com.noahhendrickson.api.course.mapper;

import com.noahhendrickson.api.course.dto.CourseHoleResponseDTO;
import com.noahhendrickson.api.course.entity.Hole;

public class HoleMapper {

    public static CourseHoleResponseDTO toHoleResponseDTO(Hole hole) {
        return new CourseHoleResponseDTO(
                hole.getId(),
                CourseMapper.toCourseResponseDTO(hole.getCourse()),
                hole.getHoleNumber(),
                hole.getCreatedAt(),
                hole.getUpdatedAt()
        );
    }
}
