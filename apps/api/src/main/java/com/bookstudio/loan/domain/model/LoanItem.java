package com.bookstudio.loan.domain.model;

import java.time.LocalDate;

import com.bookstudio.copy.domain.model.Copy;
import com.bookstudio.loan.domain.model.type.LoanItemStatus;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanItem {
    @EmbeddedId
    private LoanItemId id;

    @ManyToOne
    @MapsId("loanId")
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @MapsId("copyId")
    @JoinColumn(name = "copy_id")
    private Copy copy;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private LoanItemStatus status;
}
