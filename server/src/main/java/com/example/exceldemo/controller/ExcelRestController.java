package com.example.exceldemo.controller;

import com.example.exceldemo.common.exception.ExcelException;
import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.Person;
import com.example.exceldemo.controller.model.ExcelFileDTO;
import com.example.exceldemo.controller.model.ExcelModelMapperHelper;
import com.example.exceldemo.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(path = "/excel")
public class ExcelRestController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExcelModelMapperHelper excelModelMapperHelper;

    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExcelFileDTO> uploadFile(@RequestParam("file") MultipartFile file) throws ExcelException, InvalidFileExtensionException {
        Map<Person, List<ExcelError>> personListMap = excelService.uploadFile(file);
        ExcelFileDTO excelFileDTO = excelModelMapperHelper.buildExcelFileDTO(personListMap);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(excelFileDTO);
    }

}
