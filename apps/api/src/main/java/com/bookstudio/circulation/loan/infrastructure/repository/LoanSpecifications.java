package com.bookstudio.circulation.loan.infrastructure.repository;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.bookstudio.circulation.LoanItemStatus;
import com.bookstudio.circulation.loan.domain.model.Loan;
import com.bookstudio.circulation.loan.domain.model.LoanItem;
import com.bookstudio.shared.paging.Specs;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

/**
 * Loan-specific filters; generic ones live in {@code shared.paging.Specs}.
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

    /**
     * Code contains {@code text}, or the loan belongs to one of {@code readerIds}
     * (the readers whose name matched, resolved by the reader module).
     */
    public static Specification<Loan> codeContainsOrReaderIn(String text, Collection<Long> readerIds) {
        Specification<Loan> codeMatches = Specs.containsIgnoreCase(text, "code");
        if (readerIds.isEmpty()) {
            return codeMatches;
        }
        return Specification.anyOf(codeMatches, (root, query, cb) -> root.get("readerId").in(readerIds));
    }
}
