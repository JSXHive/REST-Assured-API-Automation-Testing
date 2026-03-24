//package com.api.automation.tests;
//
//public class PutUserTests {
//}


package com.api.automation.tests;

import com.api.automation.config.TestConfig;
import com.api.automation.models.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PUT User/Post API Tests")
public class PutUserTests extends TestConfig {

    @Test
    @Order(1)
    @DisplayName("PUT - Update entire post")
    public void testUpdatePost() {
        int postId = 1;

        Post updatedPost = Post.builder()
                .id(postId)
                .userId(1)
                .title("Updated Title")
                .body("This post has been completely updated via PUT")
                .build();

        Post responsePost = given()
                .pathParam("id", postId)
                .body(updatedPost)
                .when()
                .put("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .extract()
                .as(Post.class);

        // Verify the update
        assertEquals(updatedPost.getTitle(), responsePost.getTitle());
        assertEquals(updatedPost.getBody(), responsePost.getBody());

        System.out.println("✅ Post " + postId + " updated successfully");
    }

    @Test
    @Order(2)
    @DisplayName("PUT - Update user with Map")
    public void testUpdateUser() {
        int userId = 2;

        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("id", userId);
        updatedUser.put("name", "Jane Smith Updated");
        updatedUser.put("username", "janesmith_updated");
        updatedUser.put("email", "jane.updated@example.com");

        given()
                .pathParam("id", userId)
                .body(updatedUser)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Smith Updated"))
                .body("email", equalTo("jane.updated@example.com"));
    }

    @Test
    @Order(3)
    @DisplayName("PUT - Update non-existent resource")
    public void testUpdateNonExistentPost() {
        int nonExistentId = 999;

        Post post = Post.builder()
                .id(nonExistentId)
                .userId(1)
                .title("This should fail")
                .body("This post doesn't exist")
                .build();

        given()
                .pathParam("id", nonExistentId)
                .body(post)
                .when()
                .put("/posts/{id}")
                .then()
                .statusCode(500);  // JSONPlaceholder returns 500 for non-existent resources
        // In real APIs, this would likely be 404
    }
}