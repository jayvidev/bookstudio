package com.bookstudio.fine.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookstudio.fine.FineStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import com.bookstudio.shared.code.CodeSeries;

@Entity
@Table(name = "fines")
@Data
public class Fine {
    public static final CodeSeries CODE_SERIES = new CodeSeries("MUL");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "loan_id", nullable = false, updatable = false)
    private Long loanId;

    @Column(name = "copy_id", nullable = false, updatable = false)
    private Long copyId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer daysLate;

    @Enumerated(EnumType.STRING)
    private FineStatus status;

    private LocalDate issuedAt;
}
