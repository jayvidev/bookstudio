package com.bookstudio.billing.payment.domain.model;

import com.bookstudio.billing.payment.domain.model.type.PaymentMethod;
import com.bookstudio.shared.code.CodeSeries;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    public static final CodeSeries CODE_SERIES = new CodeSeries("PAG");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "reader_id", nullable = false)
    private Long readerId;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @ElementCollection
    @CollectionTable(name = "payment_fines", joinColumns = @JoinColumn(name = "payment_id"))
    @Column(name = "fine_id")
    private Set<Long> fineIds = new HashSet<>();

    public void replaceFines(Collection<Long> ids) {
        fineIds.clear();
        fineIds.addAll(ids);
    }
}
