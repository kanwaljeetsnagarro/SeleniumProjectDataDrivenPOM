package com.selenium.test.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelDataProvider {
    private static final String TEST_DATA_FILENAME = "TestData.xlsx";
    private static volatile Workbook workbook;

    /**
     * Gets test data from specified sheet
     * 
     * @param sheetName Name of sheet to read
     * @return Object[][] containing test data as Maps
     */
    public static Object[][] getTestData(String sheetName) {
        String testDataPath = System.getProperty("user.dir") + "/src/test/resources/testdata/" + TEST_DATA_FILENAME;
        
        try (FileInputStream fis = new FileInputStream(testDataPath)) {
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in test data file");
            }
            return getSheetData(sheet);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data: " + e.getMessage(), e);
        }
    }

    /**
     * Converts sheet data to Object array of Maps
     * 
     * @param sheet Sheet to read
     * @return Object[][] containing row data as Maps
     */
    private static Object[][] getSheetData(Sheet sheet) {
        List<Map<String, String>> data = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();

        // Get header row for column names
        Row headerRow = rowIterator.next();
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue());
        }

        // Read data rows
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Map<String, String> rowData = new HashMap<>();

            // Read each cell in row
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                rowData.put(headers.get(i), getCellValueAsString(cell));
            }
            data.add(rowData);
        }

        // Convert to Object[][]
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }

    /**
     * Converts cell value to string regardless of cell type
     * 
     * @param cell Cell to read
     * @return String value of cell
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    }
                    // Use DataFormatter to get string value without scientific notation
                    DataFormatter formatter = new DataFormatter();
                    return formatter.formatCellValue(cell).trim();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        return cell.getStringCellValue();
                    }
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}