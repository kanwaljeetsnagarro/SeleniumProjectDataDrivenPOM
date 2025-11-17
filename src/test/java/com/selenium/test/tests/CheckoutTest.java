package com.selenium.test.tests;

import com.selenium.test.pages.CheckoutPage;
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
 * CheckoutTest demonstrates testing checkout functionality with data-driven approach.
 * Key concepts demonstrated:
 * 1. Testing checkout without login (security test)
 * 2. Verification of user redirection to login page
 * 3. Testing checkout with valid login
 * 4. Page Object Model usage in tests
 */
public class CheckoutTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutTest.class);
    private CheckoutPage checkoutPage;
    private LoginPage loginPage;
    private WebDriverWait wait;

    /**
     * Validates that all required fields are present in the test data
     * @param testData Map containing test data
     * @throws IllegalArgumentException if required fields are missing
     */
    private void validateTestData(Map<String, String> testData) {
        String[] requiredFields = {"testCase", "username", "password", "firstName", 
                                   "lastName", "postalCode", "expectedResult"};
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
     * 
     * @return Object array containing test data maps
     */
    @DataProvider(name = "checkoutTestData")
    public Object[][] getCheckoutTestData() {
        return ExcelDataProvider.getTestData("Checkout");
    }

    /**
     * Tests the checkout functionality with various test cases including:
     * - Checkout without login (should redirect to login)
     * - Checkout with valid login
     * 
     * @param testData Map containing:
     *                 - testCase: Description of the test case
     *                 - username: Username to test (empty for no login)
     *                 - password: Password to test (empty for no login)
     *                 - firstName: First name for checkout
     *                 - lastName: Last name for checkout
     *                 - postalCode: Postal code for checkout
     *                 - expectedResult: Expected outcome
     */
    @Test(dataProvider = "checkoutTestData", description = "Verify checkout functionality with missing username")
    public void testCheckoutWithMissingUsername(Map<String, String> testData) {
        try {
            // Validate test data
            validateTestData(testData);

            // Extract test data from the Map
            String testCase = testData.get("testCase");
            String username = testData.get("username");
            String password = testData.get("password");
            String firstName = testData.get("firstName");
            String lastName = testData.get("lastName");
            String postalCode = testData.get("postalCode");
            String expectedResult = testData.get("expectedResult");

            logger.info("Executing checkout test case: {}", testCase);

            // Initialize wait and page objects
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            checkoutPage = new CheckoutPage(driver);
            loginPage = new LoginPage(driver);

            // Test scenario based on username presence
            if (username.isEmpty()) {
                // Test Case: Attempt checkout without login
                logger.info("Testing checkout without login - expecting redirect to login page");
                
                // Navigate directly to checkout URL without logging in
                driver.get(BASE_URL + "/checkout-step-one.html");
                wait.until(ExpectedConditions.urlMatches(".*(\\/|inventory\\.html|checkout).*"));
                
                // Verify redirect to login page
                if (expectedResult.equals("redirect_to_login")) {
                    Assert.assertTrue(checkoutPage.isOnLoginPage(),
                            String.format("Test case '%s' failed. User should be redirected to login page when accessing checkout without login. Current URL: %s",
                                    testCase, driver.getCurrentUrl()));
                    logger.info("✓ User correctly redirected to login page");
                    
                    // Verify login page elements are present
                    Assert.assertTrue(loginPage.isOnLoginPage(),
                            String.format("Test case '%s' failed. Should be on login page", testCase));
                    logger.info("✓ Login page elements are present");
                }
            } else {
                // Test Case: Checkout with valid login
                logger.info("Testing checkout with valid login");
                
                // Navigate to login page and login
                driver.get(BASE_URL);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
                loginPage.login(username, password);
                
                // Wait for successful login - should be on inventory page
                wait.until(ExpectedConditions.urlContains("inventory"));
                logger.info("✓ Successfully logged in");
                
                // Add an item to cart first
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
                driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
                logger.info("✓ Item added to cart");
                
                // Navigate to cart
                driver.findElement(By.className("shopping_cart_link")).click();
                wait.until(ExpectedConditions.urlContains("cart"));
                logger.info("✓ Navigated to cart");
                
                // Proceed to checkout
                wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
                driver.findElement(By.id("checkout")).click();
                wait.until(ExpectedConditions.urlContains("checkout-step-one"));
                logger.info("✓ Navigated to checkout page");
                
                // Verify on checkout page
                Assert.assertTrue(checkoutPage.isOnCheckoutPage(),
                        String.format("Test case '%s' failed. Should be on checkout page", testCase));
                
                // Fill checkout information
                checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
                checkoutPage.clickContinue();
                logger.info("✓ Filled checkout information and clicked continue");
                
                // Verify results
                if (expectedResult.equals("success")) {
                    wait.until(ExpectedConditions.urlContains("checkout-step-two"));
                    Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
                            String.format("Test case '%s' failed. Should proceed to checkout step two", testCase));
                    logger.info("✓ Successfully proceeded to checkout step two");
                }
            }
            
            logger.info("Test case '{}' completed successfully", testCase);
        } catch (Exception e) {
            logger.error("Test failed: {}", e.getMessage(), e);
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Cleanup method that runs after each test method
     * Ensures proper cleanup of resources and state
     */
    @AfterMethod
    public void cleanupTest() {
        try {
            if (driver != null && loginPage != null) {
                // Check if user is logged in by checking URL
                String currentUrl = driver.getCurrentUrl();
                if (!currentUrl.equals(BASE_URL) && !currentUrl.equals(BASE_URL + "/")) {
                    logger.info("Performing test cleanup - logging out");
                    driver.get(BASE_URL);
                }
            }
        } catch (Exception e) {
            logger.warn("Cleanup encountered an error: {}", e.getMessage());
        }
    }
}
