package com.noahhendrickson.api.course.service;

import com.noahhendrickson.api.common.event.SoftDeleteDeletableEntityEventPublisher;
import com.noahhendrickson.api.common.exception.HoleNotFoundException;
import com.noahhendrickson.api.course.dto.CourseHoleRequestDTO;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.repository.HoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseHoleService {

    private final HoleRepository holeRepository;
    private final SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher;

    @Autowired
    public CourseHoleService(HoleRepository holeRepository, SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher) {
        this.holeRepository = holeRepository;
        this.softDeleteDeletableEntityEventPublisher = softDeleteDeletableEntityEventPublisher;
    }

    public Hole createHole(Course course, CourseHoleRequestDTO requestDTO) {
        Hole hole = new Hole(course, requestDTO.getHoleNumber());

        return holeRepository.save(hole);
    }

    public Hole getHole(UUID holeId) {
        return holeRepository.findByIdAndDeletedIsFalse(holeId)
                .orElseThrow(() -> new HoleNotFoundException(holeId));
    }

    public Hole updateHole(UUID holeId, CourseHoleRequestDTO requestDTO) {
        Hole hole = getHole(holeId);

        if (requestDTO.getHoleNumber() != null) hole.setHoleNumber(requestDTO.getHoleNumber());

        return holeRepository.save(hole);
    }

    public void deleteHole(UUID holeId) {
        Hole hole = getHole(holeId);
        softDeleteDeletableEntityEventPublisher.deleteDeletableEntity(hole);
    }

    public List<Hole> getAllHolesOnCourse(Course course) {
        return getHolesByCourse(course);
    }

    public List<Hole> getHolesByCourse(Course course) {
        return holeRepository.findAllByCourseAndDeletedIsFalse(course);
    }
}
