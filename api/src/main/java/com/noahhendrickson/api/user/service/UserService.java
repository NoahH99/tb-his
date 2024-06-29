package com.noahhendrickson.api.user.service;

import com.noahhendrickson.api.common.event.SoftDeleteDeletableEntityEventPublisher;
import com.noahhendrickson.api.common.exception.IllegalFieldException;
import com.noahhendrickson.api.common.exception.UserNotFoundException;
import com.noahhendrickson.api.round.entity.Round;
import com.noahhendrickson.api.round.service.RoundService;
import com.noahhendrickson.api.user.dto.UserCreateRequestDTO;
import com.noahhendrickson.api.user.dto.UserUpdateRequestDTO;
import com.noahhendrickson.api.user.entity.AccountStatus;
import com.noahhendrickson.api.user.entity.User;
import com.noahhendrickson.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoundService roundService;
    private final SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher;

    @Autowired
    public UserService(UserRepository userRepository, RoundService roundService, SoftDeleteDeletableEntityEventPublisher softDeleteDeletableEntityEventPublisher) {
        this.userRepository = userRepository;
        this.roundService = roundService;
        this.softDeleteDeletableEntityEventPublisher = softDeleteDeletableEntityEventPublisher;
    }

    public User createUser(UserCreateRequestDTO requestDTO) {
        User user = new User(
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getEmail(),
                requestDTO.getAccountStatus() == null ? AccountStatus.ACTIVE : requestDTO.getAccountStatus(),
                requestDTO.getInitialHandicap()
        );

        return userRepository.save(user);
    }

    public User getUser(UUID userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User updateUser(UUID userId, UserUpdateRequestDTO requestDTO) {
        User user = getUser(userId);

        if (requestDTO.getFirstName() != null) {
            int firstNameLength = requestDTO.getFirstName().length();
            if (firstNameLength >= 1 && firstNameLength <= 75) user.setFirstName(requestDTO.getFirstName());
            else throw new IllegalFieldException("'firstName' must be between 1 and 75 characters inclusive");
        }
        if (requestDTO.getLastName() != null) {
            int lastNameLength = requestDTO.getLastName().length();
            if (lastNameLength >= 1 && lastNameLength <= 75) user.setLastName(requestDTO.getLastName());
            else throw new IllegalFieldException("'lastName' must be between 1 and 75 characters inclusive");
        }
        if (requestDTO.getEmail() != null) user.setEmail(requestDTO.getEmail());
        if (requestDTO.getAccountStatus() != null) user.setAccountStatus(requestDTO.getAccountStatus());

        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        User user = getUser(userId);
        softDeleteDeletableEntityEventPublisher.deleteDeletableEntity(user);
    }

    public List<Round> getUserRounds(UUID userId) {
        User user = getUser(userId);

        return roundService.getAllRoundsOnCourseForUser(user);
    }
}
