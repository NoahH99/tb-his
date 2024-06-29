package com.noahhendrickson.api.user.controller;

import com.noahhendrickson.api.PostgresTestContainer;
import com.noahhendrickson.api.user.dto.UserCreateRequestDTO;
import com.noahhendrickson.api.user.entity.AccountStatus;
import com.noahhendrickson.api.user.entity.User;
import com.noahhendrickson.api.user.repository.UserRepository;
import com.noahhendrickson.api.util.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerV1Test extends PostgresTestContainer {

    @LocalServerPort
    int port;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser_WithValidData_ReturnsCreatedUser() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body("id", matchesRegex(Constants.UUID_REGEX))
                .body("firstName", equalTo("John"))
                .body("fullName", nullValue())
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .body("accountStatus", equalTo("ACTIVE"))
                .body("initialHandicap", equalTo(0))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testCreateUser_WithMissingRequiredFields_ReturnsBadRequest() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(null, null, null, null, null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testCreateUser_WithEmptyFirstName_ReturnsBadRequest() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testCreateUser_WithLongFirstName_ReturnsBadRequest() {
        String longFirstName = "VeryLongFirstNameThatExceedsSeventyFiveCharactersLimitForValidationTestingVeryLongFirstNameThatExceedsSeventyFiveCharactersLimitForValidationTesting";
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(longFirstName, "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testCreateUser_WithInvalidEmail_ReturnsBadRequest() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("Joe", "Doe", "invalid-email", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testCreateUser_WithDuplicateEmail_ReturnsBadRequest() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        User user = new User(
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getEmail(),
                requestDTO.getAccountStatus(),
                requestDTO.getInitialHandicap()
        );
        userRepository.save(user);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testCreateUser_WithInvalidAccountStatus_ReturnsBadRequest() {

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("""
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "john.doe@example.com",
                            "accountStatus": "SOME_RANDOM_STRING",
                            "initialHandicap": 0
                        }
                        """)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testGetUser_WithExistingUser_ReturnsUser() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/users/" + user.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("id", equalTo(user.getId().toString()))
                .body("firstName", equalTo("John"))
                .body("fullName", equalTo("John Doe"))
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .body("accountStatus", equalTo("ACTIVE"))
                .body("initialHandicap", equalTo(0))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testGetUser_WithNonExistingUser_ReturnsBadRequest() {
        UUID nonExistingUserId = UUID.randomUUID();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/users/" + nonExistingUserId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testGetUser_WithInvalidUserIDFormat_ReturnsBadRequest() {
        String invalidFormattedUserId = "not-a-uuid";

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/users/" + invalidFormattedUserId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testUpdateUser_WithValidData_ReturnsUpdateUser() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("Jane", "Doe", "jane.doe@example.com", null, null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .put("/api/v1/users/" + user.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("id", matchesRegex(Constants.UUID_REGEX))
                .body("firstName", equalTo("Jane"))
                .body("fullName", notNullValue())
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("jane.doe@example.com"))
                .body("accountStatus", equalTo("ACTIVE"))
                .body("initialHandicap", equalTo(0))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testUpdateUser_WithNoFields_ReturnsOkRequest() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(null, null, null, null, null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .put("/api/v1/users/" + user.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("id", matchesRegex(Constants.UUID_REGEX))
                .body("firstName", equalTo("John"))
                .body("fullName", notNullValue())
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .body("accountStatus", equalTo("ACTIVE"))
                .body("initialHandicap", equalTo(0))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testUpdateUser_WithEmptyLastName_ReturnsBadRequest() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("John", "", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .put("/api/v1/users/" + user.getId().toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testUpdateUser_WithLongFirstName_ReturnsBadRequest() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        String longFirstName = "VeryLongFirstNameThatExceedsSeventyFiveCharactersLimitForValidationTestingVeryLongFirstNameThatExceedsSeventyFiveCharactersLimitForValidationTesting";
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(longFirstName, "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .put("/api/v1/users/" + user.getId().toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testUpdateUser_WithInvalidEmail_ReturnsBadRequest() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("Joe", "Doe", "invalid-email", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .put("/api/v1/users/" + user.getId().toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testUpdateUser_WithDuplicateEmail_ReturnsBadRequest() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDTO)
                .when()
                .put("/api/v1/users/" + user.getId().toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testUpdateUser_WithInvalidAccountStatus_ReturnsBadRequest() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("""
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "john.doe@example.com",
                            "accountStatus": "SOME_RANDOM_STRING"
                        }
                        """)
                .when()
                .put("/api/v1/users/" + user.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testDeleteUser_WithExistingUser_ReturnsOk() {
        User userToSave = new User("John", "Doe", "john.doe@example.com", AccountStatus.ACTIVE, 0);
        User user = userRepository.save(userToSave);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/v1/users/" + user.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testDeleteUser_WithNonExistingUser_ReturnsBadRequest() {
        UUID nonExistingUserId = UUID.randomUUID();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/v1/users/" + nonExistingUserId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }

    @Test
    public void testDeleteUser_WithInvalidUserIDFormat_ReturnsBadRequest() {
        String invalidFormattedUserId = "not-a-uuid";

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/v1/users/" + invalidFormattedUserId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", notNullValue())
                .body("errorCode", notNullValue());
    }
}