package com.noahhendrickson.api.round.controller;

import com.noahhendrickson.api.common.CourseRoundFacade;
import com.noahhendrickson.api.round.dto.RoundRequestDTO;
import com.noahhendrickson.api.round.dto.RoundResponseDTO;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.mapper.RoundMapper;
import com.noahhendrickson.api.round.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rounds/{roundId}")
public class RoundControllerV1 {

    private final RoundService roundService;
    private final CourseRoundFacade courseRoundFacade;

    @Autowired
    public RoundControllerV1(RoundService roundService, CourseRoundFacade courseRoundFacade) {
        this.roundService = roundService;
        this.courseRoundFacade = courseRoundFacade;
    }

    @GetMapping
    public ResponseEntity<RoundResponseDTO> getRound(@PathVariable UUID roundId) {
        Round round = roundService.getRound(roundId);
        RoundResponseDTO responseDTO = RoundMapper.toRoundResponseDTO(round, courseRoundFacade.getHoleInfosAndScores(round));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RoundResponseDTO> updateRound(@PathVariable UUID roundId, @RequestBody RoundRequestDTO requestDTO) {
        Round round = roundService.updateRound(roundId, requestDTO);
        RoundResponseDTO responseDTO = RoundMapper.toRoundResponseDTO(round, courseRoundFacade.getHoleInfosAndScores(round));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRound(@PathVariable UUID roundId) {
        roundService.deleteRound(roundId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
