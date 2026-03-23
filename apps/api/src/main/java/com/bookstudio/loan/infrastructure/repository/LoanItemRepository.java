package com.bookstudio.loan.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.loan.application.dto.response.LoanDetailResponse;
import com.bookstudio.loan.domain.model.Loan;
import com.bookstudio.loan.domain.model.LoanItem;
import com.bookstudio.loan.domain.model.LoanItemId;

public interface LoanItemRepository extends JpaRepository<LoanItem, LoanItemId> {
    @Query("""
        SELECT 
            c.id AS copyId,
            c.code AS copyCode,
            c.barcode AS copyBarcode,
            c.status AS copyStatus,

            li.dueDate AS dueDate,
            li.returnDate AS returnDate,
            li.status AS status
        FROM LoanItem li
        JOIN li.copy c
        WHERE li.loan.id = :id
    """)
    List<LoanDetailResponse.LoanItem> findLoanItemsByLoanId(Long id);

    void deleteAllByLoan(Loan loan);
}
