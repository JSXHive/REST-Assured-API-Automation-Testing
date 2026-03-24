//package com.api.automation.suites;
//
//public class AllTestsSuite {
//}


package com.api.automation.suites;

import com.api.automation.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Rest Assured API Test Suite")
@SelectClasses({
        GetUserTests.class,
        PostUserTests.class,
        PutUserTests.class,
        DeleteUserTests.class
})
public class AllTestsSuite {
    // This class remains empty, used only as a holder for the annotations
}

// Can also run using command "mvn clean test" or run this file