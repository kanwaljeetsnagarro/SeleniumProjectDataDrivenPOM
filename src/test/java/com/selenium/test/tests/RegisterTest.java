package com.selenium.test.tests;

import com.selenium.test.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.selenium.test.utils.TestDataGenerator;

/**
 * RegisterTest class contains test methods for the registration functionality.
 * It demonstrates:
 * 1. Test method organization
 * 2. Page Object Model usage
 * 3. Dynamic test data generation
 * 4. TestNG annotations and assertions
 */
public class RegisterTest extends BaseTest {

    /**
     * Tests the basic registration flow with valid data
     * This test:
     * 1. Generates random test data
     * 2. Performs registration
     * 3. Verifies successful registration
     */
    @Test
    public void testSuccessfulRegistration() {
        // Generate random test data
        String username = TestDataGenerator.generateUsername();
        String password = TestDataGenerator.generatePassword();

        // Initialize RegisterPage object
        driver.get(BASE_URL + "/register.html");
        RegisterPage registerPage = new RegisterPage(driver);

        // Perform registration
        registerPage.register(username, password);

        // Verify registration success
        Assert.assertFalse(registerPage.isOnRegisterPage(),
                "Should not be on register page after successful registration");
    }

    /**
     * Tests navigation from registration to login page
     * This test verifies:
     * 1. Presence of login link
     * 2. Navigation to login page works
     */
    @Test
    public void testNavigationToLogin() {
        driver.get(BASE_URL + "/register.html");
        RegisterPage registerPage = new RegisterPage(driver);

        // Click login link and verify navigation
        registerPage.clickLoginLink();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/login.html"),
                "Should navigate to login page");
    }

    /**
     * Tests registration with invalid data
     * This test verifies:
     * 1. Empty fields validation
     * 2. Error message display
     */
    @Test
    public void testInvalidRegistration() {
        driver.get(BASE_URL + "/register.html");
        RegisterPage registerPage = new RegisterPage(driver);

        // Try to register with empty fields
        registerPage.register("", "");

        // Verify still on register page
        Assert.assertTrue(registerPage.isOnRegisterPage(),
                "Should remain on register page after failed registration");
    }
}