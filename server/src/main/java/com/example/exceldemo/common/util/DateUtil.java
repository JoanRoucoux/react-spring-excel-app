package com.example.exceldemo.common.util;

import com.example.exceldemo.common.exception.TechnicalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    public static final DateTimeFormatter LOCALE_EN_US_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Format a given date to MM/dd/yyyy
     *
     * @param date date to format
     * @return {@link LocalDate} formatted date
     * @throws TechnicalException if something goes wrong while parsing the date
     */
    public static LocalDate formatWebDateStringAsLocalDate(String date) throws TechnicalException {
        return fromStringToLocalDate(date, LOCALE_EN_US_DATE_FORMAT);
    }

    /**
     * Format a given date to a specific pattern
     *
     * @param date              date to format
     * @param dateTimeFormatter pattern
     * @return {@link LocalDate} formatted date
     * @throws TechnicalException if something goes wrong while parsing the date
     */
    private static LocalDate fromStringToLocalDate(String date, DateTimeFormatter dateTimeFormatter) throws TechnicalException {
        try {
            return isNotBlank(date) ? LocalDate.parse(date, dateTimeFormatter) : null;
        } catch (Exception e) {
            throw new TechnicalException("[DateUtil][fromStringToLocalDate] Can't parse date " + e.getMessage());
        }
    }

}
