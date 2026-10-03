package com.bookstudio.loan.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bookstudio.reader.domain.model.Reader;
import com.bookstudio.shared.code.CodePeriod;
import com.bookstudio.shared.code.CodeSeries;

@Entity
@Table(name = "loans")
@Data
public class Loan {
    public static final CodeSeries CODE_SERIES = new CodeSeries("PRE", CodePeriod.DAY, 5);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoanItem> loanItems = new ArrayList<>();
}
