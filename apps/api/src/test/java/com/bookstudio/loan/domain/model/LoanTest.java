package com.bookstudio.loan.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.bookstudio.loan.LoanItemStatus;
import com.bookstudio.shared.exception.BusinessRuleException;

class LoanTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 10, 2);
    private static final LocalDate DUE = TODAY.plusDays(14);

    private final Loan loan = Loan.open("PRE-2026-00001", 1L, TODAY, null);

    @Test
    void newItemsAreOnLoanAndLendTheCopy() {
        assertThat(loan.addItem(10L, DUE)).isEqualTo(CopyEffect.LEND);

        LoanItem item = loan.findItem(10L).orElseThrow();
        assertThat(item.getStatus()).isEqualTo(LoanItemStatus.PRESTADO);
        assertThat(item.getDueDate()).isEqualTo(DUE);
        assertThat(item.getReturnDate()).isNull();
    }

    @Test
    void rejectsTheSameCopyTwice() {
        loan.addItem(10L, DUE);

        assertThatThrownBy(() -> loan.addItem(10L, DUE))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Copy 10 is already part of loan PRE-2026-00001");
    }

    @Test
    void returningRecordsTheReturnDateAndReleasesTheCopy() {
        loan.addItem(10L, DUE);

        assertThat(loan.changeItem(10L, DUE, LoanItemStatus.DEVUELTO, TODAY)).isEqualTo(CopyEffect.RELEASE);
        assertThat(loan.findItem(10L).orElseThrow().getReturnDate()).isEqualTo(TODAY);
    }

    @Test
    void reschedulingKeepsStatusAndCopy() {
        loan.addItem(10L, DUE);

        assertThat(loan.changeItem(10L, DUE.plusDays(7), LoanItemStatus.PRESTADO, TODAY)).isEqualTo(CopyEffect.NONE);
        assertThat(loan.findItem(10L).orElseThrow().getDueDate()).isEqualTo(DUE.plusDays(7));
    }

    @ParameterizedTest(name = "{0} -> {1}: {2}")
    @CsvSource({
            "PRESTADO,   RETRASADO,  NONE",
            "PRESTADO,   DEVUELTO,   RELEASE",
            "PRESTADO,   CANCELADO,  RELEASE",
            "PRESTADO,   EXTRAVIADO, MARK_LOST",
            "RETRASADO,  DEVUELTO,   RELEASE",
            "DEVUELTO,   PRESTADO,   LEND",
            "DEVUELTO,   EXTRAVIADO, MARK_LOST",
            "EXTRAVIADO, DEVUELTO,   RELEASE",
            "EXTRAVIADO, PRESTADO,   LEND",
            "CANCELADO,  DEVUELTO,   NONE",
    })
    void statusChangesTranslateToCopyEffects(LoanItemStatus from, LoanItemStatus to, CopyEffect expected) {
        loan.addItem(10L, DUE);
        loan.changeItem(10L, DUE, from, TODAY);

        assertThat(loan.changeItem(10L, DUE, to, TODAY)).isEqualTo(expected);
    }

    @Test
    void movingAwayFromReturnedClearsTheReturnDate() {
        loan.addItem(10L, DUE);
        loan.changeItem(10L, DUE, LoanItemStatus.DEVUELTO, TODAY);

        loan.changeItem(10L, DUE, LoanItemStatus.PRESTADO, TODAY);

        assertThat(loan.findItem(10L).orElseThrow().getReturnDate()).isNull();
    }

    @Test
    void removingAnItemTheReaderHoldsReleasesTheCopy() {
        loan.addItem(10L, DUE);

        assertThat(loan.removeItem(10L)).isEqualTo(CopyEffect.RELEASE);
        assertThat(loan.getLoanItems()).isEmpty();
    }

    @Test
    void removingAReturnedItemLeavesTheCopyAlone() {
        loan.addItem(10L, DUE);
        loan.changeItem(10L, DUE, LoanItemStatus.DEVUELTO, TODAY);

        assertThat(loan.removeItem(10L)).isEqualTo(CopyEffect.NONE);
    }

    @Test
    void itemsCannotBeModifiedFromOutside() {
        loan.addItem(10L, DUE);

        assertThatThrownBy(() -> loan.getLoanItems().clear()).isInstanceOf(UnsupportedOperationException.class);
    }
}
