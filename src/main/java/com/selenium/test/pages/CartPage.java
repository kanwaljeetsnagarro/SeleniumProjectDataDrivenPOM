package com.selenium.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object Model for Cart/Checkout page
 * Handles all interactions related to shopping cart and checkout functionality
 */
public class CartPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);

    // Locators for Cart Page
    @FindBy(id = "checkout-btn")
    private WebElement checkoutButton;

    @FindBy(id = "cart-items")
    private WebElement cartItemsContainer;

    @FindBy(css = ".cart-item")
    private java.util.List<WebElement> cartItems;

    @FindBy(id = "cart-total")
    private WebElement cartTotal;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(id = "login-redirect-message")
    private WebElement loginRedirectMessage;

    @FindBy(xpath = "//button[contains(text(), 'Continue Shopping')]")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//button[contains(text(), 'Remove')]")
    private java.util.List<WebElement> removeButtons;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Attempt to proceed to checkout
     * @return true if checkout button is clickable, false otherwise
     */
    public boolean proceedToCheckout() {
        try {
            logger.info("Attempting to proceed to checkout");
            waitForElementToBeClickable(checkoutButton, 10);
            checkoutButton.click();
            logger.info("Checkout button clicked successfully");
            return true;
        } catch (Exception e) {
            logger.error("Failed to proceed to checkout: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get error message displayed on the page
     * @return error message text
     */
    public String getErrorMessage() {
        try {
            waitForElementToBeVisible(errorMessage, 10);
            String message = errorMessage.getText();
            logger.info("Error message found: " + message);
            return message;
        } catch (Exception e) {
            logger.warn("No error message found: " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if login redirect message is displayed
     * @return true if login redirect message is visible
     */
    public boolean isLoginRedirectMessageDisplayed() {
        try {
            waitForElementToBeVisible(loginRedirectMessage, 10);
            logger.info("Login redirect message is displayed");
            return true;
        } catch (Exception e) {
            logger.warn("Login redirect message not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the login redirect message text
     * @return login redirect message
     */
    public String getLoginRedirectMessage() {
        try {
            if (isLoginRedirectMessageDisplayed()) {
                return loginRedirectMessage.getText();
            }
        } catch (Exception e) {
            logger.error("Failed to get login redirect message: " + e.getMessage());
        }
        return "";
    }

    /**
     * Get the number of items in the cart
     * @return number of cart items
     */
    public int getCartItemCount() {
        try {
            int count = cartItems.size();
            logger.info("Cart contains " + count + " items");
            return count;
        } catch (Exception e) {
            logger.error("Failed to get cart item count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Verify that cart contains items (not empty)
     * @return true if cart has items
     */
    public boolean isCartNotEmpty() {
        return getCartItemCount() > 0;
    }

    /**
     * Get cart total amount
     * @return cart total as string
     */
    public String getCartTotal() {
        try {
            waitForElementToBeVisible(cartTotal, 10);
            String total = cartTotal.getText();
            logger.info("Cart total: " + total);
            return total;
        } catch (Exception e) {
            logger.error("Failed to get cart total: " + e.getMessage());
            return "";
        }
    }

    /**
     * Click continue shopping button
     */
    public void clickContinueShopping() {
        try {
            logger.info("Clicking continue shopping button");
            waitForElementToBeClickable(continueShoppingButton, 10);
            continueShoppingButton.click();
            logger.info("Continue shopping button clicked");
        } catch (Exception e) {
            logger.error("Failed to click continue shopping: " + e.getMessage());
        }
    }

    /**
     * Remove an item from the cart by index
     * @param index index of the item to remove
     */
    public void removeItemFromCart(int index) {
        try {
            if (index >= 0 && index < removeButtons.size()) {
                logger.info("Removing item at index: " + index);
                waitForElementToBeClickable(removeButtons.get(index), 10);
                removeButtons.get(index).click();
                logger.info("Item removed from cart");
            }
        } catch (Exception e) {
            logger.error("Failed to remove item from cart: " + e.getMessage());
        }
    }

    /**
     * Verify if checkout button is accessible
     * @return true if checkout button is present and enabled
     */
    public boolean isCheckoutButtonAccessible() {
        try {
            return checkoutButton.isDisplayed() && checkoutButton.isEnabled();
        } catch (Exception e) {
            logger.warn("Checkout button not accessible: " + e.getMessage());
            return false;
        }
    }

    /**
     * Wait for page to load and verify cart elements are present
     * @return true if cart page loaded successfully
     */
    public boolean isCartPageLoaded() {
        try {
            waitForElementToBeVisible(cartItemsContainer, 10);
            logger.info("Cart page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Cart page failed to load: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the first cart item name
     * @return name of the first item in cart
     */
    public String getFirstCartItemName() {
        try {
            if (cartItems.size() > 0) {
                String itemName = cartItems.get(0).getText();
                logger.info("First cart item name: " + itemName);
                return itemName;
            }
        } catch (Exception e) {
            logger.error("Failed to get first cart item name: " + e.getMessage());
        }
        return "";
    }
}
