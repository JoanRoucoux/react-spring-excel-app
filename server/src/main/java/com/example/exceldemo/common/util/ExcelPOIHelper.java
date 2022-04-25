package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.ExcelException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.model.ExcelCell;
import com.example.exceldemo.common.model.ExcelHeader;
import com.example.exceldemo.common.model.ExcelRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelPOIHelper {

    /**
     * Read Excel file and extract data
     *
     * @param excelFile file to read
     * @return list of ExcelRow
     * @throws IOException if an error occurred while reading workbook
     * @throws ExcelException if an error occurred while reading file
     * @throws InvalidHeaderException if file doesn't have required header
     */
    public static List<ExcelRow> readExcel(InputStream excelFile) throws IOException, ExcelException, InvalidHeaderException {
        List<ExcelRow> excelRowList = new ArrayList<>();

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        try (Workbook workbook = WorkbookFactory.create(excelFile)) { // will automatically close workbook
            // Getting the Sheet at index zero
            Sheet firstSheet = workbook.getSheetAt(0);

            // Creating a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            // Iterating over all the rows in the sheet and fill list
            Iterator<Row> rowIterator = firstSheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                // Validating the header row
                if (currentRow.getRowNum() == 1) {
                    validateHeader(currentRow, dataFormatter);
                    continue;
                }
                fillExcelRowList(currentRow, dataFormatter, excelRowList);
            }

            return excelRowList;
        } catch (IOException e) {
            throw new ExcelException("[ExcelPOIHelper][readExcel] An error occurred while reading workbook from Excel file");
        }
    }

    /**
     * Validate expected headers in the given row
     *
     * @param headerRow     header row
     * @param dataFormatter data formatter
     * @throws InvalidHeaderException if current cell value is not valid
     */
    private static void validateHeader(Row headerRow, DataFormatter dataFormatter) throws InvalidHeaderException {
        Iterator<Cell> cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            String currentCellValue = dataFormatter.formatCellValue(currentCell);
            if (!ExcelHeader.isValid(currentCellValue)) {
                throw new InvalidHeaderException("[ExcelPOIHelper][validateHeader] Current cell value doesn't match any of the expected headers: " + currentCellValue);
            }
        }
    }

    /**
     * Fill ExcelRow list object with extracted data
     *
     * @param currentRow current row
     * @param dataFormatter data formatter
     * @param excelRowList list of Excel rows
     */
    private static void fillExcelRowList(Row currentRow, DataFormatter dataFormatter, List<ExcelRow> excelRowList) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(new ExcelRow());
        beanWrapper.setPropertyValue("rowIndex", currentRow.getRowNum());

        // Iterating over all cells of currentRow
        // and dynamically building an ExcelRow
        Iterator<Cell> cellIterator = currentRow.cellIterator();
        Field[] fields = beanWrapper.getWrappedClass().getDeclaredFields();
        List<String> columnNameList = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());

        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            int columnIndex = currentCell.getColumnIndex();
            String columnName = columnNameList.get(columnIndex);
            String currentCellValue = dataFormatter.formatCellValue(currentCell);
            String columnLetter = CellReference.convertNumToColString(columnIndex);
            ExcelCell excelCell = new ExcelCell(currentCellValue, columnLetter, columnIndex);
            beanWrapper.setPropertyValue(columnName, excelCell);
        }

        excelRowList.add((ExcelRow) beanWrapper.getWrappedInstance());
    }

}
