package com.example.exceldemo.controller.model;

import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExcelModelMapperHelper {

    @Autowired
    private ModelMapper modelMapper;

    public ExcelFileDTO buildExcelFileDTO(Map<Person, List<ExcelError>> personListMap) {
        int personListSize = personListMap.size();
        int excelErrorListSum = personListMap.values().stream().mapToInt(List::size).sum();
        List<ExcelErrorDTO> excelErrorDTOList = personListMap.values().stream()
                .flatMap(List::stream)
                .map(this::buildExcelErrorDTO)
                .collect(Collectors.toList());

        return ExcelFileDTO.builder()
                .personListSize(personListSize)
                .excelErrorListSize(excelErrorListSum)
                .excelErrorDTOList(excelErrorDTOList)
                .build();
    }

    public ExcelErrorDTO buildExcelErrorDTO(ExcelError excelError) {
        return modelMapper.map(excelError, ExcelErrorDTO.class);
    }

}
