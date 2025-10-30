---
applyTo: "**/*.java"
description: Guidelines for Selenium automation using Java
---

# Selenium + Java Project Guidelines

## 🔹 Project Structure
- Follow the **Page Object Model (POM)** design pattern.
- Each page class should only contain locators and methods specific to that page.
- Keep test classes clean — only contain test logic, not element locators.

## 🔹 Naming Conventions
- Use **camelCase** for variables and methods (e.g., `clickLoginButton()`).
- Use **PascalCase** for class names (e.g., `LoginPage`, `BaseTest`).
- Prefix test methods with `test` (e.g., `testUserLoginSuccess()`).

## 🔹 Locator Strategy
- Prefer using `By.id` or `By.name` where possible (they are fastest).
- Avoid using `Thread.sleep()`; use explicit waits (`WebDriverWait`) instead.
- Store locators as private variables in page classes.

## 🔹 Synchronization & Waits
- Always use **explicit waits** (`ExpectedConditions`) for element actions.
- Use utility methods to wrap wait logic (e.g., `waitForElementToBeVisible()`).

## 🔹 Error Handling & Logging
- Log actions using a logger (e.g., `log.info("Clicking on login button");`).
- Use try/catch only when necessary; allow test frameworks to handle assertion failures.

## 🔹 Test Best Practices
- Use **@BeforeMethod** and **@AfterMethod** for setup/cleanup in test classes.
- Use **data providers** for data-driven testing.
- Keep tests **independent** and **idempotent** (can be re-run without state issues).

## 🔹 Assertion Guidelines
- Use soft assertions only when multiple checks are needed in a single test.
- Prefer meaningful assertion messages (`Assert.assertEquals(actual, expected, "Username mismatch");`).

## 🔹 Reporting
- Integrate a test reporting tool (e.g., **ExtentReports**, **Allure**).
- Take screenshots on failure and attach to reports.

## 🔹 Other Guidelines
- Avoid hard-coded test data — use external data files (Excel, JSON, etc.).
- Add JavaDocs for utility and base and test classes.
- Annotate test methods with priority and description if needed.

