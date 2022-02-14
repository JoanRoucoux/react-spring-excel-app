package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class MultipartFileValidator {

    public static final Set<String> VALID_FILE_EXTENSION_SET = Set.of("xls", "xlsx", "xlsm");

    public static void validateFileExtension(MultipartFile file) throws InvalidFileExtensionException {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!VALID_FILE_EXTENSION_SET.contains(fileExtension)) {
            throw new InvalidFileExtensionException("[MultipartFileValidator][validateFileExtension] Invalid file extension " + fileExtension);
        }
    }

}
