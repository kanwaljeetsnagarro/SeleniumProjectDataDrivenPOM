# Checkout Test - Sample Excel Data Format

## Instructions

Add the following data to your `TestData.xlsx` file in a new sheet named `CheckoutTest`.

## Column Headers

Create these columns in the Excel sheet:

| Column A | Column B | Column C |
|----------|----------|----------|
| TestCaseID | ExpectedError | ExpectedUrl |

## Sample Test Data

Add the following rows to your sheet:

### Row 1 (Headers)
```
TestCaseID          | ExpectedError                              | ExpectedUrl
```

### Row 2 - Direct Checkout Access Without Session
```
CHK_001             | Please login to continue                   | /login
```

### Row 3 - Expired Session
```
CHK_002             | Session expired. Please login again        | /login
```

### Row 4 - Unauthorized Access
```
CHK_003             | Unauthorized access to checkout            | /login
```

### Row 5 - Missing Authentication Token
```
CHK_004             | Authentication required                    | /login
```

## Expected Behavior

When running `testCheckoutWithoutUsername()` with this data:

1. **CHK_001**: 
   - System shows "Please login to continue"
   - User is redirected to /login
   
2. **CHK_002**:
   - System shows "Session expired. Please login again"
   - User is redirected to /login
   
3. **CHK_003**:
   - System shows "Unauthorized access to checkout"
   - User is redirected to /login
   
4. **CHK_004**:
   - System shows "Authentication required"
   - User is redirected to /login

## Excel File Structure

```
TestData.xlsx
└── CheckoutTest (sheet)
    ├── Column A: TestCaseID (CHK_001, CHK_002, etc.)
    ├── Column B: ExpectedError (error message text)
    └── Column C: ExpectedUrl (expected redirect URL)
```

## Notes

- **TestCaseID**: Unique identifier for each test case (e.g., CHK_001, CHK_002)
- **ExpectedError**: The error message that should be displayed. Test will verify this message contains login/session/authorization keywords
- **ExpectedUrl**: The URL the user should be redirected to (typically /login)

## Customization

Modify the test data based on your actual application:

- Update error messages to match your application's actual error text
- Update URL paths to match your application's actual routes
- Add more test cases as needed following the same pattern

## How Test Data is Used

In `CheckoutTest.java`, the `@DataProvider` method reads this Excel data:

```java
@DataProvider(name = "checkoutTestData")
public Object[][] getCheckoutTestData() {
    return ExcelDataProvider.getTestData("CheckoutTest");
}
```

Each row of data is passed to the test method:

```java
@Test(dataProvider = "checkoutTestData")
public void testCheckoutWithoutUsername(String testCaseId, String expectedError, String expectedUrl)
```

## Running Tests with Specific Data

You can run tests and they will automatically use all rows from the `CheckoutTest` sheet:

```bash
# Run all checkout data-driven tests
mvn test -Dtest=CheckoutTest#testCheckoutWithoutUsername
```

Each row in the Excel sheet will trigger a separate test execution with different data.

## Verification Checklist

- [ ] Excel file path: `src/test/resources/testdata/TestData.xlsx`
- [ ] Sheet name: `CheckoutTest`
- [ ] Headers match expected columns
- [ ] At least one test data row exists
- [ ] TestCaseID values are unique
- [ ] ExpectedError values are not empty
- [ ] ExpectedUrl values are not empty
- [ ] File is saved and closed before running tests

## Troubleshooting

**Tests not picking up data?**
- Verify sheet name is exactly "CheckoutTest" (case-sensitive)
- Ensure Excel file is in correct location: `src/test/resources/testdata/`
- Check that data starts from row 2 (row 1 is headers)

**Tests fail with "No data found"?**
- Verify ExcelDataProvider is correctly configured
- Check that ExcelDataProvider can find and read the file
- Ensure Excel file is not corrupted

**Wrong data being used?**
- Verify column order: TestCaseID, ExpectedError, ExpectedUrl
- Check that no extra columns are inserted before these columns
- Ensure no empty rows exist between data rows
