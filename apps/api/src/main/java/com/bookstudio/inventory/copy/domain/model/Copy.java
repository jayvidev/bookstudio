package com.bookstudio.inventory.copy.domain.model;

import com.bookstudio.inventory.CopyStatus;
import com.bookstudio.inventory.copy.domain.model.type.CopyCondition;
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
     * Status change requested by staff (maintenance, lost, back on the shelf).
     * Lending and returning only happen through loans, so a copy on loan cannot
     * be changed here and no copy can be put on loan here.
     *
     * @throws BusinessRuleException if the change would bypass a loan
     */
    public void changeStatusManually(CopyStatus newStatus) {
        if (newStatus == status) {
            return;
        }
        if (status == CopyStatus.PRESTADO) {
            throw new BusinessRuleException(
                    "Copy %s is on loan; change it through its loan".formatted(code));
        }
        if (newStatus == CopyStatus.PRESTADO) {
            throw new BusinessRuleException("Copies are put on loan by creating a loan, not by editing the copy");
        }
        status = newStatus;
    }

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
