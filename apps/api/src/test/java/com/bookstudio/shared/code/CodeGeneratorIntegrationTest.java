package com.bookstudio.shared.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import com.bookstudio.IntegrationTest;

@IntegrationTest
class CodeGeneratorIntegrationTest {

    private static final CodeSeries SERIES = new CodeSeries("TST");

    @Autowired
    CodeGenerator generator;

    @Autowired
    JdbcClient jdbc;

    @Test
    void startsAtOneAndIncrementsWithinPeriod() {
        LocalDate date = LocalDate.of(2026, 1, 15);

        assertThat(generator.next(SERIES, date)).isEqualTo("TST-2026-00001");
        assertThat(generator.next(SERIES, date)).isEqualTo("TST-2026-00002");
    }

    @Test
    void restartsCounterOnNewPeriod() {
        generator.next(SERIES, LocalDate.of(2026, 12, 31));

        assertThat(generator.next(SERIES, LocalDate.of(2027, 1, 1))).isEqualTo("TST-2027-00001");
    }

    @Test
    void continuesFromCodesThatExistedBeforeTheMigration() {
        String lastReaderCode = jdbc.sql("SELECT MAX(code) FROM readers").query(String.class).single();
        LocalDate sameYear = LocalDate.of(Integer.parseInt(lastReaderCode.split("-")[1]), 6, 1);
        long lastNumber = Long.parseLong(lastReaderCode.split("-")[2]);

        assertThat(generator.next(new CodeSeries("LEC"), sameYear))
                .endsWith("-%05d".formatted(lastNumber + 1));
    }

    @Test
    void everyExistingCodeFollowsTheUnifiedFormat() {
        List<String> invalid = jdbc.sql("""
                SELECT code FROM (
                    SELECT code FROM readers UNION ALL SELECT code FROM copies
                    UNION ALL SELECT code FROM loans UNION ALL SELECT code FROM reservations
                    UNION ALL SELECT code FROM fines UNION ALL SELECT code FROM payments
                ) all_codes
                WHERE code !~ '^(LEC|EJE|PRE|RES|MUL|PAG)-[0-9]{4}-[0-9]{5}$'
                """).query(String.class).list();

        assertThat(invalid).isEmpty();
    }
}
