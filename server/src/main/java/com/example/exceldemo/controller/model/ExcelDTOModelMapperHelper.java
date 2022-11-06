package com.example.exceldemo.controller.model;

import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.PersonDocument;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ExcelDTOModelMapperHelper {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Build and sort list of ExcelErrorDTO objects from list of ExcelError
     *
     * @param excelErrorList list of Excel errors
     * @return {@link List<ExcelErrorDTO>} list of ExcelErrorDTO
     */
    public List<ExcelErrorDTO> buildExcelErrorDTOList(List<ExcelError> excelErrorList) {
        List<ExcelErrorDTO> excelErrorDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(excelErrorList)) {
            excelErrorDTOList = excelErrorList.stream()
                    .map(this::buildExcelErrorDTO)
                    .sorted(Comparator.comparing(ExcelErrorDTO::getRowIndex))
                    .toList();
        }
        return excelErrorDTOList;
    }

    /**
     * Build ExcelErrorDTO object from ExcelError
     *
     * @param excelError Excel error
     * @return {@link ExcelErrorDTO} object
     */
    public ExcelErrorDTO buildExcelErrorDTO(ExcelError excelError) {
        return modelMapper.map(excelError, ExcelErrorDTO.class);
    }

    /**
     * Build PersonDTO object from PersonDocument
     *
     * @param personDocument person document
     * @return {@link PersonDTO} object
     */
    public PersonDTO buildPersonDTO(PersonDocument personDocument) {
        return modelMapper.map(personDocument, PersonDTO.class);
    }

}
