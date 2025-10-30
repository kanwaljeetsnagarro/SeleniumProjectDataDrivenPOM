---
applyTo: "**/*Test.java"
description: Coding standards and guidelines for writing tests using TestNG
---

# 🧪 TestNG Guidelines for Automation Projects

## 🔹 Annotation Usage
- Use `@BeforeMethod` and `@AfterMethod` for per-test setup and cleanup.
- Use `@BeforeClass` and `@AfterClass` for one-time setup/teardown logic.
- Avoid using `@BeforeSuite` or `@AfterSuite` unless suite-level config is required.
- Do not mix JUnit and TestNG annotations.

## 🔹 Test Method Conventions
- Prefix test methods with `test` and use descriptive names (e.g., `testUserLoginValidCredentials()`).
- Use `@Test(priority = n)` only when ordering is critical — strive for independent tests.
- Annotate test cases with meaningful `description` for reporting.

## 🔹 Assertions
- Use `Assert` class from `org.testng.Assert` for validations.
- Prefer hard assertions unless multiple verifications are required in one test.
- Add custom messages to assertions for easier debugging:
  ```java
  Assert.assertEquals(actual, expected, "Mismatch in user name");
