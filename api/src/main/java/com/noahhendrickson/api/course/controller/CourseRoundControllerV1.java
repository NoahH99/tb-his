package com.noahhendrickson.api.course.controller;

import com.noahhendrickson.api.common.CourseRoundFacade;
import com.noahhendrickson.api.round.dto.RoundRequestDTO;
import com.noahhendrickson.api.round.dto.RoundResponseDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.mapper.RoundMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses/{courseId}")
public class CourseRoundControllerV1 {

    private final CourseRoundFacade courseRoundFacade;

    @Autowired
    public CourseRoundControllerV1(CourseRoundFacade courseRoundFacade) {
        this.courseRoundFacade = courseRoundFacade;
    }

    @GetMapping("/rounds")
    public ResponseEntity<List<RoundResponseDTO>> getAllRoundsOnCourse(@PathVariable UUID courseId) {
        List<Round> rounds = courseRoundFacade.getAllRoundsOnCourse(courseId);
        List<RoundResponseDTO> responseDTOs = rounds.stream()
                .map(round -> RoundMapper.toRoundResponseDTO(round, courseRoundFacade.getHoleInfosAndScores(round)))
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/tees/{teeId}/rounds")
    public ResponseEntity<List<RoundResponseDTO>> getAllRoundsOnCourseForTee(@PathVariable UUID courseId, @PathVariable UUID teeId) {
        List<Round> rounds = courseRoundFacade.getAllRoundsOnCourseForTee(courseId, teeId);
        List<RoundResponseDTO> responseDTOs = rounds.stream()
                .map(round -> RoundMapper.toRoundResponseDTO(round, courseRoundFacade.getHoleInfosAndScores(round)))
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PostMapping("/tees/{teeId}/rounds")
    public ResponseEntity<RoundResponseDTO> createRoundOnCourseForTee(@PathVariable UUID courseId, @PathVariable UUID teeId, @Valid @RequestBody RoundRequestDTO requestDTO) {
        Round round = courseRoundFacade.createRoundOnCourseForTee(courseId, teeId, requestDTO);
        RoundResponseDTO responseDTO = RoundMapper.toRoundResponseDTO(round, courseRoundFacade.getHoleInfosAndScores(round));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
