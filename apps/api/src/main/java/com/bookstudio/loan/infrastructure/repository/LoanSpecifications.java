package com.bookstudio.loan.infrastructure.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.bookstudio.loan.LoanItemStatus;
import com.bookstudio.loan.domain.model.Loan;
import com.bookstudio.loan.domain.model.LoanItem;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

/**
 * Composable filters for {@link Loan}. Each returns an unrestricted
 * specification when its argument is absent, so callers can combine them
 * without null checks and only present filters reach the SQL.
 */
public final class LoanSpecifications {

    private LoanSpecifications() {
    }

    public static Specification<Loan> hasItemWithStatus(LoanItemStatus status) {
        if (status == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> {
            Subquery<Long> items = query.subquery(Long.class);
            Root<LoanItem> item = items.from(LoanItem.class);
            items.select(cb.literal(1L))
                    .where(cb.equal(item.get("loan"), root), cb.equal(item.get("status"), status));
            return cb.exists(items);
        };
    }

    public static Specification<Loan> belongsToReader(Long readerId) {
        if (readerId == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("readerId"), readerId);
    }

    public static Specification<Loan> loanedOnOrAfter(LocalDate from) {
        if (from == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("loanDate"), from);
    }

    public static Specification<Loan> loanedOnOrBefore(LocalDate to) {
        if (to == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("loanDate"), to);
    }

    /**
     * Code contains {@code text}, or the loan belongs to one of {@code readerIds}
     * (the readers whose name matched, resolved by the reader module).
     */
    public static Specification<Loan> codeContainsOrReaderIn(String text, Collection<Long> readerIds) {
        return (root, query, cb) -> {
            var codeMatches = cb.like(cb.lower(root.get("code")), "%" + text.trim().toLowerCase() + "%");
            return readerIds.isEmpty() ? codeMatches : cb.or(codeMatches, root.get("readerId").in(readerIds));
        };
    }
}
