package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelErrorType {

    WRONG_CIVILITY_FORMAT("wrong_civility_format"),
    WRONG_FIRSTNAME_FORMAT("wrong_firstname_format"),
    WRONG_LASTNAME_FORMAT("wrong_lastname_format"),
    WRONG_BIRTHDATE_FORMAT("wrong_birthdate_format"),
    WRONG_STREET_ADDRESS_FORMAT("wrong_street_address_format"),
    WRONG_CITY_FORMAT("wrong_city_format"),
    WRONG_STATE_FORMAT("wrong_state_format"),
    WRONG_ZIP_CODE_FORMAT("wrong_zip_code_format"),
    WRONG_MOBILE_NUMBER_FORMAT("wrong_mobile_number_format"),
    WRONG_EMAIL_ADDRESS_FORMAT("wrong_email_address_format"),
    DUPLICATE_EMAIL_ADDRESS("duplicate_email_address");

    private final String value;

}
