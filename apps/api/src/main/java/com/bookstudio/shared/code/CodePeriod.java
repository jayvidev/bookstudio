package com.bookstudio.shared.code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Time window a code counter restarts on.
 */
public enum CodePeriod {
    YEAR(DateTimeFormatter.ofPattern("yyyy")),
    DAY(DateTimeFormatter.BASIC_ISO_DATE);

    private final DateTimeFormatter formatter;

    CodePeriod(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public String keyOf(LocalDate date) {
        return date.format(formatter);
    }
}
