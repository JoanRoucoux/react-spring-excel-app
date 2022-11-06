package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum ExcelHeader {

    CIVILITY("Civility"),
    FIRSTNAME("Firstname"),
    LASTNAME("Lastname"),
    BIRTHDATE("Birthdate"),
    STREET_ADDRESS("Street Address"),
    CITY("City"),
    STATE("State"),
    ZIPCODE("Zipcode"),
    MOBILE_NUMBER("Mobile Number"),
    EMAIL_ADDRESS("Email Address");

    private final String value;

    public static boolean isValid(String value) {
        return stream(values())
                .map(ExcelHeader::getValue)
                .anyMatch(value::equalsIgnoreCase);
    }

}
