package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.TechnicalException;
import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.ExcelErrorType;
import com.example.exceldemo.common.model.Person;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.exceldemo.common.util.DateUtil.formatWebDateStringAsLocalDate;
import static com.example.exceldemo.common.util.ExcelCellValidator.isEmailAddressValid;
import static com.example.exceldemo.common.util.ExcelCellValidator.isPostCodeValid;

@Component
public class ExcelPOIHelper {

    public Map<Person, List<ExcelError>> readExcel(InputStream excelFile) throws IOException {
        Map<Person, List<ExcelError>> personListMap = new HashMap<>();

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(excelFile);

        // Getting the Sheet at index zero
        Sheet firstSheet = workbook.getSheetAt(0);

        // Creating a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // Iterating over all the rows in the sheet and fill map
        Iterator<Row> rowIterator = firstSheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (currentRow.getRowNum() == 1) {
                continue; // skip the first row
            }
            this.fillPersonListMap(currentRow, dataFormatter, personListMap);
        }

        // Closing the workbook
        workbook.close();

        return personListMap;
    }

    private void fillPersonListMap(Row currentRow, DataFormatter dataFormatter, Map<Person, List<ExcelError>> personListMap) {
        Person person = new Person();
        List<ExcelError> excelErrorList = new ArrayList<>();

        // Iterating over all cells of currentRow
        Iterator<Cell> cellIterator = currentRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            String currentCellValue = dataFormatter.formatCellValue(currentCell);
            String columnLetter = CellReference.convertNumToColString(currentCell.getColumnIndex());
            int columnIndex = currentCell.getColumnIndex();
            switch (columnIndex) {
                case 1 -> person.setCivility(currentCellValue);
                case 2 -> person.setName(currentCellValue);
                case 3 -> person.setFirstName(currentCellValue);
                case 4 -> {
                    LocalDate birthDate = null;
                    try {
                        birthDate = formatWebDateStringAsLocalDate(currentCellValue);
                    } catch (TechnicalException e) {
                        addExcelErrorToList(excelErrorList, currentRow, currentCellValue, columnLetter, ExcelErrorType.WRONG_BIRTHDATE_FORMAT);
                    }
                    person.setBirthDate(birthDate);
                }
                case 5 -> person.setBirthCity(currentCellValue);
                case 6 -> person.setBirthDepartment(currentCellValue);
                case 7 -> person.setBirthCountry(currentCellValue);
                case 8 -> person.setAddress(currentCellValue);
                case 9 -> {
                    boolean isPostCodeValid = isPostCodeValid(currentCellValue);
                    if (!isPostCodeValid) {
                        this.addExcelErrorToList(excelErrorList, currentRow, currentCellValue, columnLetter, ExcelErrorType.WRONG_POSTCODE_FORMAT);
                    }
                    person.setPostCode(currentCellValue);
                }
                case 10 -> person.setCity(currentCellValue);
                case 11 -> person.setCountry(currentCellValue);
                case 12 -> person.setPhoneCode(currentCellValue);
                case 13 -> person.setPhoneNumber(currentCellValue);
                case 14 -> {
                    boolean isEmailAddressValid = isEmailAddressValid(currentCellValue);
                    if (!isEmailAddressValid) {
                        this.addExcelErrorToList(excelErrorList, currentRow, currentCellValue, columnLetter, ExcelErrorType.WRONG_EMAIL_FORMAT);
                    }
                    person.setEmailAddress(currentCellValue);
                }
            }
        }

        personListMap.put(person, excelErrorList);
    }

    private void addExcelErrorToList(List<ExcelError> excelErrorList, Row currentRow, String currentCellValue, String columnLetter, ExcelErrorType excelErrorType) {
        ExcelError excelError = ExcelError.builder()
                .rowIndex(currentRow.getRowNum())
                .columnLetter(columnLetter)
                .originalValue(currentCellValue)
                .excelErrorType(excelErrorType)
                .build();
        excelErrorList.add(excelError);
    }

}
