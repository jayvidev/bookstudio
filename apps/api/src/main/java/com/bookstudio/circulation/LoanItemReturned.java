package com.bookstudio.circulation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * A reader returned a copy of a loan. Published after the loan is saved;
 * other modules react to it (e.g. billing issues a fine when it is late).
 */
public record LoanItemReturned(
    Long loanId,
    String loanCode,
    Long copyId,
    Long readerId,
    LocalDate dueDate,
    LocalDate returnDate
) {

    public long daysLate() {
        return dueDate == null ? 0 : Math.max(0, ChronoUnit.DAYS.between(dueDate, returnDate));
    }
}
