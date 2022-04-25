package com.example.exceldemo.common.model;

import lombok.Data;

@Data
public class ExcelRow {

    private int rowIndex;
    private ExcelCell civility;
    private ExcelCell firstName;
    private ExcelCell lastName;
    private ExcelCell birthDate;
    private ExcelCell birthCity;
    private ExcelCell birthDepartment;
    private ExcelCell birthCountry;
    private ExcelCell address;
    private ExcelCell postCode;
    private ExcelCell city;
    private ExcelCell country;
    private ExcelCell phoneCode;
    private ExcelCell phoneNumber;
    private ExcelCell emailAddress;

}
