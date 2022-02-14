package com.example.exceldemo.service;

import com.example.exceldemo.common.exception.ExcelException;
import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.Person;
import com.example.exceldemo.common.util.ExcelPOIHelper;
import com.example.exceldemo.common.util.MultipartFileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    @Autowired
    private ExcelPOIHelper excelPOIHelper;

    public Map<Person, List<ExcelError>> uploadFile(MultipartFile file) throws InvalidFileExtensionException, ExcelException {
        MultipartFileValidator.validateFileExtension(file);
        try (InputStream inputStream = file.getInputStream()) { // will automatically close input stream
            return excelPOIHelper.readExcel(inputStream);
        } catch (IOException e) {
            throw new ExcelException("[ExcelService][uploadFile] An error occurred while reading uploaded Excel file");
        }
    }
}
