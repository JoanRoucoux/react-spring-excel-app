package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MultipartFileValidatorTest {

    @Test
    void validateExcelFileExtension_should_throw_InvalidFileExtensionException_when_file_extension_is_not_excel() {
        // GIVEN
        MockMultipartFile csvFile = new MockMultipartFile("data", "filename.csv", "text/plain", "some csv".getBytes());

        // WHEN
        try {
            MultipartFileValidator.validateExcelFileExtension(csvFile);
            fail("An InvalidFileExtensionException should be thrown");
        } catch (InvalidFileExtensionException invalidFileExtensionException) {
            // THEN
            assertEquals("[MultipartFileValidator][validateFileExtension] Invalid file extension: csv", invalidFileExtensionException.getMessage());
        }
    }

}
