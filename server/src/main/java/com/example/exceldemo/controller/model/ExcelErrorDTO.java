package com.example.exceldemo.controller.model;

import lombok.Data;

@Data
public class ExcelErrorDTO {

    private int rowIndex;
    private String columnLetter;
    private String originalValue;
    private String excelErrorType;

}
