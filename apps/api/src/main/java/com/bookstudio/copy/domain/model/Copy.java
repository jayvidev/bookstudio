package com.bookstudio.copy.domain.model;

import com.bookstudio.copy.CopyStatus;
import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.shared.code.CodeSeries;
import com.bookstudio.shared.exception.BusinessRuleException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import lombok.Data;

@Entity
@Table(name = "copies")
@Data
public class Copy {
    public static final CodeSeries CODE_SERIES = new CodeSeries("EJE");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "book_id", nullable = false, updatable = false)
    private Long bookId;

    @Column(name = "shelf_id", nullable = false)
    private Long shelfId;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    private CopyStatus status;

    @Enumerated(EnumType.STRING)
    private CopyCondition condition;

    /**
     * @throws BusinessRuleException unless the copy is available
     */
    public void lend() {
        if (status != CopyStatus.DISPONIBLE) {
            throw new BusinessRuleException("Copy %s is not available (status: %s)".formatted(code, status));
        }
        status = CopyStatus.PRESTADO;
    }

    public void release() {
        status = CopyStatus.DISPONIBLE;
    }

    public void markLost() {
        status = CopyStatus.EXTRAVIADO;
    }
}
