package com.noahhendrickson.api.course.service;

import com.noahhendrickson.api.common.event.SoftDeleteDeletableEntityEventPublisher;
import com.noahhendrickson.api.common.exception.TeeNotFoundException;
import com.noahhendrickson.api.course.dto.CourseTeeRequestDTO;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.course.repository.TeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseTeeService {

    private final TeeRepository teeRepository;
    private final SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher;

    @Autowired
    public CourseTeeService(TeeRepository teeRepository, SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher) {
        this.teeRepository = teeRepository;
        this.softDeleteDeletableEntityEventPublisher = softDeleteDeletableEntityEventPublisher;
    }

    public List<Tee> getAllTees() {
        return teeRepository.findAllByDeletedIsFalse();
    }

    public Tee createTee(Course course, CourseTeeRequestDTO requestDTO) {
        Tee tee = new Tee(
                course,
                requestDTO.getName(),
                requestDTO.getCourseRating(),
                requestDTO.getSlopeRating()
        );

        return teeRepository.save(tee);
    }

    public List<Tee> getTeesForCourse(Course course) {
        return teeRepository.findAllByCourseAndDeletedIsFalse(course);
    }

    public Tee getTee(UUID teeId) {
        return teeRepository.findByIdAndDeletedIsFalse(teeId).orElseThrow(() -> new TeeNotFoundException(teeId));
    }

    public Tee updateTee(UUID teeId, CourseTeeRequestDTO requestDTO) {
        Tee tee = getTee(teeId);

        if (requestDTO.getName() != null) tee.setName(requestDTO.getName());
        if (requestDTO.getCourseRating() != null) tee.setCourseRating(requestDTO.getCourseRating());
        if (requestDTO.getSlopeRating() != null) tee.setSlopeRating(requestDTO.getSlopeRating());

        return teeRepository.save(tee);
    }

    public void deleteTee(UUID teeId) {
        Tee tee = getTee(teeId);
        softDeleteDeletableEntityEventPublisher.deleteDeletableEntity(tee);
    }
}
