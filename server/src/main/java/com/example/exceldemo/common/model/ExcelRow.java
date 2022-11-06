package com.example.exceldemo.common.model;

import com.example.exceldemo.common.constraint.ExcelConstraint;
import lombok.Data;

import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_BIRTHDATE_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_CITY_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_CIVILITY_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_EMAIL_ADDRESS_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_FIRSTNAME_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_LASTNAME_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_MOBILE_NUMBER_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_STATE_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_STREET_ADDRESS_FORMAT;
import static com.example.exceldemo.common.model.ExcelErrorType.WRONG_ZIP_CODE_FORMAT;
import static com.example.exceldemo.common.model.ExcelPattern.BIRTHDATE_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.CITY_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.CIVILITY_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.EMAIL_ADDRESS_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.FIRSTNAME_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.LASTNAME_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.MOBILE_NUMBER_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.STATE_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.STREET_ADDRESS_PATTERN;
import static com.example.exceldemo.common.model.ExcelPattern.ZIP_CODE_PATTERN;

@Data
public class ExcelRow {

    @ExcelConstraint(pattern = CIVILITY_PATTERN, errorType = WRONG_CIVILITY_FORMAT)
    private ExcelCell civility;

    @ExcelConstraint(pattern = FIRSTNAME_PATTERN, errorType = WRONG_FIRSTNAME_FORMAT)
    private ExcelCell firstname;

    @ExcelConstraint(pattern = LASTNAME_PATTERN, errorType = WRONG_LASTNAME_FORMAT)
    private ExcelCell lastname;

    @ExcelConstraint(pattern = BIRTHDATE_PATTERN, errorType = WRONG_BIRTHDATE_FORMAT)
    private ExcelCell birthdate;

    @ExcelConstraint(pattern = STREET_ADDRESS_PATTERN, errorType = WRONG_STREET_ADDRESS_FORMAT)
    private ExcelCell streetAddress;

    @ExcelConstraint(pattern = CITY_PATTERN, errorType = WRONG_CITY_FORMAT)
    private ExcelCell city;

    @ExcelConstraint(pattern = STATE_PATTERN, errorType = WRONG_STATE_FORMAT)
    private ExcelCell state;

    @ExcelConstraint(pattern = ZIP_CODE_PATTERN, errorType = WRONG_ZIP_CODE_FORMAT)
    private ExcelCell zipCode;

    @ExcelConstraint(pattern = MOBILE_NUMBER_PATTERN, errorType = WRONG_MOBILE_NUMBER_FORMAT)
    private ExcelCell mobileNumber;

    @ExcelConstraint(pattern = EMAIL_ADDRESS_PATTERN, errorType = WRONG_EMAIL_ADDRESS_FORMAT)
    private ExcelCell emailAddress;

    private int rowIndex;

}
