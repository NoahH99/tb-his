package com.noahhendrickson.api.user.controller;

import com.noahhendrickson.api.common.CourseRoundFacade;
import com.noahhendrickson.api.round.dto.RoundResponseDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.mapper.RoundMapper;
import com.noahhendrickson.api.user.dto.UserCreateRequestDTO;
import com.noahhendrickson.api.user.dto.UserResponseDTO;
import com.noahhendrickson.api.user.dto.UserUpdateRequestDTO;
import com.noahhendrickson.api.user.entity.User;
import com.noahhendrickson.api.user.mapper.UserMapper;
import com.noahhendrickson.api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private final UserService userService;
    private final CourseRoundFacade courseRoundFacade;

    @Autowired
    public UserControllerV1(UserService userService, CourseRoundFacade courseRoundFacade) {
        this.userService = userService;
        this.courseRoundFacade = courseRoundFacade;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO requestDTO) {
        User user = userService.createUser(requestDTO);
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(user);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID userId) {
        User user = userService.getUser(userId);
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(user);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserUpdateRequestDTO requestDTO) {
        User user = userService.updateUser(userId, requestDTO);
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(user);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/rounds")
    public ResponseEntity<List<RoundResponseDTO>> getUserRounds(@PathVariable UUID userId) {
        List<Round> rounds = userService.getUserRounds(userId);
        List<RoundResponseDTO> roundResponseDTOList = rounds.stream().map(round -> RoundMapper.toRoundResponseDTO(round, courseRoundFacade.getHoleInfosAndScores(round))).toList();

        return new ResponseEntity<>(roundResponseDTOList, HttpStatus.OK);
    }
}
