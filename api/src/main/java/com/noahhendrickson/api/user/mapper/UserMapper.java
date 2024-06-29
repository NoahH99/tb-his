package com.noahhendrickson.api.user.mapper;

import com.noahhendrickson.api.user.dto.UserResponseDTO;
import com.noahhendrickson.api.user.entity.User;

public class UserMapper {

    public static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getEmail(),
                user.getAccountStatus(),
                user.getInitialHandicap(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
