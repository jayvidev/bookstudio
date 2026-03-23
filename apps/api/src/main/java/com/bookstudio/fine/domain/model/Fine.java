package com.bookstudio.fine.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookstudio.fine.domain.model.type.FineStatus;
import com.bookstudio.loan.domain.model.LoanItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fines")
@Data
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private String code;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "loan_id", referencedColumnName = "loan_id"),
            @JoinColumn(name = "copy_id", referencedColumnName = "copy_id")
    })
    private LoanItem loanItem;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer daysLate;

    @Enumerated(EnumType.STRING)
    private FineStatus status;

    private LocalDate issuedAt;
}
