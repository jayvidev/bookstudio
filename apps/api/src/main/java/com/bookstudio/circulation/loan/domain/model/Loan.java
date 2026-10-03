package com.bookstudio.circulation.loan.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bookstudio.circulation.LoanItemStatus;
import com.bookstudio.shared.code.CodeSeries;
import com.bookstudio.shared.exception.BusinessRuleException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root: a reader borrowing one or more copies. Items are only added,
 * changed or removed through this class, which reports the effect each change
 * has on the copy so the application layer can apply it in the copy module.
 */
@Entity
@Table(name = "loans")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Loan {
    public static final CodeSeries CODE_SERIES = new CodeSeries("PRE");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "reader_id", nullable = false)
    private Long readerId;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoanItem> loanItems = new ArrayList<>();

    public static Loan open(String code, Long readerId, LocalDate loanDate, String observation) {
        Loan loan = new Loan();
        loan.code = code;
        loan.readerId = readerId;
        loan.loanDate = loanDate;
        loan.observation = observation;
        return loan;
    }

    public void updateDetails(Long readerId, String observation) {
        this.readerId = readerId;
        this.observation = observation;
    }

    public List<LoanItem> getLoanItems() {
        return Collections.unmodifiableList(loanItems);
    }

    public Optional<LoanItem> findItem(Long copyId) {
        return loanItems.stream().filter(item -> item.getCopyId().equals(copyId)).findFirst();
    }

    /**
     * Adds a copy on loan. The caller must lend the copy ({@link CopyEffect#LEND}).
     *
     * @throws BusinessRuleException if the copy is already part of this loan
     */
    public CopyEffect addItem(Long copyId, LocalDate dueDate) {
        if (findItem(copyId).isPresent()) {
            throw new BusinessRuleException("Copy %d is already part of loan %s".formatted(copyId, code));
        }
        loanItems.add(new LoanItem(this, copyId, dueDate));
        return CopyEffect.LEND;
    }

    /**
     * Changes the due date and status of an existing item, keeping its history.
     */
    public CopyEffect changeItem(Long copyId, LocalDate dueDate, LoanItemStatus status, LocalDate today) {
        LoanItem item = findItem(copyId)
                .orElseThrow(() -> new BusinessRuleException("Copy %d is not part of loan %s".formatted(copyId, code)));
        item.reschedule(dueDate);
        return item.changeStatus(status, today);
    }

    /**
     * The reader brings the copy back.
     *
     * @throws BusinessRuleException if the copy is not currently on loan in this loan
     */
    public CopyEffect returnItem(Long copyId, LocalDate today) {
        LoanItem item = findItem(copyId)
                .orElseThrow(() -> new BusinessRuleException("Copy %d is not part of loan %s".formatted(copyId, code)));
        if (!item.holdsCopy()) {
            throw new BusinessRuleException("Copy %d of loan %s is not on loan (status: %s)"
                    .formatted(copyId, code, item.getStatus()));
        }
        return item.changeStatus(LoanItemStatus.DEVUELTO, today);
    }

    /**
     * Removes an item; if the reader still had the copy it goes back to the shelf.
     */
    public CopyEffect removeItem(Long copyId) {
        LoanItem item = findItem(copyId)
                .orElseThrow(() -> new BusinessRuleException("Copy %d is not part of loan %s".formatted(copyId, code)));
        loanItems.remove(item);
        return item.holdsCopy() ? CopyEffect.RELEASE : CopyEffect.NONE;
    }
}
