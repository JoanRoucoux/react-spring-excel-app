package com.example.exceldemo.common.validator;

import com.example.exceldemo.common.constraint.ExcelConstraint;
import com.example.exceldemo.common.model.ExcelCell;
import com.example.exceldemo.common.model.ExcelErrorType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public class ExcelValidator implements ConstraintValidator<ExcelConstraint, ExcelCell> {

    private Pattern pattern;
    private ExcelErrorType errorType;

    @Override
    public void initialize(ExcelConstraint excelConstraint) {
        this.pattern = Pattern.compile(excelConstraint.pattern().getValue(), Pattern.CASE_INSENSITIVE);
        this.errorType = excelConstraint.errorType();
    }

    @Override
    public boolean isValid(ExcelCell excelCell, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(this.errorType.getValue()).addConstraintViolation();
        return nonNull(excelCell) && this.pattern.matcher(excelCell.getValue()).matches();
    }

}
