package com.noahhendrickson.api.common;

import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.entity.HoleInfo;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.course.service.CourseService;
import com.noahhendrickson.api.course.service.CourseTeeService;
import com.noahhendrickson.api.course.service.HoleInfoService;
import com.noahhendrickson.api.round.dto.RoundRequestDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.entity.Score;
import com.noahhendrickson.api.round.service.RoundService;
import com.noahhendrickson.api.round.service.ScoreService;
import com.noahhendrickson.api.user.entity.User;
import com.noahhendrickson.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CourseRoundFacade {

    private final CourseService courseService;
    private final CourseTeeService courseTeeService;
    private final RoundService roundService;
    private final UserService userService;
    private final HoleInfoService holeInfoService;
    private final ScoreService scoreService;

    @Autowired
    public CourseRoundFacade(CourseService courseService, CourseTeeService courseTeeService, RoundService roundService, UserService userService, HoleInfoService holeInfoService, ScoreService scoreService) {
        this.courseService = courseService;
        this.courseTeeService = courseTeeService;
        this.roundService = roundService;
        this.userService = userService;
        this.holeInfoService = holeInfoService;
        this.scoreService = scoreService;
    }

    public List<Round> getAllRoundsOnCourse(UUID courseId) {
        Course course = courseService.getCourse(courseId);

        return roundService.getAllRoundsOnCourse(course);
    }

    public List<Round> getAllRoundsOnCourseForTee(UUID courseId, UUID teeId) {
        Course course = courseService.getCourse(courseId);
        Tee tee = courseTeeService.getTee(teeId);

        return roundService.getAllRoundsOnCourseForTee(course, tee);
    }

    public Round createRoundOnCourseForTee(UUID courseId, UUID teeId, RoundRequestDTO requestDTO) {
        User user = userService.getUser(requestDTO.getUserId());
        Course course = courseService.getCourse(courseId);
        Tee tee = courseTeeService.getTee(teeId);

        return roundService.createRound(user, course, tee, requestDTO);
    }

    public Map<HoleInfo, Score> getHoleInfosAndScores(Round round) {
        List<Hole> holes = round.getCourse().getHoles();

        Map<HoleInfo, Score> holeInfoScoreMap = new HashMap<>();

        for (Hole hole : holes) {
            HoleInfo holeInfo = holeInfoService.getHoleInfoByTeeAndHole(round.getTee(), hole);
            Score score = scoreService.getScoreByRoundAndHole(round, hole);

            holeInfoScoreMap.put(holeInfo, score);
        }

        return holeInfoScoreMap;
    }
}
