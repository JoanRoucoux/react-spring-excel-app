package com.example.exceldemo.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ErrorListDTO extends ErrorDTO {

    private List<ExcelErrorDTO> excelErrorList;

    public ErrorListDTO(String reason, String message, LocalDateTime timestamp, List<ExcelErrorDTO> excelErrorList) {
        super(reason, message, timestamp);
        this.excelErrorList = excelErrorList;
    }

}
