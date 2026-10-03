package com.bookstudio.shared.code;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class CodeSeriesTest {

    private static final LocalDate DATE = LocalDate.of(2026, 10, 2);

    @Test
    void formatsDailySeries() {
        assertThat(new CodeSeries("PRE", CodePeriod.DAY, 5).format(DATE, 7))
                .isEqualTo("PRE-20261002-00007");
    }

    @Test
    void formatsYearlySeries() {
        assertThat(new CodeSeries("EJ", CodePeriod.YEAR, 4).format(DATE, 123))
                .isEqualTo("EJ-2026-0123");
    }

    @Test
    void failsWhenCounterOverflowsDigits() {
        CodeSeries series = new CodeSeries("EJ", CodePeriod.YEAR, 4);

        assertThatThrownBy(() -> series.format(DATE, 10_000))
                .isInstanceOf(IllegalStateException.class);
    }
}
