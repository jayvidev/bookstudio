package com.bookstudio.shared.code;

import java.time.LocalDate;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * Hands out the next business code of a series.
 *
 * <p>The counter is incremented with a single upsert, so concurrent callers are
 * serialized by the row lock and can never read the same value. It runs inside
 * the caller's transaction: if that transaction rolls back, the number is
 * released and the sequence stays gapless.
 */
@Component
@RequiredArgsConstructor
public class CodeGenerator {
    private final JdbcClient jdbc;

    @Transactional(propagation = Propagation.MANDATORY)
    public String next(CodeSeries series, LocalDate date) {
        long number = jdbc.sql("""
                INSERT INTO code_sequences (series, period, last_value)
                VALUES (:series, :period, 1)
                ON CONFLICT (series, period)
                DO UPDATE SET last_value = code_sequences.last_value + 1
                RETURNING last_value
                """)
                .param("series", series.prefix())
                .param("period", series.period().keyOf(date))
                .query(Long.class)
                .single();

        return series.format(date, number);
    }
}
