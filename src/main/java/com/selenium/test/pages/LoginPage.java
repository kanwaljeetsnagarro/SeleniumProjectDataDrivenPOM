package com.selenium.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage implements Page Object Model pattern for login page.
 * It encapsulates:
 * 1. Page elements - Using @FindBy for element location
 * 2. Page actions - Methods to interact with elements
 * 3. Page validations - Methods to verify page state
 */
public class LoginPage extends BasePage {
    // Page elements using @FindBy for maintainable element location
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    /**
     * Constructor initializes login page elements
     * 
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Performs login with username and password
     * 
     * @param username Username for login
     * @param password Password for login
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Checks if user is currently logged in
     * @return true if user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return !isOnLoginPage();
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        driver.get("https://www.saucedemo.com/logout");
        waitForElementVisible(usernameField);
    }

    /**
     * Enters username in the username field
     * 
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        type(usernameField, username);
    }

    /**
     * Enters password in the password field
     * 
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    /**
     * Clicks the login button
     */
    public void clickLoginButton() {
        click(loginButton);
    }

    /**
     * Gets the value of the username field
     * 
     * @return Current value of username field
     */
    public String getUsernameValue() {
        return usernameField.getAttribute("value");
    }

    /**
     * Gets the value of the password field
     * 
     * @return Current value of password field
     */
    public String getPasswordValue() {
        return passwordField.getAttribute("value");
    }

    /**
     * Gets error message if login fails
     * 
     * @return Error message text or empty string if no error
     */
    public String getErrorMessage() {
        return isDisplayed(errorMessage) ? getText(errorMessage) : "";
    }

    /**
     * Checks if error message is displayed
     * 
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Checks if currently on login page
     * 
     * @return true if URL contains login endpoint
     */
    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().equals(BASE_URL);
    }
}