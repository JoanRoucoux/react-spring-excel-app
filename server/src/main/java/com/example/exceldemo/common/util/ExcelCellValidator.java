package com.example.exceldemo.common.util;

import java.util.regex.Pattern;

public class ExcelCellValidator {

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String POSTCODE_REGEX_PATTERN = "^(?:0[1-9]|[1-8]\\d|9[0-8])\\d{3}$";

    public static boolean isEmailAddressValid(String emailAddress) {
        return patternMatches(emailAddress, EMAIL_REGEX_PATTERN);
    }

    public static boolean isPostCodeValid(String postCode) {
        return patternMatches(postCode, POSTCODE_REGEX_PATTERN);
    }

    private static boolean patternMatches(String value, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }

}
