package com.selenium.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.selenium.test.utils.TestDataGenerator;

/**
 * RegisterPage implements Page Object Model pattern for registration page.
 * It encapsulates:
 * 1. Page elements - Using @FindBy for element location
 * 2. Page actions - Methods to interact with elements
 * 3. Page validations - Methods to verify page state
 */
public class RegisterPage extends BasePage {
    // Page elements using @FindBy for maintainable element location
    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "confirm-password")
    private WebElement confirmPasswordField;

    @FindBy(id = "register-button")
    private WebElement registerButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(css = "p > a[href='login.html']")
    private WebElement loginLink;

    /**
     * Constructor initializes register page elements
     * 
     * @param driver WebDriver instance
     */
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to registration page
     * 
     * @return RegisterPage instance
     */
    public RegisterPage navigateToRegisterPage() {
        driver.get(BASE_URL + "/register");
        return this;
    }

    /**
     * Register a new user
     * 
     * @param firstName       First name of the user
     * @param lastName        Last name of the user
     * @param email           Email address
     * @param password        Password
     * @param confirmPassword Confirm password
     */
    public void register(String firstName, String lastName, String email,
            String password, String confirmPassword) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(passwordField, password);
        type(confirmPasswordField, confirmPassword);
        click(registerButton);
    }

    /**
     * Simplified register method for basic registration
     * 
     * @param username Username for registration
     * @param password Password for registration
     */
    public void register(String username, String password) {
        register(username, username, TestDataGenerator.generateEmail(), password, password);
    }

    /**
     * Clicks the login link
     */
    public void clickLoginLink() {
        click(loginLink);
    }

    /**
     * Get error message if registration fails
     * 
     * @return Error message text or empty string if no error
     */
    public String getErrorMessage() {
        return isDisplayed(errorMessage) ? getText(errorMessage) : "";
    }

    /**
     * Check if registration page is displayed
     * 
     * @return true if registration page is displayed
     */
    public boolean isRegisterPageDisplayed() {
        return isDisplayed(registerButton);
    }

    /**
     * Checks if currently on register page
     * 
     * @return true if URL contains register.html
     */
    public boolean isOnRegisterPage() {
        return driver.getCurrentUrl().contains("register.html");
    }
}