package com.selenium.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Page Object Model for Inventory page
 * Handles all interactions related to product browsing and cart management
 */
public class InventoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(InventoryPage.class);

    // Locators for Inventory Page
    @FindBy(id = "inventory-container")
    private WebElement inventoryContainer;

    @FindBy(css = ".inventory-item")
    private List<WebElement> inventoryItems;

    @FindBy(xpath = "//button[contains(text(), 'Add to Cart')]")
    private List<WebElement> addToCartButtons;

    @FindBy(id = "cart-link")
    private WebElement cartLink;

    @FindBy(css = ".cart-badge")
    private WebElement cartBadge;

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "user-greeting")
    private WebElement userGreeting;

    @FindBy(xpath = "//h1[contains(text(), 'Products')]")
    private WebElement productsHeading;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if inventory page is loaded
     * @return true if inventory page is displayed
     */
    public boolean isInventoryPageLoaded() {
        try {
            waitForElementToBeVisible(inventoryContainer, 10);
            logger.info("Inventory page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Inventory page failed to load: " + e.getMessage());
            return false;
        }
    }

    /**
     * Add product to cart by index
     * @param index index of the product to add
     */
    public void addProductToCart(int index) {
        try {
            if (index >= 0 && index < addToCartButtons.size()) {
                logger.info("Adding product at index " + index + " to cart");
                waitForElementToBeClickable(addToCartButtons.get(index), 10);
                addToCartButtons.get(index).click();
                logger.info("Product added to cart");
            }
        } catch (Exception e) {
            logger.error("Failed to add product to cart: " + e.getMessage());
        }
    }

    /**
     * Navigate to cart
     */
    public void goToCart() {
        try {
            logger.info("Navigating to cart");
            waitForElementToBeClickable(cartLink, 10);
            cartLink.click();
            logger.info("Cart page accessed");
        } catch (Exception e) {
            logger.error("Failed to navigate to cart: " + e.getMessage());
        }
    }

    /**
     * Try to access checkout directly without logging in
     * Navigate to checkout URL directly
     */
    public void accessCheckoutDirectly() {
        try {
            logger.info("Attempting to access checkout directly");
            String baseUrl = driver.getCurrentUrl();
            String checkoutUrl = baseUrl.replaceAll("(inventory|products|home).*", "checkout");
            driver.navigate().to(checkoutUrl);
            logger.info("Attempted to access checkout URL directly: " + checkoutUrl);
        } catch (Exception e) {
            logger.error("Failed to access checkout directly: " + e.getMessage());
        }
    }

    /**
     * Get cart item count
     * @return number of items in cart
     */
    public int getCartItemCount() {
        try {
            String cartCount = cartBadge.getText();
            int count = Integer.parseInt(cartCount);
            logger.info("Cart contains " + count + " items");
            return count;
        } catch (Exception e) {
            logger.info("Cart is empty or badge not visible: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get number of available inventory items
     * @return count of inventory items
     */
    public int getInventoryItemCount() {
        try {
            int count = inventoryItems.size();
            logger.info("Inventory has " + count + " items");
            return count;
        } catch (Exception e) {
            logger.error("Failed to get inventory item count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get user greeting message
     * @return user greeting text
     */
    public String getUserGreeting() {
        try {
            waitForElementToBeVisible(userGreeting, 10);
            String greeting = userGreeting.getText();
            logger.info("User greeting: " + greeting);
            return greeting;
        } catch (Exception e) {
            logger.info("User greeting not found: " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if user is logged in (user greeting visible)
     * @return true if user greeting is displayed
     */
    public boolean isUserLoggedIn() {
        try {
            return userGreeting.isDisplayed();
        } catch (Exception e) {
            logger.info("User not logged in or greeting not visible: " + e.getMessage());
            return false;
        }
    }

    /**
     * Logout from the application
     */
    public void logout() {
        try {
            logger.info("Logging out from application");
            waitForElementToBeClickable(logoutButton, 10);
            logoutButton.click();
            logger.info("Logged out successfully");
        } catch (Exception e) {
            logger.error("Failed to logout: " + e.getMessage());
        }
    }

    /**
     * Get product name by index
     * @param index index of the product
     * @return product name
     */
    public String getProductName(int index) {
        try {
            if (index >= 0 && index < inventoryItems.size()) {
                String name = inventoryItems.get(index).getText();
                logger.info("Product name at index " + index + ": " + name);
                return name;
            }
        } catch (Exception e) {
            logger.error("Failed to get product name: " + e.getMessage());
        }
        return "";
    }
}
