package com.example.exceldemo.common.constraint;

import com.example.exceldemo.common.model.ExcelErrorType;
import com.example.exceldemo.common.model.ExcelPattern;
import com.example.exceldemo.common.validator.ExcelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ExcelValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface ExcelConstraint {

    ExcelPattern pattern();

    ExcelErrorType errorType();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
