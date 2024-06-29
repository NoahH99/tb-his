package com.noahhendrickson.api.course.mapper;

import com.noahhendrickson.api.course.dto.CourseResponseDTO;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.user.dto.CourseHandicapDTO;

public class CourseMapper {

    public static CourseResponseDTO toCourseResponseDTO(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getLocation(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }

    public static CourseHandicapDTO toCourseHandicapDTO(Course course, double handicap, boolean initialHandicap) {
        return new CourseHandicapDTO(
                course.getId(),
                course.getName(),
                course.getLocation(),
                course.getCreatedAt(),
                course.getUpdatedAt(),
                handicap,
                initialHandicap
        );
    }
}
