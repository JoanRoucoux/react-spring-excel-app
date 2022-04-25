package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExcelFile {

    private int personListSize;
    private List<ExcelError> excelErrorList;

}
