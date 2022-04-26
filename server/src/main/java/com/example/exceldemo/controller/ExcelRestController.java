package com.example.exceldemo.controller;

import com.example.exceldemo.common.exception.ExcelException;
import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.model.ExcelFile;
import com.example.exceldemo.controller.model.ExcelFileDTO;
import com.example.exceldemo.controller.model.ExcelModelMapperHelper;
import com.example.exceldemo.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Excel Resource")
@RestController
@RequestMapping(path = "/api/excel")
public class ExcelRestController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExcelModelMapperHelper excelModelMapperHelper;

    @Operation(summary = "Get Excel template")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                                    schema = @Schema(implementation = Resource.class)
                            )
                    }
            ),
    })
    @GetMapping("/download")
    public ResponseEntity<Resource> getTemplate() throws IOException {
        InputStreamResource excelTemplate = new InputStreamResource(excelService.getTemplate());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=excel_template")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelTemplate);
    }

    @Operation(summary = "Upload Excel file")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExcelFileDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "File too large / Invalid file extension / Error while reading file / Invalid header",
                    content = @Content
            ),
    })
    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExcelFileDTO> uploadFile(
            @Parameter(description = "Excel file to upload")
            @RequestParam("file") MultipartFile file) throws ExcelException, InvalidFileExtensionException, InvalidHeaderException {
        ExcelFile excelFile = excelService.uploadFile(file);
        ExcelFileDTO excelFileDTO = excelModelMapperHelper.buildExcelFileDTO(excelFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(excelFileDTO);
    }

}
