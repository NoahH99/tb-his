package com.noahhendrickson.api.round.service;

import com.noahhendrickson.api.common.event.SoftDeleteDeletableEntityEventPublisher;
import com.noahhendrickson.api.common.exception.RoundNotFoundException;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.round.dto.RoundRequestDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.repository.RoundRepository;
import com.noahhendrickson.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher;

    @Autowired
    public RoundService(RoundRepository roundRepository, SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher) {
        this.roundRepository = roundRepository;
        this.softDeleteDeletableEntityEventPublisher = softDeleteDeletableEntityEventPublisher;
    }

    public Round createRound(User user, Course course, Tee tee, RoundRequestDTO requestDTO) {
        Round round = new Round(
                user,
                course,
                tee,
                requestDTO.getDate()
        );

        return roundRepository.save(round);
    }

    public Round getRound(UUID roundId) {
        return roundRepository.findByIdAndDeletedIsFalse(roundId).orElseThrow(() -> new RoundNotFoundException(roundId));
    }

    public Round updateRound(UUID roundId, RoundRequestDTO requestDTO) {
        Round round = getRound(roundId);

        if (requestDTO.getDate() != null) round.setDate(requestDTO.getDate());

        return roundRepository.save(round);
    }

    public void deleteRound(UUID roundId) {
        Round round = getRound(roundId);
        softDeleteDeletableEntityEventPublisher.deleteDeletableEntity(round);
    }

    public List<Round> getAllRoundsOnCourseForUser(User user) {
        return roundRepository.findAllByUserAndDeletedIsFalse(user);
    }

    public List<Round> getAllRoundsOnCourse(Course course) {
        return roundRepository.findAllByCourseAndDeletedIsFalse(course);
    }

    public List<Round> getAllRoundsOnCourseForTee(Course course, Tee tee) {
        return roundRepository.findAllByCourseAndTeeAndDeletedIsFalse(course, tee);
    }
}
