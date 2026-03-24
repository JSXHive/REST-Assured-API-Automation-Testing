//package com.api.automation.tests;
//
//public class GetUserTests {
//}


package com.api.automation.tests;

import com.api.automation.config.TestConfig;
import com.api.automation.models.User;
import com.api.automation.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET User API Tests")
public class GetUserTests extends TestConfig {

    @BeforeEach
    public void setUp() {
        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    @Test
    @Order(1)
    @DisplayName("GET all users - Verify status code and response body")
    public void testGetAllUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))  // Verify list is not empty
                .body("id", everyItem(notNullValue()))  // Every user has an ID
                .body("name", everyItem(notNullValue()))  // Every user has a name
                .time(lessThan(TestUtils.MAX_RESPONSE_TIME));  // Response time check
    }

    @Test
    @Order(2)
    @DisplayName("GET user by ID - Verify specific user details")
    public void testGetUserById() {
        int userId = 1;

        User user = given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        // Verify user details
        assertEquals(userId, user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getAddress());
        assertNotNull(user.getCompany());

        // Log for debugging
        System.out.println("✅ Retrieved user: " + user.getName() + " (ID: " + user.getId() + ")");
    }

    @Test
    @Order(3)
    @DisplayName("GET user with invalid ID - Should return 404")
    public void testGetUserWithInvalidId() {
        int invalidUserId = 999;

        given()
                .pathParam("id", invalidUserId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404);  // Expect 404 Not Found
    }

    @Test
    @Order(4)
    @DisplayName("GET users - Deserialize response to List of Users")
    public void testGetUsersAsList() {
        Response response = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Deserialize to List<User>
        List<User> users = response.as(new TypeRef<List<User>>() {});

        // Verify the list
        assertFalse(users.isEmpty());
        assertTrue(users.size() > 5);  // JSONPlaceholder has 10 users

        // Print first user for debugging
        if (!users.isEmpty()) {
            User firstUser = users.get(0);
            System.out.println("📊 First user: " + firstUser.getName() +
                    " from " + firstUser.getAddress().getCity());
        }
    }
}