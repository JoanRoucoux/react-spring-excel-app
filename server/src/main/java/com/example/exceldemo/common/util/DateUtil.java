package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.TechnicalException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DateUtil {

    public static final DateTimeFormatter WEB_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate formatWebDateStringAsLocalDate(String date) throws TechnicalException {
        return fromStringToLocalDate(date, WEB_DATE_FORMAT);
    }

    private static LocalDate fromStringToLocalDate(String date, DateTimeFormatter dateTimeFormatter) throws TechnicalException {
        try {
            return isNotBlank(date) ? LocalDate.parse(date, dateTimeFormatter) : null;
        } catch (Exception e) {
            throw new TechnicalException("[DateUtil][fromStringToLocalDate] Can't parse date " + e.getMessage());
        }
    }
}
