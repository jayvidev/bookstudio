package com.bookstudio.loan.domain.model;

import java.time.LocalDate;

import com.bookstudio.loan.LoanItemStatus;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * One copy within a loan. Created and changed only through {@link Loan}.
 */
@Entity
@Table(name = "loan_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoanItem {
    @EmbeddedId
    private LoanItemId id;

    @ManyToOne
    @MapsId("loanId")
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private LoanItemStatus status;

    LoanItem(Loan loan, Long copyId, LocalDate dueDate) {
        this.id = new LoanItemId(null, copyId);
        this.loan = loan;
        this.dueDate = dueDate;
        this.status = LoanItemStatus.PRESTADO;
    }

    public Long getCopyId() {
        return id.getCopyId();
    }

    /**
     * Whether the reader currently has the copy.
     */
    public boolean holdsCopy() {
        return holdsCopy(status);
    }

    void reschedule(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Moves the item to {@code newStatus}, recording the return date when it is
     * returned, and tells the caller what that means for the copy.
     */
    CopyEffect changeStatus(LoanItemStatus newStatus, LocalDate today) {
        LoanItemStatus previous = status;
        if (newStatus == previous) {
            return CopyEffect.NONE;
        }

        status = newStatus;
        returnDate = newStatus == LoanItemStatus.DEVUELTO ? today : null;

        if (newStatus == LoanItemStatus.EXTRAVIADO) {
            return CopyEffect.MARK_LOST;
        }
        if (holdsCopy(newStatus) && !holdsCopy(previous)) {
            return CopyEffect.LEND;
        }
        if (!holdsCopy(newStatus) && (holdsCopy(previous) || previous == LoanItemStatus.EXTRAVIADO)) {
            return CopyEffect.RELEASE;
        }
        return CopyEffect.NONE;
    }

    private static boolean holdsCopy(LoanItemStatus status) {
        return status == LoanItemStatus.PRESTADO || status == LoanItemStatus.RETRASADO;
    }
}
