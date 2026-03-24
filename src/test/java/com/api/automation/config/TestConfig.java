//package com.api.automation.config;
//
//public class TestConfig {
//}

package com.api.automation.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public class TestConfig {

    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    @BeforeAll
    public static void setup() {
        // Base URL for JSONPlaceholder API
        String baseUrl = "https://jsonplaceholder.typicode.com";

        // Build Request Specification
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(baseUrl);
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.log(LogDetail.ALL); // Log all request details
        requestSpecification = requestBuilder.build();

        // Build Response Specification
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectContentType(ContentType.JSON);
        responseBuilder.log(LogDetail.ALL); // Log all response details
        responseSpecification = responseBuilder.build();
    }
}