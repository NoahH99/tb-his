package com.noahhendrickson.api.course.controller;

import com.noahhendrickson.api.course.CourseFacade;
import com.noahhendrickson.api.course.dto.CourseRequestDTO;
import com.noahhendrickson.api.course.dto.CourseResponseDTO;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.mapper.CourseMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseControllerV1 {

    private final CourseFacade courseFacade;

    @Autowired
    public CourseControllerV1(CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<Course> courses = courseFacade.getAllCourses();
        List<CourseResponseDTO> responseDTOs = courses.stream().map(CourseMapper::toCourseResponseDTO).toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO requestDTO) {
        Course course = courseFacade.createCourse(requestDTO);
        CourseResponseDTO responseDTO = CourseMapper.toCourseResponseDTO(course);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourse(@PathVariable UUID courseId) {
        Course course = courseFacade.getCourse(courseId);
        CourseResponseDTO responseDTO = CourseMapper.toCourseResponseDTO(course);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable UUID courseId, @RequestBody CourseRequestDTO requestDTO) {
        Course course = courseFacade.updateCourse(courseId, requestDTO);
        CourseResponseDTO responseDTO = CourseMapper.toCourseResponseDTO(course);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId) {
        courseFacade.deleteCourse(courseId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
