package com.example.exceldemo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
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

    @ExceptionHandler(ExcelException.class)
    public ResponseEntity<String> handleExcelException() {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Error while reading file!");
    }

}
