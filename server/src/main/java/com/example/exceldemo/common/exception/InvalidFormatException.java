package com.example.exceldemo.common.exception;

import com.example.exceldemo.common.model.ExcelError;
import lombok.Getter;

import java.util.List;

public class InvalidFormatException extends FunctionalException {

    @Getter
    private final transient List<ExcelError> excelErrorList;

    public InvalidFormatException(String message, List<ExcelError> excelErrorList) {
        super(message);
        this.excelErrorList = excelErrorList;
    }

}
