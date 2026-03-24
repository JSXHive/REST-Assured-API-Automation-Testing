//package com.api.automation.tests;
//
//public class DeleteUserTests {
//}


package com.api.automation.tests;

import com.api.automation.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("DELETE API Tests")
public class DeleteUserTests extends TestConfig {

    @Test
    @Order(1)
    @DisplayName("DELETE - Delete existing post")
    public void testDeletePost() {
        int postId = 1;

        given()
                .pathParam("id", postId)
                .when()
                .delete("/posts/{id}")
                .then()
                .statusCode(200);  // JSONPlaceholder returns 200 on successful delete
        // Note: Some APIs return 204 No Content

        System.out.println("✅ Post " + postId + " deleted successfully");
    }

    @Test
    @Order(2)
    @DisplayName("DELETE - Delete non-existent resource")
    public void testDeleteNonExistentPost() {
        int nonExistentId = 999;

        given()
                .pathParam("id", nonExistentId)
                .when()
                .delete("/posts/{id}")
                .then()
                .statusCode(200);  // JSONPlaceholder still returns 200
        // In real APIs, this should return 404
    }

    @Test
    @Order(3)
    @DisplayName("DELETE - Delete user")
    public void testDeleteUser() {
        int userId = 2;

        given()
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(200);
    }
}