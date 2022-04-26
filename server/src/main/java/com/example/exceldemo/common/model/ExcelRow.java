package com.example.exceldemo.common.model;

import lombok.Data;

@Data
public class ExcelRow {

    private ExcelCell civility;
    private ExcelCell firstname;
    private ExcelCell lastname;
    private ExcelCell birthdate;
    private ExcelCell streetAddress;
    private ExcelCell city;
    private ExcelCell state;
    private ExcelCell zipcode;
    private ExcelCell mobileNumber;
    private ExcelCell emailAddress;
    private int rowIndex;

}
