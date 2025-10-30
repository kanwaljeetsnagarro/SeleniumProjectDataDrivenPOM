package com.selenium.test.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.FileOutputStream;
import java.io.File;
import java.util.*;

/**
 * TestDataGenerator creates the Excel file containing test data.
 * This class demonstrates:
 * 1. Test data management best practices
 * 2. Apache POI for Excel file creation
 * 3. Separation of test data from test code
 * 4. Maintainable test data structure
 */
public class TestDataGenerator {
    // Constants for file path and Excel structure
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/TestData.xlsx";
    private static final String[] HEADERS = { "testCase", "username", "password", "expectedResult" };

    private static final Random random = new Random();

    /**
     * Test data for login scenarios
     * Each array element represents a test case with:
     * 1. Test case description
     * 2. Username to test
     * 3. Password to test
     * 4. Expected result
     * 
     * This approach allows:
     * - Easy addition of new test cases
     * - Clear documentation of test scenarios
     * - Maintainable test data
     */
    private static final Object[][] LOGIN_DATA = {
            // Standard user login - happy path
            { "Standard User Login", "standard_user", "secret_sauce", "success" },

            // Locked out user - expected failure
            { "Locked Out User", "locked_out_user", "secret_sauce",
                    "Epic sadface: Sorry, this user has been locked out." },

            // Problem user - tests application's handling of problematic users
            { "Problem User", "problem_user", "secret_sauce", "success" },

            // Performance glitch user - tests application's performance handling
            { "Performance Glitch User", "performance_glitch_user", "secret_sauce", "success" },

            // Empty credentials - validation testing
            { "Empty Credentials", "", "", "Epic sadface: Username is required" },

            // Invalid credentials - security testing
            { "Invalid Password", "standard_user", "wrongpass",
                    "Epic sadface: Username and password do not match any user in this service" },

            // Non-existent user - security testing
            { "Invalid Username", "invalid_user", "secret_sauce",
                    "Epic sadface: Username and password do not match any user in this service" }
    };

    /**
     * Generates the Excel test data file
     * Process:
     * 1. Creates directory structure if needed
     * 2. Creates new Excel workbook
     * 3. Populates data sheets
     * 4. Saves to file system
     */
    public static void generateTestData() {
        try {
            // Create directory structure if it doesn't exist
            File dir = new File("src/test/resources/testdata");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Create and populate Excel workbook
            try (Workbook workbook = new XSSFWorkbook()) {
                // Create sheet with login test data
                createSheet(workbook, "Login", LOGIN_DATA);

                // Save workbook to file using try-with-resources
                try (FileOutputStream fos = new FileOutputStream(TEST_DATA_PATH)) {
                    workbook.write(fos);
                }
                System.out.println("Test data file generated successfully at: " + TEST_DATA_PATH);
            }
        } catch (Exception e) {
            System.err.println("Failed to generate test data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates and populates an Excel sheet
     * Features:
     * 1. Header row creation
     * 2. Data population
     * 3. Column auto-sizing for readability
     * 
     * @param workbook  Excel workbook to add sheet to
     * @param sheetName Name of sheet to create
     * @param data      Data to populate in sheet
     */
    private static void createSheet(Workbook workbook, String sheetName, Object[][] data) {
        Sheet sheet = workbook.createSheet(sheetName);

        // Create header row with column names
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
        }

        // Populate data rows
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data[i][j].toString());
            }
        }

        // Auto-size columns for better readability
        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Generates a random username.
     * Format: "testuser_" followed by random number
     * 
     * @return Random username string
     */
    public static String generateUsername() {
        return "testuser_" + random.nextInt(10000);
    }

    /**
     * Generates a random password.
     * Rules:
     * - At least 8 characters
     * - Contains letters and numbers
     * - Contains at least one special character
     * 
     * @return Random password string
     */
    public static String generatePassword() {
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String special = "!@#$%^&*";

        StringBuilder password = new StringBuilder();

        // Add at least one character from each required type
        password.append(letters.charAt(random.nextInt(letters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        // Add 5 more random characters
        String allChars = letters + numbers + special;
        for (int i = 0; i < 5; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }

    /**
     * Generates a random email address.
     * Format: username@testdomain.com
     * 
     * @return Random email string
     */
    public static String generateEmail() {
        return generateUsername() + "@testdomain.com";
    }

    /**
     * Main method to execute data generation
     * Can be run independently to regenerate test data
     * Usage:
     * 1. Run directly from IDE
     * 2. Run via Maven: mvn exec:java
     * 3. Run via Java command line
     */
    public static void main(String[] args) {
        generateTestData();
    }
}