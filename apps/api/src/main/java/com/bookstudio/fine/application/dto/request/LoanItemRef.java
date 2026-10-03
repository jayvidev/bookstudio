package com.bookstudio.fine.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Identifies the loan item a fine is issued for: one copy within one loan.
 */
public record LoanItemRef(
    @NotNull(message = "Loan ID is required")
    Long loanId,

    @NotNull(message = "Copy ID is required")
    Long copyId
) {}
