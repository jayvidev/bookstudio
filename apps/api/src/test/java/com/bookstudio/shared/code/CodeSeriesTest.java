package com.bookstudio.shared.code;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class CodeSeriesTest {

    private static final LocalDate DATE = LocalDate.of(2026, 10, 2);

    @Test
    void formatsPrefixYearAndPaddedCounter() {
        assertThat(new CodeSeries("PRE").format(DATE, 7)).isEqualTo("PRE-2026-00007");
    }

    @Test
    void rejectsPrefixThatIsNotThreeUppercaseLetters() {
        assertThatThrownBy(() -> new CodeSeries("MULT")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CodeSeries("pre")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void failsWhenCounterOverflowsFiveDigits() {
        assertThatThrownBy(() -> new CodeSeries("EJE").format(DATE, 100_000))
                .isInstanceOf(IllegalStateException.class);
    }
}
