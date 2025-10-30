package com.selenium.test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * BasePage serves as a foundational class for all page objects in the Selenium framework.
 * It provides essential utilities for waiting on elements, interacting with web elements,
 * and retrieving page information, thereby promoting a streamlined approach to UI automation.
 */
public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final int TIMEOUT = 10;
    protected static final String BASE_URL = "https://www.saucedemo.com";

    /**
     * Constructor for BasePage
     * @param driver WebDriver instance to use
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for an element to be visible
     * @param element WebElement to wait for
     */
    protected void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for an element to be clickable
     * @param element WebElement to wait for
     */
    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Types text into an element after ensuring it's visible
     * @param element WebElement to type into
     * @param text Text to type
     */
    protected void type(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clicks an element after ensuring it's clickable
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        waitForElementClickable(element);
        element.click();
    }

    /**
     * Gets text from an element after ensuring it's visible
     * @param element WebElement to get text from
     * @return Text content of the element
     */
    protected String getText(WebElement element) {
        waitForElementVisible(element);
        return element.getText();
    }

    /**
     * Gets value from an input element
     * @param element WebElement to get value from
     * @return Value attribute of the element
     */
    protected String getValue(WebElement element) {
        waitForElementVisible(element);
        return element.getAttribute("value");
    }

    /**
     * Checks if an element is displayed
     * @param element WebElement to check
     * @return true if element is displayed, false otherwise
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Gets the current page URL
     * @return Current page URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Gets the page title
     * @return Page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
}