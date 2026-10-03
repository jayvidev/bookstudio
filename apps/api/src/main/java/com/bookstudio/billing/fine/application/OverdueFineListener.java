package com.bookstudio.billing.fine.application;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.bookstudio.circulation.LoanItemReturned;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Billing's reaction to circulation: a copy returned after its due date gets
 * an overdue fine.
 *
 * <p>{@code @ApplicationModuleListener} runs after the loan transaction commits,
 * asynchronously and in its own transaction: a problem here never undoes the
 * return. The publication is stored in the event registry, so if this fails it
 * is retried instead of lost.
 */
@Component
@RequiredArgsConstructor
@Slf4j
class OverdueFineListener {
    private final FineService fineService;

    @ApplicationModuleListener
    void on(LoanItemReturned event) {
        if (event.daysLate() == 0) {
            return;
        }
        fineService.issueOverdueFine(event.loanId(), event.copyId(), event.daysLate(), event.returnDate())
                .ifPresent(code -> log.info("Issued overdue fine {} for loan {} copy {} ({} days late)",
                        code, event.loanCode(), event.copyId(), event.daysLate()));
    }
}
