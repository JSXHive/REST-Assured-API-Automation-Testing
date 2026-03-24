//package com.api.automation.utils;
//
//public class TestUtils {
//}


package com.api.automation.utils;

import java.util.concurrent.TimeUnit;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThan;

public class TestUtils {

    public static final long MAX_RESPONSE_TIME = 2000; // 2 seconds

    // Response time assertion helper
    public static void assertResponseTime(long responseTime) {
        if (responseTime > MAX_RESPONSE_TIME) {
            System.out.println("⚠️ Warning: Response time " + responseTime +
                    "ms exceeded threshold of " + MAX_RESPONSE_TIME + "ms");
        }
    }

    // Generate unique email for tests
    public static String generateUniqueEmail() {
        return "testuser" + System.currentTimeMillis() + "@example.com";
    }
}