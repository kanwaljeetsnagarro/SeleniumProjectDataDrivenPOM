package com.selenium.test.tests;

import com.selenium.test.pages.LoginPage;
import com.selenium.test.utils.ExcelDataProvider;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Map;

/**
 * LoginTest demonstrates data-driven testing with TestNG and Excel data.
 * Key concepts demonstrated:
 * 1. Data-driven testing using @DataProvider
 * 2. Excel data source integration
 * 3. Page Object Model usage in tests
 * 4. TestNG test organization and assertions
 */
public class LoginTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;
    private WebDriverWait wait;

    /**
     * Validates that all required fields are present in the test data
     * @param testData Map containing test data
     * @throws IllegalArgumentException if required fields are missing
     */
    private void validateTestData(Map<String, String> testData) {
        String[] requiredFields = {"testCase", "username", "password", "expectedResult"};
        for (String field : requiredFields) {
            if (testData.get(field) == null) {
                String errorMsg = "Test data missing required field: " + field;
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
        }
    }

    /**
     * Data provider method that supplies test data from Excel
     * Benefits of external test data:
     * 1. Separation of test data from test logic
     * 2. Easy maintenance of test cases
     * 3. Non-technical users can modify test data
     * 4. Support for large number of test cases
     * 
     * @return Object array containing test data maps
     */
    @DataProvider(name = "loginTestData")
    public Object[][] getLoginTestData() {
        // Get test data from Excel sheet named "Login"
        // Returns array of Maps, each containing a test case
        return ExcelDataProvider.getTestData("Login");
    }

    /**
     * Tests the login functionality with various test cases
     * 
     * @param testData Map containing:
     *                 - testCase: Description of the test case
     *                 - username: Username to test
     *                 - password: Password to test
     *                 - expectedResult: Expected outcome
     * 
     *                 This test demonstrates:
     *                 1. Page Object Model usage
     *                 2. Data-driven testing with TestNG
     *                 3. Proper test organization and assertions
     *                 4. Handling different test scenarios with same code
     */
    @Test(dataProvider = "loginTestData")
    public void testLogin(Map<String, String> testData) {
        try {
            // Validate test data
            validateTestData(testData);

            // Extract test data from the Map
            String testCase = testData.get("testCase");
            String username = testData.get("username");
            String password = testData.get("password");
            String expectedResult = testData.get("expectedResult");

            logger.info("Executing login test case: {}", testCase);

            // Initialize wait and page objects
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            loginPage = new LoginPage(driver);

            // Navigate to login page
            driver.get(BASE_URL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

            // Perform login
            loginPage.login(username, password);

            // Verify results
            if (expectedResult.equals("success")) {
                Assert.assertFalse(loginPage.isOnLoginPage(),
                        String.format("Login failed for test case '%s'. Expected successful login.", testCase));
            } else {
                String actualError = loginPage.getErrorMessage();
                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                        String.format("Error message not displayed for test case '%s'", testCase));
                Assert.assertEquals(actualError, expectedResult,
                        String.format("Error message mismatch for test case '%s'. Expected: '%s', Actual: '%s'",
                                testCase, expectedResult, actualError));
            }
            
            logger.info("Test case '{}' completed successfully", testCase);
        } catch (Exception e) {
            logger.error("Test failed: {}", e.getMessage(), e);
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Tests individual field validation during login
     * 
     * @param testData Map containing:
     *                 - testCase: Description of the test case
     *                 - username: Username to test
     *                 - password: Password to test
     * 
     *                 This test demonstrates:
     *                 1. Field-level validation
     *                 2. Page Object Model usage
     *                 3. Data-driven testing with TestNG
     *                 4. Proper test organization and assertions
     * @throws InterruptedException 
     */
    @Test(dataProvider = "loginTestData")
    public void testLoginFieldValidation(Map<String, String> testData) {
        try {
            // Validate test data
            validateTestData(testData);

            // Extract test data from the Map
            String testCase = testData.get("testCase");
            String username = testData.get("username");
            String password = testData.get("password");

            logger.info("Executing field validation for test case: {}", testCase);

            // Initialize wait and page objects
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            loginPage = new LoginPage(driver);

            // Navigate to the login page
            driver.get(BASE_URL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

            // Test individual field inputs
            loginPage.enterUsername(username);
            loginPage.enterPassword(password);

            // Validate input fields have correct values
            String actualUsername = loginPage.getUsernameValue();
            String actualPassword = loginPage.getPasswordValue();

            Assert.assertEquals(
                    actualUsername,
                    username,
                    String.format("Username mismatch for test case '%s'. Expected: '%s', Actual: '%s'",
                            testCase, username, actualUsername));
            Assert.assertEquals(
                    actualPassword,
                    password,
                    String.format("Password mismatch for test case '%s'. Expected: '%s', Actual: '%s'",
                            testCase, password, actualPassword));

            logger.info("Field validation completed successfully for test case: {}", testCase);
        } catch (Exception e) {
            logger.error("Field validation failed: {}", e.getMessage(), e);
            Assert.fail("Field validation failed: " + e.getMessage());
        }
    }

    /**
     * Cleanup method that runs after each test method
     * Ensures proper cleanup of resources and state
     */
    @AfterMethod
    public void cleanupTest() {
        try {
            if (driver != null && loginPage != null && loginPage.isLoggedIn()) {
                logger.info("Performing test cleanup - logging out");
                loginPage.logout();
            }
        } catch (Exception e) {
            logger.warn("Cleanup encountered an error: {}", e.getMessage());
        }
    }
}