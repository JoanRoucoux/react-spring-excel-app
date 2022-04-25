package com.example.exceldemo.service;

import com.example.exceldemo.common.exception.ExcelException;
import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.ExcelFile;
import com.example.exceldemo.common.model.ExcelRow;
import com.example.exceldemo.common.model.Person;
import com.example.exceldemo.common.util.ExcelPOIHelper;
import com.example.exceldemo.common.util.MultipartFileValidator;
import com.example.exceldemo.common.util.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);
    private static final String EXCEL_TEMPLATE_RESOURCE_PATH = "templates/excel_template.xlsx";

    /**
     * Get Excel template from resources folder
     *
     * @return input stream
     * @throws IOException if an error occurred while getting input stream
     */
    public InputStream getTemplate() throws IOException {
        return new ClassPathResource(EXCEL_TEMPLATE_RESOURCE_PATH)
                .getInputStream();
    }

    /**
     * Extract data from file, collect errors and save it to database if none encountered
     *
     * @param file file to read
     * @return excel file object
     * @throws InvalidFileExtensionException if file doesn't have an Excel file extension
     * @throws ExcelException if an error occurred while reading file
     * @throws InvalidHeaderException if file doesn't have required header
     */
    public ExcelFile uploadFile(MultipartFile file) throws InvalidFileExtensionException, ExcelException, InvalidHeaderException {
        MultipartFileValidator.validateExcelFileExtension(file);

        // Extracting data from uploaded Excel file
        List<ExcelRow> excelRowList;
        try (InputStream inputStream = file.getInputStream()) { // will automatically close input stream
           excelRowList = ExcelPOIHelper.readExcel(inputStream);
           logger.info("[ExcelService][uploadFile] Number of extracted rows: {}", excelRowList.size());
        } catch (IOException e) {
            throw new ExcelException("[ExcelService][uploadFile] An error occurred while reading uploaded Excel file");
        }

        // Validating extracted data and collecting errors
        List<ExcelError> excelErrorList = new ArrayList<>();
        excelRowList.forEach(excelRow -> PersonValidator.validateExcelRow(excelRow, excelErrorList));
        logger.info("[ExcelService][uploadFile] Number of errors: {}", excelErrorList.size());

        // Mapping and saving data to database if no errors
        if (excelErrorList.isEmpty()) {
            List<Person> personList = excelRowList.stream()
                    .map(Person::fromExcelRow)
                    .collect(Collectors.toList());
            // TODO: save it to DB
        }

        // Building object response
        return new ExcelFile(excelRowList.size(), excelErrorList);
    }

}
