package com.bookstudio.shared.code;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * A family of human-readable business codes, e.g. {@code PRE-2026-00001}:
 * three-letter prefix, the year the counter restarts on, and a five-digit counter.
 */
public record CodeSeries(String prefix) {

    private static final Pattern PREFIX = Pattern.compile("[A-Z]{3}");
    private static final long MAX_COUNTER = 99_999;

    public CodeSeries {
        if (prefix == null || !PREFIX.matcher(prefix).matches()) {
            throw new IllegalArgumentException("Code prefix must be three uppercase letters: " + prefix);
        }
    }

    public String periodOf(LocalDate date) {
        return String.valueOf(date.getYear());
    }

    public String format(LocalDate date, long counter) {
        if (counter < 1 || counter > MAX_COUNTER) {
            throw new IllegalStateException(
                    "Counter %d out of range for series %s in %s".formatted(counter, prefix, periodOf(date)));
        }
        return "%s-%s-%05d".formatted(prefix, periodOf(date), counter);
    }
}
