package com.example.exceldemo.controller;

import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.exception.InvalidFormatException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.exception.InvalidRowSizeException;
import com.example.exceldemo.common.exception.TechnicalException;
import com.example.exceldemo.common.model.PersonDocument;
import com.example.exceldemo.controller.model.ExcelDTOModelMapperHelper;
import com.example.exceldemo.controller.model.PersonDTO;
import com.example.exceldemo.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Excel Resource")
@RestController
@RequestMapping(path = "/api/excel")
public class ExcelRestController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExcelDTOModelMapperHelper excelDTOModelMapperHelper;

    @Operation(summary = "Get Excel template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", schema = @Schema(implementation = Resource.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping("/download")
    public ResponseEntity<Resource> getTemplate() throws TechnicalException {
        final InputStreamResource excelTemplate = new InputStreamResource(excelService.getTemplate());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=excel_template.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelTemplate);
    }

    @Operation(summary = "Upload Excel file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "24"))
            }),
            @ApiResponse(responseCode = "417", description = "File too large / Invalid file extension / Invalid row size / Invalid header / Invalid format"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> uploadFile(
            @Parameter(required = true, description = "Excel file to upload")
            @RequestParam("file") final MultipartFile file)
            throws InvalidFileExtensionException, InvalidRowSizeException, InvalidHeaderException, InvalidFormatException, TechnicalException {
        final int personListSize = excelService.uploadFile(file);
        return ResponseEntity.ok()
                .body(personListSize);
    }

    @Operation(summary = "Retrieve uploaded persons from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping("/persons")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        final List<PersonDocument> personDocumentList = excelService.getPersons();
        final List<PersonDTO> personDTOList = personDocumentList.stream()
                .map(excelDTOModelMapperHelper::buildPersonDTO)
                .toList();
        return ResponseEntity.ok()
                .body(personDTOList);
    }

    @Operation(summary = "Delete uploaded persons from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @DeleteMapping("/persons")
    public ResponseEntity<Void> deletePersons() {
        excelService.deletePersons();
        return ResponseEntity.noContent()
                .build();
    }

}
