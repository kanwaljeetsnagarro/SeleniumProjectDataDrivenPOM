package com.selenium.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * CheckoutPage implements Page Object Model pattern for checkout page.
 * It encapsulates:
 * 1. Page elements - Using @FindBy for element location
 * 2. Page actions - Methods to interact with elements
 * 3. Page validations - Methods to verify page state
 */
public class CheckoutPage extends BasePage {
    // Checkout form elements
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    /**
     * Constructor initializes checkout page elements
     * 
     * @param driver WebDriver instance
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fills checkout information form
     * 
     * @param firstName First name
     * @param lastName Last name
     * @param postalCode Postal code
     */
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    /**
     * Enters first name in the first name field
     * 
     * @param firstName First name to enter
     */
    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    /**
     * Enters last name in the last name field
     * 
     * @param lastName Last name to enter
     */
    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    /**
     * Enters postal code in the postal code field
     * 
     * @param postalCode Postal code to enter
     */
    public void enterPostalCode(String postalCode) {
        type(postalCodeField, postalCode);
    }

    /**
     * Clicks the continue button
     */
    public void clickContinue() {
        click(continueButton);
    }

    /**
     * Clicks the cancel button
     */
    public void clickCancel() {
        click(cancelButton);
    }

    /**
     * Gets error message if checkout fails
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
     * Checks if currently on checkout page
     * 
     * @return true if URL contains checkout
     */
    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains("checkout");
    }

    /**
     * Checks if redirected to login page
     * 
     * @return true if on login page
     */
    public boolean isOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.equals(BASE_URL) || currentUrl.equals(BASE_URL + "/");
    }
}
