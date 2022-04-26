package com.example.exceldemo.common.util;

import com.example.exceldemo.common.model.ExcelCell;
import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.ExcelErrorType;
import com.example.exceldemo.common.model.ExcelRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_EMAIL_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_POSTCODE_FORMAT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PersonValidator {

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String POSTCODE_REGEX_PATTERN = "^(?:0[1-9]|[1-8]\\d|9[0-8])\\d{3}$";

    public static void validateExcelRow(ExcelRow excelRow, List<ExcelError> excelErrorList) {
        final int rowIndex = excelRow.getRowIndex();
        validateCell(excelRow.getEmailAddress(), EMAIL_REGEX_PATTERN, rowIndex, WRONG_EMAIL_FORMAT, excelErrorList);
        validateCell(excelRow.getZipcode(), POSTCODE_REGEX_PATTERN, rowIndex, WRONG_POSTCODE_FORMAT, excelErrorList);
    }

    private static void validateCell(ExcelCell excelCell, String pattern, int rowIndex, ExcelErrorType excelErrorType, List<ExcelError> excelErrorList) {
        final boolean isValid = patternMatches(excelCell.getValue(), pattern);
        if (!isValid) {
            ExcelError excelError = buildExcelError(excelCell, rowIndex, excelErrorType);
            excelErrorList.add(excelError);
        }
    }

    private static boolean patternMatches(String value, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }

    private static ExcelError buildExcelError(ExcelCell excelCell, int rowIndex, ExcelErrorType excelErrorType) {
        return ExcelError.builder()
                .rowIndex(rowIndex)
                .columnLetter(excelCell.getColumnLetter())
                .originalValue(excelCell.getValue())
                .excelErrorType(excelErrorType)
                .build();
    }

}
