# Generate Java Page Classes — Selenium POM

Purpose
- Concise, repo-agnostic rules to create consistent, maintainable Java Page Object classes for a Selenium-based POM framework.

Quick scan (read first)
- Build file: pom.xml or build.gradle — confirm Java version, TestNG/JUnit, Selenium, Lombok.
- Look for existing test infrastructure: BaseTest, BasePage, DriverFactory/WebDriverManager, Waits/ExpectedConditions helpers.
- Locate existing pages/components under: src/main/java/**/pages or **/ui/**/components and tests under src/test/java.

File & class placement
- Package path must match directory. Place pages under src/main/java/<root>/pages/<area>/.
- Class name: <Feature>NamePage (e.g., LoginPage). Reusable parts -> <Name>Component (e.g., HeaderComponent).
- Keep page classes small and focused (one page per class).

Constructor & WebDriver handling
- Prefer constructor injection: public LoginPage(WebDriver driver) { this.driver = driver; ... }
- If project uses a ThreadLocal driver wrapper or DriverFactory, accept/obtain that wrapper consistent with existing pages (do not introduce new static drivers).
- If PageFactory is used in the project, call PageFactory.initElements(driver, this) in the constructor.

Locator strategy (detect and follow repo style)
- Two common patterns — match existing pages:
  1. PageFactory pattern: use private @FindBy(...) WebElement fields.
  2. By-locator pattern: use private final By locators + driver.findElement/ wait helpers.
- Do not mix patterns within a single page. Prefer the pattern used by other pages in the same package.

Fields & visibility
- Keep WebElements and locators private.
- Expose behavior via methods, not raw elements or locators.
- If constants (timeouts/xpaths) are used elsewhere, reference the shared constant classes.

Actions & method signatures
- Action methods should express intent: enterUsername(String user), clickLogin(), selectCountry(String).
- Navigation methods return Page objects when navigation occurs (e.g., LoginPage.login() -> HomePage). For fluent API on same page, return this.
- Query methods: isXxxVisible(), getXxxText(), hasErrorMessage().
- Keep methods single-responsibility and short.

Synchronization & waits
- Always wait for element to be ready before interacting.
- Prefer existing wait utilities (Waits.untilVisible(locator) or a shared WebDriverWait instance). Use ExpectedConditions when helper not present.
- Do not introduce Thread.sleep; follow project-wide timeout constants.

Error handling, diagnostics & screenshots
- Follow repository convention on failures: capture screenshots via existing utility, log an informative message, and throw a descriptive runtime exception.
- If there is a centralized screenshot or Allure attachment helper, call it from catch blocks or test hooks, not inside every method.

Logging & annotations
- Use the same logger style as the project (e.g., @Slf4j or LoggerFactory). Mirror existing classes.
- If Allure or similar reporting is used, annotate key public actions with @Step per existing style.

Components & reuse
- Extract repeatable regions (headers, footers, widgets) into Component classes and reuse them rather than duplicating locators.
- Component classes follow the same rules as pages but remain focused, and are composed into Page classes.

Lombok, records & Java idioms
- If Lombok is present in the project, use it for simple DTOs/POJOs used by pages (not for WebElements).
- Use records only if the project already uses records for immutable DTOs and Java version supports them.

Tests & examples
- Create a minimal test stub using the repo's test base class (e.g., extends BaseTest) to demonstrate page usage.
- Example patterns:
```java
// If PageFactory is used:
public class LoginPage {
  private final WebDriver driver;
  @FindBy(id = "username") private WebElement username;
  @FindBy(id = "password") private WebElement password;
  public LoginPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
    // optionally: Waits.untilVisible(username);
  }
  public HomePage login(String u, String p) {
    username.sendKeys(u);
    password.sendKeys(p);
    // click and return new HomePage(driver);
  }
}
```

Do's and don'ts (short)
- Do follow existing pages' locator pattern and logging style.
- Do return Page objects for navigation and this for fluent actions.
- Do reuse helper classes (Waits, DriverFactory, ScreenshotUtil).
- Don't introduce static WebDriver fields.
- Don't expose raw WebElements or locators publicly.
- Don't add new dependencies or frameworks without matching project usage.

Merge & update rules
- If an existing BasePage or page examples exist, model your new page after them. Keep naming, constructor style, and wait behavior consistent.
- Add a short one-line Javadoc describing responsibility at the top of each page class.
