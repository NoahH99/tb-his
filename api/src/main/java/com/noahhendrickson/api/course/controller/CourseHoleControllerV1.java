package com.noahhendrickson.api.course.controller;

import com.noahhendrickson.api.course.CourseFacade;
import com.noahhendrickson.api.course.dto.CourseHoleRequestDTO;
import com.noahhendrickson.api.course.dto.CourseHoleResponseDTO;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.mapper.HoleMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/holes")
public class CourseHoleControllerV1 {

    private final CourseFacade courseFacade;

    @Autowired
    public CourseHoleControllerV1(CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    @GetMapping
    public ResponseEntity<List<CourseHoleResponseDTO>> getAllHoles(@PathVariable UUID courseId) {
        List<Hole> holes = courseFacade.getAllHolesOnCourse(courseId);
        List<CourseHoleResponseDTO> responseDTOs = holes.stream().map(HoleMapper::toHoleResponseDTO).toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseHoleResponseDTO> createHole(@PathVariable UUID courseId, @Valid @RequestBody CourseHoleRequestDTO requestDTO) {
        Hole hole = courseFacade.createHole(courseId, requestDTO);
        CourseHoleResponseDTO responseDTO = HoleMapper.toHoleResponseDTO(hole);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{holeId}")
    public ResponseEntity<CourseHoleResponseDTO> getHole(@PathVariable UUID courseId, @PathVariable UUID holeId) {
        Hole hole = courseFacade.getHole(courseId, holeId);
        CourseHoleResponseDTO responseDTO = HoleMapper.toHoleResponseDTO(hole);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{holeId}")
    public ResponseEntity<CourseHoleResponseDTO> updateHole(@PathVariable UUID courseId, @PathVariable UUID holeId, @RequestBody CourseHoleRequestDTO requestDTO) {
        Hole hole = courseFacade.updateHole(courseId, holeId, requestDTO);
        CourseHoleResponseDTO responseDTO = HoleMapper.toHoleResponseDTO(hole);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{holeId}")
    public ResponseEntity<Void> deleteHole(@PathVariable UUID courseId, @PathVariable UUID holeId) {
        courseFacade.deleteHole(courseId, holeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
