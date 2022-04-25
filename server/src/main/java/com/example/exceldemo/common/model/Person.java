package com.example.exceldemo.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private String civility;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String birthCity;
    private String birthDepartment;
    private String birthCountry;
    private String address;
    private String postCode;
    private String city;
    private String country;
    private String phoneCode;
    private String phoneNumber;
    private String emailAddress;

    public static Person fromExcelRow(ExcelRow excelRow) {
        return builder()
                .civility(excelRow.getCivility().getValue())
                .firstName(excelRow.getFirstName().getValue())
                .lastName(excelRow.getLastName().getValue())
                .birthDate(excelRow.getBirthDate().getValue())
                .birthCity(excelRow.getBirthCity().getValue())
                .birthDepartment(excelRow.getBirthDepartment().getValue())
                .birthCountry(excelRow.getBirthCountry().getValue())
                .address(excelRow.getAddress().getValue())
                .postCode(excelRow.getPostCode().getValue())
                .city(excelRow.getCity().getValue())
                .country(excelRow.getCountry().getValue())
                .phoneCode(excelRow.getPhoneCode().getValue())
                .phoneNumber(excelRow.getPhoneNumber().getValue())
                .emailAddress(excelRow.getEmailAddress().getValue())
                .build();
    }

}
