package com.example.exceldemo.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcelFileDTO {

    private int personListSize;
    private int excelErrorListSize;
    private List<ExcelErrorDTO> excelErrorDTOList;

}
