package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelCell {

    private String value;
    private String columnLetter;
    private int columnIndex;

}
