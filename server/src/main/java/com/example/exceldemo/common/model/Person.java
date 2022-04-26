package com.example.exceldemo.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private String civility;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String mobileNumber;
    private String emailAddress;

    public static Person fromExcelRow(ExcelRow excelRow) {
        return builder()
                .civility(excelRow.getCivility().getValue())
                .firstname(excelRow.getFirstname().getValue())
                .lastname(excelRow.getLastname().getValue())
                .birthdate(excelRow.getBirthdate().getValue())
                .streetAddress(excelRow.getStreetAddress().getValue())
                .city(excelRow.getCity().getValue())
                .state(excelRow.getState().getValue())
                .zipcode(excelRow.getZipcode().getValue())
                .mobileNumber(excelRow.getMobileNumber().getValue())
                .emailAddress(excelRow.getEmailAddress().getValue())
                .build();
    }

}
