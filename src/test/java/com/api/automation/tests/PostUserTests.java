//package com.api.automation.tests;
//
//public class PostUserTests {
//}


package com.api.automation.tests;

import com.api.automation.config.TestConfig;
import com.api.automation.models.Post;
import com.api.automation.utils.TestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST User/Post API Tests")
public class PostUserTests extends TestConfig {

    @Test
    @Order(1)
    @DisplayName("POST - Create new post with POJO")
    public void testCreatePostWithPojo() {
        // Create Post object using Builder pattern
        Post newPost = Post.builder()
                .userId(1)
                .title("Test Post Title")
                .body("This is the body of the test post created via Rest Assured")
                .build();

        Post createdPost = given()
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // 201 Created
                .body("id", notNullValue())
                .extract()
                .as(Post.class);

        // Verify the response
        assertNotNull(createdPost.getId());
        assertEquals(newPost.getUserId(), createdPost.getUserId());
        assertEquals(newPost.getTitle(), createdPost.getTitle());
        assertEquals(newPost.getBody(), createdPost.getBody());

        System.out.println("✅ Post created with ID: " + createdPost.getId());
    }

    @Test
    @Order(2)
    @DisplayName("POST - Create new post using Map")
    public void testCreatePostWithMap() {
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("userId", 2);
        postMap.put("title", "Post from Map");
        postMap.put("body", "This post was created using a HashMap");

        given()
                .contentType(ContentType.JSON)
                .body(postMap)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("userId", equalTo(2))
                .body("title", equalTo("Post from Map"))
                .body("body", equalTo("This post was created using a HashMap"));
    }

    @Test
    @Order(3)
    @DisplayName("POST - Create user with unique email")
    public void testCreateUser() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "John Doe");
        userMap.put("username", "johndoe");
        userMap.put("email", TestUtils.generateUniqueEmail());  // Unique email

        given()
                .body(userMap)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("John Doe"))
                .body("email", containsString("@example.com"));
    }

    @Test
    @Order(4)
    @DisplayName("POST - Negative test with empty body")
    public void testCreatePostWithEmptyBody() {
        given()
                .body("{}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // JSONPlaceholder still returns 201 even with empty body
                .body("id", notNullValue());
        // Note: In real APIs, this should be 400 Bad Request
    }
}