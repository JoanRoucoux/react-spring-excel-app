package com.example.exceldemo.controller;

import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.exception.InvalidFormatException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.exception.InvalidRowSizeException;
import com.example.exceldemo.common.exception.ResourceNotFoundException;
import com.example.exceldemo.controller.model.ErrorDTO;
import com.example.exceldemo.controller.model.ErrorListDTO;
import com.example.exceldemo.controller.model.ExcelDTOModelMapperHelper;
import com.example.exceldemo.controller.model.ExcelErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.exceldemo.common.model.ErrorReason.ERR_FUNC_INVALID_FILE_EXTENSION;
import static com.example.exceldemo.common.model.ErrorReason.ERR_FUNC_INVALID_FORMAT;
import static com.example.exceldemo.common.model.ErrorReason.ERR_FUNC_INVALID_HEADER;
import static com.example.exceldemo.common.model.ErrorReason.ERR_FUNC_INVALID_ROW_SIZE;
import static com.example.exceldemo.common.model.ErrorReason.ERR_FUNC_MAX_UPLOAD_SIZE;
import static com.example.exceldemo.common.model.ErrorReason.ERR_FUNC_RESOURCE_NOT_FOUND;
import static com.example.exceldemo.common.model.ErrorReason.ERR_TECH;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private ExcelDTOModelMapperHelper excelDTOModelMapperHelper;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        final ErrorDTO errorDTO = new ErrorDTO(
                ERR_FUNC_MAX_UPLOAD_SIZE.getValue(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(errorDTO);
    }

    @ExceptionHandler(InvalidFileExtensionException.class)
    public ResponseEntity<ErrorDTO> handleInvalidFileExtensionException(InvalidFileExtensionException e) {
        final ErrorDTO errorDTO = new ErrorDTO(
                ERR_FUNC_INVALID_FILE_EXTENSION.getValue(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(errorDTO);
    }

    @ExceptionHandler(InvalidRowSizeException.class)
    public ResponseEntity<ErrorDTO> handleInvalidRowSizeException(InvalidRowSizeException e) {
        final ErrorDTO errorDTO = new ErrorDTO(
                ERR_FUNC_INVALID_ROW_SIZE.getValue(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(errorDTO);
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ErrorDTO> handleInvalidHeaderException(InvalidHeaderException e) {
        final ErrorDTO errorDTO = new ErrorDTO(
                ERR_FUNC_INVALID_HEADER.getValue(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(errorDTO);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorListDTO> handleInvalidFormatException(InvalidFormatException e) {
        final List<ExcelErrorDTO> excelErrorDTOList = excelDTOModelMapperHelper.buildExcelErrorDTOList(e.getExcelErrorList());
        final ErrorListDTO errorListResponseDTO = new ErrorListDTO(
                ERR_FUNC_INVALID_FORMAT.getValue(),
                e.getMessage(),
                LocalDateTime.now(),
                excelErrorDTOList
        );
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(errorListResponseDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException e) {
        final ErrorDTO errorDTO = new ErrorDTO(
                ERR_FUNC_RESOURCE_NOT_FOUND.getValue(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(NOT_FOUND)
                .body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception e) {
        final ErrorDTO errorDTO = new ErrorDTO(
                ERR_TECH.getValue(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(errorDTO);
    }

}
