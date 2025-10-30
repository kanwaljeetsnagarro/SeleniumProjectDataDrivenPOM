# Selenium Data-Driven Page Object Model (POM) Framework

## Project Architecture

```
SeleniumProjectDataDrivenPOM/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── selenium/
│   │               └── test/
│   │                   ├── pages/
│   │                   │   ├── BasePage.java
│   │                   │   ├── LoginPage.java
│   │                   │   └── ... (other page objects)
│   │                   └── utils/
│   │                       ├── ExcelUtils.java
│   │                       └── ... (other utilities)
│   └── test/
│       └── java/
│           └── com/
│               └── selenium/
│                   └── test/
│                       └── tests/
│                           ├── BaseTest.java
│                           ├── LoginTest.java
│                           └── ... (other test classes)
│
├── testng.xml
├── pom.xml
├── conversation_backup.txt
└── README.md
```

---

## Framework Components

### 1. **BaseTest**

- Handles browser setup and teardown for each test.
- Initializes the WebDriver instance.
- Ensures each test runs in a fresh browser session.

### 2. **BasePage**

- Parent class for all page objects.
- Stores the WebDriver instance.
- Provides reusable methods for actions like click, type, wait, getText, etc.
- All page classes extend BasePage to inherit these utilities.

### 3. **Page Classes (e.g., LoginPage)**

- Represent individual web pages.
- Contain web element locators and page-specific actions.
- Extend BasePage to use the shared driver and utilities.

### 4. **Test Classes (e.g., LoginTest)**

- Contain actual test cases.
- Extend BaseTest to get browser management.
- Use page objects to perform actions and assertions.

### 5. **Utils**

- Utility classes for data-driven testing (e.g., reading Excel files).
- Helpers for screenshots, waits, and other cross-cutting concerns.

### 6. **Configuration**

- `testng.xml`: Test suite configuration.
- `pom.xml`: Maven dependencies and build configuration.

---

## Data Flow & Driver Flow

1. **Test starts**:`BaseTest` creates a WebDriver instance.
2. **Test class** (e.g., `LoginTest`) uses this driver to create a page object (e.g., `LoginPage`).
3. **Page object** passes the driver to `BasePage` via its constructor.
4. **BasePage** stores the driver and provides utility methods for all page objects.
5. **Test ends**:
   `BaseTest` closes the browser.

---

## Why This Architecture?

- **Separation of Concerns**:

  - Test setup/teardown is separate from page actions.
  - Page objects focus only on page-specific logic.
- **Reusability**:

  - Common actions are written once in `BasePage` and reused everywhere.
- **Maintainability**:

  - Changes to browser setup or common actions are made in one place.
- **Scalability**:

  - Easy to add new pages and tests without duplicating code.

---

## How to Extend

- **Add a new page**:Create a new class in `pages/`, extend `BasePage`, add locators and actions.
- **Add a new test**:Create a new class in `tests/`, extend `BaseTest`, use page objects for actions.
- **Add data-driven tests**:
  Use utility classes to read data from Excel or other sources.

---

## Example Test Flow

```java
public class LoginTest extends BaseTest {
    @Test
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("user", "pass");
        Assert.assertTrue(loginPage.isLoggedIn());
    }
}
```

---

## Summary

This framework follows the **Base Page Object Pattern** within the Page Object Model (POM) design.
It is designed for maintainability, reusability, and scalability, making it suitable for both small and large automation projects.

For more details, see `conversation_backup.txt` for a full Q&A on the framework's design and best practices.
