package com.selenium.test.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * BaseTest provides common test functionality:
 * 1. WebDriver setup and cleanup
 * 2. Browser configuration
 * 3. Common test utilities
 */
public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "https://www.saucedemo.com";

    /**
     * Sets up WebDriver before each test method.
     * Supports multiple browsers through TestNG parameters.
     * 
     * @param browser Browser to run tests on (chrome/firefox)
     */
    @BeforeMethod
    @Parameters({ "browser" })
    public void setUp(@Optional("chrome") String browser) {
        // Setup WebDriver based on browser parameter
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
        }

        // Maximize window and set implicit wait
        driver.manage().window().maximize();
    }

    /**
     * Cleans up WebDriver after each test method
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Gets current WebDriver instance
     * 
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }
}