package com.example.exceldemo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomeExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException() {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("File too large!");
    }

    @ExceptionHandler(InvalidFileExtensionException.class)
    public ResponseEntity<String> handleInvalidFileExtensionException() {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Invalid file extension!");
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<String> handleInvalidHeaderException() {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Invalid header!");
    }

    @ExceptionHandler(ExcelException.class)
    public ResponseEntity<String> handleExcelException() {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Error while reading file!");
    }

}
