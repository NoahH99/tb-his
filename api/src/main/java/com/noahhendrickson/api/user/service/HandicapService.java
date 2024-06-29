package com.noahhendrickson.api.user.service;

import com.noahhendrickson.api.course.CourseFacade;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.HoleInfo;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.course.mapper.CourseMapper;
import com.noahhendrickson.api.course.service.CourseTeeService;
import com.noahhendrickson.api.course.service.HoleInfoService;
import com.noahhendrickson.api.round.entity.CumulativeScore;
import com.noahhendrickson.api.round.service.CumulativeScoreService;
import com.noahhendrickson.api.user.dto.UserHandicapForCourseResponseDTO;
import com.noahhendrickson.api.user.dto.UserHandicapResponseDTO;
import com.noahhendrickson.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HandicapService {

    private static final Map<Integer, HandicapAdjustment> handicapAdjustments = new HashMap<>();

    static {
        handicapAdjustments.put(3, new HandicapAdjustment(1, -2.0));
        handicapAdjustments.put(4, new HandicapAdjustment(1, -1.0));
        handicapAdjustments.put(5, new HandicapAdjustment(1, 0.0));
        handicapAdjustments.put(6, new HandicapAdjustment(2, -1.0));
        handicapAdjustments.put(7, new HandicapAdjustment(2, 0.0));
        handicapAdjustments.put(8, new HandicapAdjustment(2, 0.0));
        handicapAdjustments.put(9, new HandicapAdjustment(3, 0.0));
        handicapAdjustments.put(10, new HandicapAdjustment(3, 0.0));
        handicapAdjustments.put(11, new HandicapAdjustment(3, 0.0));
        handicapAdjustments.put(12, new HandicapAdjustment(4, 0.0));
        handicapAdjustments.put(13, new HandicapAdjustment(4, 0.0));
        handicapAdjustments.put(14, new HandicapAdjustment(4, 0.0));
        handicapAdjustments.put(15, new HandicapAdjustment(5, 0.0));
        handicapAdjustments.put(16, new HandicapAdjustment(5, 0.0));
        handicapAdjustments.put(17, new HandicapAdjustment(6, 0.0));
        handicapAdjustments.put(18, new HandicapAdjustment(6, 0.0));
        handicapAdjustments.put(19, new HandicapAdjustment(7, 0.0));
        handicapAdjustments.put(20, new HandicapAdjustment(8, 0.0));
    }

    private final HoleInfoService holeInfoService;
    private final CourseTeeService courseTeeService;
    private final CumulativeScoreService cumulativeScoreService;
    private final UserService userService;
    private final CourseFacade courseFacade;

    @Autowired
    public HandicapService(HoleInfoService holeInfoService, CourseTeeService courseTeeService, CumulativeScoreService cumulativeScoreService, UserService userService, CourseFacade courseFacade) {
        this.holeInfoService = holeInfoService;
        this.courseTeeService = courseTeeService;
        this.cumulativeScoreService = cumulativeScoreService;
        this.userService = userService;
        this.courseFacade = courseFacade;
    }

    public UserHandicapResponseDTO getUserHandicap(UUID userId) {
        User user = userService.getUser(userId);

        List<CumulativeScore> cumulativeScores = cumulativeScoreService.getUserCumulativeScores(user);
        PriorityQueue<Double> lowestDifferentials = calculateLowestDifferentials(cumulativeScores);

        int count = Math.min(lowestDifferentials.size(), 20);
        if (count < 3) {
            return new UserHandicapResponseDTO(user.getId(), user.getInitialHandicap(), true);
        }

        double handicapIndex = calculateHandicapIndex(lowestDifferentials, count);
        return new UserHandicapResponseDTO(user.getId(), handicapIndex, false);
    }

    public UserHandicapForCourseResponseDTO getUserHandicapForCourse(UUID userId, UUID courseId) {
        User user = userService.getUser(userId);
        Course course = courseFacade.getCourse(courseId);

        List<Tee> tees = courseTeeService.getTeesForCourse(course);
        List<HoleInfo> holeInfos = holeInfoService.getAllHoleInfosByCourse(course);

        double averageCourseRating = calculateAverageCourseRating(tees);
        int averageSlopeRating = calculateAverageSlopeRating(tees);
        int par = calculateTotalPar(holeInfos);
        double handicapIndex = getUserHandicap(user.getId()).getHandicapIndex();

        if (user.getInitialHandicap() == handicapIndex) {
            return new UserHandicapForCourseResponseDTO(user.getId(), CourseMapper.toCourseHandicapDTO(course, user.getInitialHandicap(), true));
        }

        double courseHandicapIndex = calculateCourseHandicap(handicapIndex, averageSlopeRating, averageCourseRating, par);
        return new UserHandicapForCourseResponseDTO(user.getId(), CourseMapper.toCourseHandicapDTO(course, courseHandicapIndex, false));
    }

    private PriorityQueue<Double> calculateLowestDifferentials(List<CumulativeScore> cumulativeScores) {
        PriorityQueue<Double> lowestDifferentials = new PriorityQueue<>(21);

        for (CumulativeScore score : cumulativeScores) {
            double handicapDifferential = calculateHandicapDifferential(score);
            lowestDifferentials.offer(handicapDifferential);
            if (lowestDifferentials.size() > 20) {
                lowestDifferentials.poll();
            }
        }

        return lowestDifferentials;
    }

    private double calculateHandicapDifferential(CumulativeScore score) {
        Tee tee = score.getTee();
        return ((score.getTotalScoreAdjusted() - tee.getCourseRating()) / tee.getSlopeRating()) * 113;
    }

    private double calculateHandicapIndex(PriorityQueue<Double> lowestDifferentials, int count) {
        double sumOfLowestDifferentials = 0.0;
        HandicapAdjustment adjustment = handicapAdjustments.get(count);

        for (int i = 0; i < adjustment.getNumberOfScoresUsedForHandicapCalculation(); i++) {
            if (!lowestDifferentials.isEmpty()) {
                sumOfLowestDifferentials += lowestDifferentials.poll() + adjustment.getAdjustment();
            }
        }

        return sumOfLowestDifferentials / count;
    }

    private double calculateAverageCourseRating(List<Tee> tees) {
        return tees.stream()
                .mapToDouble(Tee::getCourseRating)
                .average()
                .orElse(0.0);
    }

    private int calculateAverageSlopeRating(List<Tee> tees) {
        return (int) tees.stream()
                .mapToInt(Tee::getSlopeRating)
                .average()
                .orElse(0.0);
    }

    private int calculateTotalPar(List<HoleInfo> holeInfos) {
        return holeInfos.stream()
                .mapToInt(HoleInfo::getPar)
                .sum();
    }

    private double calculateCourseHandicap(double handicap, int averageSlopeRating, double averageCourseRating, int par) {
        return ((handicap * averageSlopeRating) / 113) + ((averageCourseRating - par) / 2);
    }

    private static class HandicapAdjustment {

        private final int numberOfScoresUsedForHandicapCalculation;
        private final double adjustment;

        public HandicapAdjustment(int numberOfScoresUsedForHandicapCalculation, double adjustment) {
            this.numberOfScoresUsedForHandicapCalculation = numberOfScoresUsedForHandicapCalculation;
            this.adjustment = adjustment;
        }

        public int getNumberOfScoresUsedForHandicapCalculation() {
            return numberOfScoresUsedForHandicapCalculation;
        }

        public double getAdjustment() {
            return adjustment;
        }
    }
}
