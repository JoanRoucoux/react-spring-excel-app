package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ExcelErrorDTO {

    @Schema(example = "16")
    private int rowIndex;

    @Schema(example = "B")
    private String columnLetter;

    @Schema(example = "Smith")
    private String originalValue;

    @Schema(example = "WRONG_LASTNAME_FORMAT")
    private String excelErrorType;

}
