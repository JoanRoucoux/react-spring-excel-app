package com.example.exceldemo.controller.model;

import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.ExcelFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExcelModelMapperHelper {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Build ExcelFileDTO object from ExcelFile
     *
     * @param excelFile Excel file data
     * @return ExcelFileDTO object
     */
    public ExcelFileDTO buildExcelFileDTO(ExcelFile excelFile) {
        List<ExcelErrorDTO> excelErrorDTOList = excelFile.getExcelErrorList().stream()
                .map(this::buildExcelErrorDTO)
                .collect(Collectors.toList());

        return new ExcelFileDTO(excelFile.getPersonListSize(), excelErrorDTOList);
    }

    /**
     * Build ExcelErrorDTO object from ExcelError
     *
     * @param excelError Excel error
     * @return ExcelErrorDTO object
     */
    public ExcelErrorDTO buildExcelErrorDTO(ExcelError excelError) {
        return modelMapper.map(excelError, ExcelErrorDTO.class);
    }

}
