package com.noahhendrickson.api.course.service;

import com.noahhendrickson.api.common.event.SoftDeleteDeletableEntityEventPublisher;
import com.noahhendrickson.api.common.exception.CourseNotFoundException;
import com.noahhendrickson.api.course.dto.CourseRequestDTO;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher;

    @Autowired
    public CourseService(CourseRepository courseRepository, SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher) {
        this.courseRepository = courseRepository;
        this.softDeleteDeletableEntityEventPublisher = softDeleteDeletableEntityEventPublisher;
    }

    public Course createCourse(CourseRequestDTO requestDTO) {
        Course course = new Course(
                requestDTO.getName(),
                requestDTO.getLocation()
        );

        return courseRepository.save(course);
    }

    public Course updateCourse(UUID courseId, CourseRequestDTO requestDTO) {
        Course course = getCourse(courseId);

        if (requestDTO.getName() != null) course.setName(requestDTO.getName());
        if (requestDTO.getLocation() != null) course.setLocation(requestDTO.getLocation());

        return courseRepository.save(course);
    }

    public void deleteCourse(UUID courseId) {
        Course course = getCourse(courseId);
        softDeleteDeletableEntityEventPublisher.deleteDeletableEntity(course);
    }

    public Course getCourse(UUID courseId) {
        return courseRepository.findByIdAndDeletedIsFalse(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAllByDeletedIsFalse();
    }
}
