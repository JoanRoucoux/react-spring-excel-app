package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExcelFileDTO {

    @Schema(example = "24")
    private int personListSize;

    private List<ExcelErrorDTO> excelErrorDTOList;

}
