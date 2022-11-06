package com.example.exceldemo.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcelError {

    private int rowIndex;
    private String columnLetter;
    private String originalValue;
    private String excelErrorType;

}
