package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultipartFileValidator {

    public static final Set<String> EXCEL_FILE_EXTENSION_SET = Set.of("xls", "xlsx", "xlsm");

    /**
     * Validate Excel file extensions
     *
     * @param file file to validate
     * @throws InvalidFileExtensionException when file extension is invalid
     */
    public static void validateExcelFileExtension(MultipartFile file) throws InvalidFileExtensionException {
        validateFileExtension(file, EXCEL_FILE_EXTENSION_SET);
    }

    /**
     * Validate file extensions
     *
     * @param file         file to validate
     * @param extensionSet file extensions to compare
     * @throws InvalidFileExtensionException when file extension is invalid
     */
    private static void validateFileExtension(MultipartFile file, Set<String> extensionSet) throws InvalidFileExtensionException {
        final String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extensionSet.contains(fileExtension)) {
            throw new InvalidFileExtensionException("[MultipartFileValidator][validateFileExtension] Invalid file extension: " + fileExtension);
        }
    }

}
