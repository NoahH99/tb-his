package com.noahhendrickson.bot.service;

import com.noahhendrickson.bot.dto.ErrorResponse;
import com.noahhendrickson.bot.dto.UserRegistrationRequest;
import com.noahhendrickson.bot.dto.UserRegistrationResponse;
import com.noahhendrickson.bot.util.EmbedUtil;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final UserService userService;
    private final RestTemplate restTemplate;
    private final String apiUrl;

    @Autowired
    public RegistrationService(UserService userService, RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public MessageEmbed registerUser(User user, String firstName, String lastName, String email, Integer initialHandicap) {
        String endpoint = apiUrl + "/api/v1/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserRegistrationRequest request = new UserRegistrationRequest(firstName, lastName, email, initialHandicap);
        HttpEntity<UserRegistrationRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<UserRegistrationResponse> responseEntity = sendRequest(endpoint, entity);

            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                UserRegistrationResponse userRegistrationResponse = responseEntity.getBody();

                if (userRegistrationResponse != null) {
                    userService.createUser(user.getId(), userRegistrationResponse.getId());
                    return EmbedUtil.success("Successfully registered as **" + firstName + " " + lastName + "**.", userRegistrationResponse.getId().toString());
                }
            } else {
                ErrorResponse errorResponse = handleErrorResponse(responseEntity);
                return EmbedUtil.error(errorResponse.getMessage(), errorResponse.getErrorCode());
            }
        } catch (Exception exception) {
            return EmbedUtil.error(exception.getMessage());
        }

        throw new RuntimeException();
    }

    private ResponseEntity<UserRegistrationResponse> sendRequest(String endpoint, HttpEntity<UserRegistrationRequest> entity) {
        return restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                UserRegistrationResponse.class
        );
    }

    private ErrorResponse handleErrorResponse(ResponseEntity<?> responseEntity) {
        if (responseEntity.getBody() != null && responseEntity.getBody() instanceof ErrorResponse) {
            return (ErrorResponse) responseEntity.getBody();
        }

        throw new RuntimeException();
    }
}
