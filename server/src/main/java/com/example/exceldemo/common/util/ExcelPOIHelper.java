package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.ExcelException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.exception.InvalidRowSizeException;
import com.example.exceldemo.common.model.ExcelCell;
import com.example.exceldemo.common.model.ExcelHeader;
import com.example.exceldemo.common.model.ExcelRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public final class ExcelPOIHelper {

    /**
     * Read Excel file and extract data
     *
     * @param excelFile      file to read
     * @param maxRowsAllowed max rows to extract
     * @return list of ExcelRow
     * @throws ExcelException          if an error occurred while reading file
     * @throws InvalidRowSizeException if Excel sheet contains more rows than the given max
     * @throws InvalidHeaderException  if file doesn't have required header
     */
    public List<ExcelRow> readExcel(InputStream excelFile, int maxRowsAllowed)
            throws ExcelException, InvalidRowSizeException, InvalidHeaderException {
        List<ExcelRow> excelRowList = new ArrayList<>();

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        try (Workbook workbook = WorkbookFactory.create(excelFile)) { // will automatically close workbook
            // Getting the Sheet at index zero
            final Sheet firstSheet = workbook.getSheetAt(0);

            // Creating a DataFormatter to format and get each cell's value as String
            final DataFormatter dataFormatter = new DataFormatter();

            // Validating max rows
            validateMaxRowsAllowed(maxRowsAllowed, firstSheet);

            // Iterating over all the rows in the sheet and fill list
            final Iterator<Row> rowIterator = firstSheet.rowIterator();
            while (rowIterator.hasNext()) {
                final Row currentRow = rowIterator.next();
                // Validating the header row
                if (currentRow.getRowNum() == 0) {
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
     * Validate max rows allowed in the Excel sheet
     *
     * @param maxRowsAllowed max rows to extract
     * @param firstSheet     Excel sheet
     * @throws InvalidRowSizeException if Excel sheet contains more rows than the given max
     */
    private static void validateMaxRowsAllowed(int maxRowsAllowed, Sheet firstSheet) throws InvalidRowSizeException {
        final int numberOfRows = firstSheet.getPhysicalNumberOfRows();
        if (numberOfRows > maxRowsAllowed) {
            throw new InvalidRowSizeException("[ExcelPOIHelper][validateMaxRowsAllowed] Current Excel sheet contains more rows than allowed for"
                    + " numberOfRows=" + numberOfRows
                    + " maxRowsAllowed=" + maxRowsAllowed);
        }
    }

    /**
     * Validate expected headers in the given row
     *
     * @param headerRow     header row
     * @param dataFormatter data formatter
     * @throws InvalidHeaderException if current cell value is not valid
     */
    private void validateHeader(Row headerRow, DataFormatter dataFormatter) throws InvalidHeaderException {
        final Iterator<Cell> cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            final Cell currentCell = cellIterator.next();
            final String currentCellValue = dataFormatter.formatCellValue(currentCell);
            if (!ExcelHeader.isValid(currentCellValue)) {
                throw new InvalidHeaderException("[ExcelPOIHelper][validateHeader] Current cell value doesn't match any of the expected headers: " + currentCellValue);
            }
        }
    }

    /**
     * Fill ExcelRow list object with extracted data
     *
     * @param currentRow    current row
     * @param dataFormatter data formatter
     * @param excelRowList  list of Excel rows
     */
    private void fillExcelRowList(Row currentRow, DataFormatter dataFormatter, List<ExcelRow> excelRowList) {
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(new ExcelRow());
        beanWrapper.setPropertyValue("rowIndex", currentRow.getRowNum());

        // Iterating over all cells of currentRow
        // and dynamically building an ExcelRow
        final Iterator<Cell> cellIterator = currentRow.cellIterator();
        final Field[] fields = beanWrapper.getWrappedClass().getDeclaredFields();
        final List<String> columnNameList = Arrays.stream(fields).map(Field::getName).toList();

        while (cellIterator.hasNext()) {
            final Cell currentCell = cellIterator.next();
            final int columnIndex = currentCell.getColumnIndex();
            final String columnName = columnNameList.get(columnIndex);
            final String currentCellValue = dataFormatter.formatCellValue(currentCell);
            final String columnLetter = CellReference.convertNumToColString(columnIndex);
            final ExcelCell excelCell = new ExcelCell(currentCellValue, columnLetter, columnIndex);
            beanWrapper.setPropertyValue(columnName, excelCell);
        }

        excelRowList.add((ExcelRow) beanWrapper.getWrappedInstance());
    }

}
