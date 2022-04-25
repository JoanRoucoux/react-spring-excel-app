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
    BIRTH_CITY("Birth city"),
    BIRTH_DEPARTMENT("Birth department"),
    BIRTH_COUNTRY("Birth country"),
    ADDRESS("Address"),
    POSTCODE("Postcode"),
    CITY("City"),
    COUNTRY("Country"),
    PHONE_CODE("Phone code"),
    EMAIL_ADDRESS("Email address");

    private String value;

    public static boolean isValid(String value) {
        return stream(values())
                .map(ExcelHeader::getValue)
                .anyMatch(value::equalsIgnoreCase);
    }

}
