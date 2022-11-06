package com.example.exceldemo.service;

import com.example.exceldemo.common.exception.InvalidFileExtensionException;
import com.example.exceldemo.common.exception.InvalidFormatException;
import com.example.exceldemo.common.exception.InvalidHeaderException;
import com.example.exceldemo.common.exception.InvalidRowSizeException;
import com.example.exceldemo.common.exception.TechnicalException;
import com.example.exceldemo.common.model.ExcelCell;
import com.example.exceldemo.common.model.ExcelError;
import com.example.exceldemo.common.model.ExcelRow;
import com.example.exceldemo.common.model.PersonDocument;
import com.example.exceldemo.common.util.ExcelPOIHelper;
import com.example.exceldemo.common.util.MultipartFileValidator;
import com.example.exceldemo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.exceldemo.common.model.ExcelErrorType.DUPLICATE_EMAIL_ADDRESS;
import static java.util.Objects.nonNull;

@Service
public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);
    private static final String EXCEL_TEMPLATE_RESOURCE_PATH = "templates/excel_template.xlsx";
    private static final int MAX_ROWS_ALLOWED = 500;

    @Autowired
    private ExcelPOIHelper excelPOIHelper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private Validator validator;

    /**
     * Get Excel template from resources folder
     *
     * @return {@link InputStream} input stream
     * @throws TechnicalException if an error occurred while getting input stream
     */
    public InputStream getTemplate() throws TechnicalException {
        try {
            return new ClassPathResource(EXCEL_TEMPLATE_RESOURCE_PATH)
                    .getInputStream();
        } catch (IOException e) {
            throw new TechnicalException("[ExcelService][getTemplate] An error occurred while retrieving Excel template file");
        }
    }

    /**
     * Extract data from file, collect errors and save it to database if none encountered
     *
     * @param file file to read
     * @return int number of persons
     * @throws InvalidFileExtensionException if file doesn't have an Excel file extension
     * @throws InvalidRowSizeException       if Excel sheet contains more rows than the given max
     * @throws InvalidHeaderException        if file doesn't have required header
     * @throws TechnicalException            if an error occurred while reading file
     * @throws InvalidFormatException        if file contains invalid rows
     */
    public int uploadFile(MultipartFile file)
            throws InvalidFileExtensionException, InvalidRowSizeException, InvalidHeaderException, TechnicalException, InvalidFormatException {
        MultipartFileValidator.validateExcelFileExtension(file);

        // Extracting data from uploaded Excel file
        List<ExcelRow> excelRowList;
        try (InputStream inputStream = file.getInputStream()) { // will automatically close input stream
            excelRowList = excelPOIHelper.readExcel(inputStream, MAX_ROWS_ALLOWED);
            logger.info("[ExcelService][uploadFile] Number of extracted rows: {}", excelRowList.size());
        } catch (IOException e) {
            throw new TechnicalException("[ExcelService][uploadFile] An error occurred while reading uploaded Excel file");
        }

        // Validating extracted data and collecting errors
        validateExcelRowList(excelRowList);

        // Mapping and saving data to database
        final List<PersonDocument> personDocumentList = excelRowList.stream()
                .map(PersonDocument::fromExcelRow)
                .toList();
        personRepository.saveAll(personDocumentList);

        // Building object response
        return excelRowList.size();
    }

    /**
     * Retrieve persons from database
     *
     * @return {@link List<PersonDocument>} list of persons
     */
    public List<PersonDocument> getPersons() {
        return personRepository.findAll();
    }

    /**
     * Delete persons from database
     */
    public void deletePersons() {
        personRepository.deleteAll();
    }

    /**
     * Validate extracted data and collect errors
     *
     * @param excelRowList list of ExcelRow
     * @throws InvalidFormatException if file contains invalid rows
     */
    private void validateExcelRowList(List<ExcelRow> excelRowList) throws InvalidFormatException {
        List<ExcelError> excelErrorList = new ArrayList<>();

        validateExcelRowListPatterns(excelRowList, excelErrorList);
        validateExcelRowListDuplicates(excelRowList, excelErrorList);

        if (!excelErrorList.isEmpty()) {
            throw new InvalidFormatException(
                    "[ExcelService][uploadFile] Uploaded Excel file contains " + excelErrorList.size() + " errors",
                    excelErrorList
            );
        }
    }

    /**
     * Validate patterns and collect errors
     *
     * @param excelRowList   list of ExcelRow
     * @param excelErrorList list of ExcelError
     */
    private void validateExcelRowListPatterns(List<ExcelRow> excelRowList, List<ExcelError> excelErrorList) {
        excelRowList.forEach(excelRow -> {
            final Set<ConstraintViolation<ExcelRow>> violations = validator.validate(excelRow);
            violations.stream()
                    .filter(excelRowConstraintViolation -> nonNull(excelRowConstraintViolation.getInvalidValue()))
                    .forEach(excelRowConstraintViolation -> {
                        final ExcelCell invalidExcelCell = (ExcelCell) excelRowConstraintViolation.getInvalidValue();
                        final ExcelError excelError = ExcelError.builder()
                                .rowIndex(excelRow.getRowIndex())
                                .columnLetter(invalidExcelCell.getColumnLetter())
                                .originalValue(invalidExcelCell.getValue())
                                .excelErrorType(excelRowConstraintViolation.getMessage())
                                .build();
                        excelErrorList.add(excelError);
                    });
        });
    }

    /**
     * Validate duplicate email addresses and collect errors
     *
     * @param excelRowList   list of ExcelRow
     * @param excelErrorList list of ExcelError
     */
    private static void validateExcelRowListDuplicates(List<ExcelRow> excelRowList, List<ExcelError> excelErrorList) {
        final List<ExcelRow> duplicatesExcelRowList = excelRowList.stream()
                .collect(Collectors.groupingBy(ExcelRow::getEmailAddress))
                .entrySet().stream()
                .filter(excelCellListEntry -> excelCellListEntry.getValue().size() > 1)
                .flatMap(excelCellListEntry -> excelCellListEntry.getValue().stream())
                .toList();
        if (!CollectionUtils.isEmpty(duplicatesExcelRowList)) {
            duplicatesExcelRowList.forEach(excelRow -> {
                final ExcelCell excelCell = excelRow.getEmailAddress();
                final ExcelError excelError = ExcelError.builder()
                        .rowIndex(excelRow.getRowIndex())
                        .columnLetter(excelCell.getColumnLetter())
                        .originalValue(excelCell.getValue())
                        .excelErrorType(DUPLICATE_EMAIL_ADDRESS.getValue())
                        .build();
                excelErrorList.add(excelError);
            });
        }
    }

}
