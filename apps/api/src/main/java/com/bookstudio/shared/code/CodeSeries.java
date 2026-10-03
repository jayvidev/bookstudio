package com.bookstudio.shared.code;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Format of a human-readable business code, e.g. {@code PRE-20261002-00001}:
 * prefix, period key and a zero-padded counter that restarts every period.
 */
public record CodeSeries(String prefix, CodePeriod period, int digits) {

    public CodeSeries {
        Objects.requireNonNull(prefix, "prefix");
        Objects.requireNonNull(period, "period");
        if (digits < 1) {
            throw new IllegalArgumentException("digits must be positive");
        }
    }

    public String format(LocalDate date, long number) {
        String counter = String.valueOf(number);
        if (counter.length() > digits) {
            throw new IllegalStateException(
                    "Code counter %s overflowed %d digits for %s".formatted(counter, digits, prefix));
        }
        return prefix + "-" + period.keyOf(date) + "-" + "0".repeat(digits - counter.length()) + counter;
    }
}
