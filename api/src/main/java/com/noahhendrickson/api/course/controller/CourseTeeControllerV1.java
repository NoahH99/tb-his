package com.noahhendrickson.api.course.controller;

import com.noahhendrickson.api.course.CourseFacade;
import com.noahhendrickson.api.course.dto.CourseTeeRequestDTO;
import com.noahhendrickson.api.course.dto.CourseTeeResponseDTO;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.course.mapper.TeeMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/tees")
public class CourseTeeControllerV1 {

    private final CourseFacade courseFacade;

    @Autowired
    public CourseTeeControllerV1(CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    @GetMapping
    public ResponseEntity<List<CourseTeeResponseDTO>> getAllTees(@PathVariable UUID courseId) {
        List<Tee> tees = courseFacade.getAllTees();
        List<CourseTeeResponseDTO> responseDTOs = tees.stream().map(TeeMapper::toTeeResponseDTO).toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseTeeResponseDTO> createTee(@PathVariable UUID courseId, @Valid @RequestBody CourseTeeRequestDTO requestDTO) {
        Tee tee = courseFacade.createTee(courseId, requestDTO);
        CourseTeeResponseDTO responseDTO = TeeMapper.toTeeResponseDTO(tee);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{teeId}")
    public ResponseEntity<CourseTeeResponseDTO> getTee(@PathVariable UUID courseId, @PathVariable UUID teeId) {
        Tee tee = courseFacade.getTee(courseId, teeId);
        CourseTeeResponseDTO responseDTO = TeeMapper.toTeeResponseDTO(tee);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{teeId}")
    public ResponseEntity<CourseTeeResponseDTO> updateTee(@PathVariable UUID courseId, @PathVariable UUID teeId, @RequestBody CourseTeeRequestDTO requestDTO) {
        Tee tee = courseFacade.updateTee(courseId, teeId, requestDTO);
        CourseTeeResponseDTO responseDTO = TeeMapper.toTeeResponseDTO(tee);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{teeId}")
    public ResponseEntity<Void> deleteTee(@PathVariable UUID courseId, @PathVariable UUID teeId) {
        courseFacade.deleteTee(courseId, teeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
