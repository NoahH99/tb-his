package com.noahhendrickson.api.course.service;

import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.round.dto.RoundRequestDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.repository.RoundRepository;
import com.noahhendrickson.api.user.entity.User;
import com.noahhendrickson.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseRoundService {

    private final CourseService courseService;
    private final CourseTeeService courseTeeService;
    private final UserService userService;
    private final RoundRepository roundRepository;

    @Autowired
    public CourseRoundService(CourseService courseService, CourseTeeService courseTeeService, UserService userService, RoundRepository roundRepository) {
        this.courseService = courseService;
        this.courseTeeService = courseTeeService;
        this.userService = userService;
        this.roundRepository = roundRepository;
    }

    public List<Round> getAllRoundsOnCourse(UUID courseId) {
        Course course = courseService.getCourse(courseId);
        return roundRepository.findAllByCourseAndDeletedIsFalse(course);
    }

    public List<Round> getAllRoundsOnCourseForTee(UUID courseId, UUID teeId) {
        Course course = courseService.getCourse(courseId);
        Tee tee = courseTeeService.getTee(teeId);

        return roundRepository.findAllByCourseAndTeeAndDeletedIsFalse(course, tee);
    }

    public Round createRoundOnCourseForTee(UUID courseId, UUID teeId, RoundRequestDTO requestDTO) {
        User user = userService.getUser(requestDTO.getUserId());
        Course course = courseService.getCourse(courseId);
        Tee tee = courseTeeService.getTee(teeId);

        Round round = new Round(user, course, tee, requestDTO.getDate());

        return roundRepository.save(round);
    }
}
