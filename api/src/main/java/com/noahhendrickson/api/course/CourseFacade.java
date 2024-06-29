package com.noahhendrickson.api.course;

import com.noahhendrickson.api.course.dto.CourseHoleRequestDTO;
import com.noahhendrickson.api.course.dto.CourseRequestDTO;
import com.noahhendrickson.api.course.dto.CourseTeeRequestDTO;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.course.service.CourseHoleService;
import com.noahhendrickson.api.course.service.CourseService;
import com.noahhendrickson.api.course.service.CourseTeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CourseFacade {

    private final CourseService courseService;
    private final CourseHoleService courseHoleService;
    private final CourseTeeService courseTeeService;

    @Autowired
    public CourseFacade(CourseService courseService, CourseHoleService courseHoleService, CourseTeeService courseTeeService) {
        this.courseService = courseService;
        this.courseHoleService = courseHoleService;
        this.courseTeeService = courseTeeService;
    }

    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    public Course createCourse(CourseRequestDTO requestDTO) {
        return courseService.createCourse(requestDTO);
    }

    public Course getCourse(UUID courseId) {
        return courseService.getCourse(courseId);
    }

    public Course updateCourse(UUID courseId, CourseRequestDTO requestDTO) {
        return courseService.updateCourse(courseId, requestDTO);
    }

    public void deleteCourse(UUID courseId) {
        courseService.deleteCourse(courseId);
    }

    public List<Hole> getAllHolesOnCourse(UUID courseId) {
        Course course = courseService.getCourse(courseId);
        return courseHoleService.getAllHolesOnCourse(course);
    }

    public Hole createHole(UUID courseId, CourseHoleRequestDTO requestDTO) {
        Course course = courseService.getCourse(courseId);
        return courseHoleService.createHole(course, requestDTO);
    }

    public Hole getHole(UUID ignored, UUID holeId) {
        return courseHoleService.getHole(holeId);
    }

    public Hole updateHole(UUID ignored, UUID holeId, CourseHoleRequestDTO requestDTO) {
        return courseHoleService.updateHole(holeId, requestDTO);
    }

    public void deleteHole(UUID ignored, UUID holeId) {
        courseHoleService.deleteHole(holeId);
    }

    public List<Tee> getAllTees() {
        return courseTeeService.getAllTees();
    }

    public Tee createTee(UUID courseId, CourseTeeRequestDTO requestDTO) {
        Course course = courseService.getCourse(courseId);
        return courseTeeService.createTee(course, requestDTO);
    }

    public Tee getTee(UUID ignored, UUID teeId) {
        return courseTeeService.getTee(teeId);
    }

    public Tee updateTee(UUID ignored, UUID teeId, CourseTeeRequestDTO requestDTO) {
        return courseTeeService.updateTee(teeId, requestDTO);
    }

    public void deleteTee(UUID ignored, UUID teeId) {
        courseTeeService.deleteTee(teeId);
    }
}
