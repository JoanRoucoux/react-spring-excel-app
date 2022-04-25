package com.example.exceldemo.service;

import com.example.exceldemo.common.util.ExcelPOIHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;

@ExtendWith(MockitoExtension.class)
class ExcelServiceTest {

    @InjectMocks
    private ExcelService excelService;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private ExcelPOIHelper excelPOIHelper;

    @Test
    void getTemplate_should_return_resource_file() {
        // GIVEN


        // WHEN


        // THEN

    }

}
