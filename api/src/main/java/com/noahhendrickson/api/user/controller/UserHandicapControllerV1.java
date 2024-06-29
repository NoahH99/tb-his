package com.noahhendrickson.api.user.controller;

import com.noahhendrickson.api.user.dto.UserHandicapForCourseResponseDTO;
import com.noahhendrickson.api.user.dto.UserHandicapResponseDTO;
import com.noahhendrickson.api.user.service.HandicapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/handicap")
public class UserHandicapControllerV1 {

    private final HandicapService handicapService;

    @Autowired
    public UserHandicapControllerV1(HandicapService handicapService) {
        this.handicapService = handicapService;
    }

    @GetMapping
    public ResponseEntity<UserHandicapResponseDTO> getUserHandicap(@PathVariable UUID userId) {
        UserHandicapResponseDTO handicap = handicapService.getUserHandicap(userId);

        return new ResponseEntity<>(handicap, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<UserHandicapForCourseResponseDTO> getUserHandicapForCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
        UserHandicapForCourseResponseDTO handicap = handicapService.getUserHandicapForCourse(userId, courseId);

        return new ResponseEntity<>(handicap, HttpStatus.OK);
    }

}
