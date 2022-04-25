package com.example.exceldemo.controller;

import com.example.exceldemo.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExcelRestControllerTest {

    @InjectMocks
    private ExcelRestController excelRestController;

    @Mock
    private ExcelService excelService;

    @Test
    void getTemplate_should_return_resource_file() {
        // GIVEN


        // WHEN


        // THEN

    }

    @Test
    void uploadFile_should_return_resource_file() {
        // GIVEN


        // WHEN


        // THEN

    }

}
