package com.bookstudio.loan;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.IntegrationTest;

/**
 * Runs without the per-test rollback so each request commits for real, which is
 * what atomicity and concurrency depend on. Cleans up after itself.
 */
@IntegrationTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class LoanConcurrencyIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    private long lastLoanId;
    private List<Long> copies;

    @BeforeEach
    void rememberState() {
        lastLoanId = jdbc.sql("SELECT COALESCE(MAX(id), 0) FROM loans").query(Long.class).single();
        copies = jdbc.sql("SELECT id FROM copies WHERE status = 'DISPONIBLE' ORDER BY id LIMIT 2").query(Long.class).list();
    }

    @AfterEach
    void restoreState() {
        jdbc.sql("DELETE FROM loans WHERE id > ?").param(lastLoanId).update();
        jdbc.sql("UPDATE copies SET status = 'DISPONIBLE' WHERE id IN (:ids)").param("ids", copies).update();
    }

    @Test
    void aFailedLoanDoesNotLendAnyOfItsCopies() {
        long free = copies.get(0);
        long busy = jdbc.sql("SELECT id FROM copies WHERE status = 'PRESTADO' LIMIT 1").query(Long.class).single();

        assertThat(post(loanJson(free, busy)).getResponse().getStatus()).isEqualTo(409);

        assertThat(copyStatus(free)).isEqualTo("DISPONIBLE");
        assertThat(jdbc.sql("SELECT COUNT(*) FROM loans WHERE id > ?").param(lastLoanId).query(Long.class).single())
                .isZero();
    }

    @Test
    void twoSimultaneousLoansOfTheSameCopyLetOnlyOneWin() throws Exception {
        long copyId = copies.get(0);
        CountDownLatch start = new CountDownLatch(1);
        Callable<Integer> lend = () -> {
            start.await();
            return post(loanJson(copyId)).getResponse().getStatus();
        };

        List<Integer> statuses;
        try (ExecutorService pool = Executors.newFixedThreadPool(2)) {
            List<Future<Integer>> results = List.of(pool.submit(lend), pool.submit(lend));
            start.countDown();
            statuses = List.of(results.get(0).get(), results.get(1).get());
        }

        assertThat(statuses).containsExactlyInAnyOrder(201, 409);
        assertThat(copyStatus(copyId)).isEqualTo("PRESTADO");
        assertThat(jdbc.sql("""
                SELECT COUNT(*) FROM loan_items WHERE copy_id = ? AND status IN ('PRESTADO', 'RETRASADO')""")
                .param(copyId).query(Long.class).single()).isEqualTo(1);
    }

    private MvcTestResult post(String json) {
        return mvc.post().uri("/loans").contentType(MediaType.APPLICATION_JSON).content(json).exchange();
    }

    private static String loanJson(long... copyIds) {
        StringBuilder items = new StringBuilder();
        for (long copyId : copyIds) {
            items.append(items.isEmpty() ? "" : ",").append("""
                    {"copyId": %d, "dueDate": "%s"}""".formatted(copyId, LocalDate.now().plusDays(7)));
        }
        return """
                {"readerId": 1, "observation": null, "items": [%s]}""".formatted(items);
    }

    private String copyStatus(long copyId) {
        return jdbc.sql("SELECT status FROM copies WHERE id = ?").param(copyId).query(String.class).single();
    }
}
