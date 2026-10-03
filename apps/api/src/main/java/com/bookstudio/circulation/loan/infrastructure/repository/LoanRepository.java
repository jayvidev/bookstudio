package com.bookstudio.circulation.loan.infrastructure.repository;

import com.bookstudio.circulation.loan.application.dto.response.LoanDetailResponse;
import com.bookstudio.circulation.loan.application.dto.response.LoanListResponse;
import com.bookstudio.circulation.loan.domain.model.Loan;
import com.bookstudio.shared.response.OptionResponse;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    String LIST_SELECT = """
        SELECT 
            l.id AS id,
            l.code AS code,

            r.id AS readerId,
            r.code AS readerCode,
            CONCAT(r.firstName, ' ', r.lastName) AS readerFullName,

            l.loanDate AS loanDate,
            COALESCE(COUNT(li.id), 0) AS itemCount,

            COALESCE(SUM(CASE WHEN li.status = 'PRESTADO' THEN 1 ELSE 0 END), 0) AS borrowedCount,
            COALESCE(SUM(CASE WHEN li.status = 'DEVUELTO' THEN 1 ELSE 0 END), 0) AS returnedCount,
            COALESCE(SUM(CASE WHEN li.status = 'RETRASADO' THEN 1 ELSE 0 END), 0) AS overdueCount,
            COALESCE(SUM(CASE WHEN li.status = 'EXTRAVIADO' THEN 1 ELSE 0 END), 0) AS lostCount,
            COALESCE(SUM(CASE WHEN li.status = 'CANCELADO' THEN 1 ELSE 0 END), 0) AS canceledCount
        FROM Loan l
        JOIN Reader r ON r.id = l.readerId
        LEFT JOIN l.loanItems li
        """;

    String LIST_GROUP_BY = """
        GROUP BY l.id, l.code, r.id, r.code, r.firstName, r.lastName, l.loanDate
        """;

    @Query(LIST_SELECT + "WHERE l.id = :id " + LIST_GROUP_BY)
    Optional<LoanListResponse> findListItemById(Long id);

    @Query(LIST_SELECT + "WHERE l.id IN :ids " + LIST_GROUP_BY)
    List<LoanListResponse> findListByIds(Collection<Long> ids);

    @Query("""
        SELECT
            l.id AS value,
            l.code AS label
        FROM Loan l
        ORDER BY l.code DESC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            l.id AS id,
            l.code AS code,

            r.id AS readerId,
            r.code AS readerCode,
            CONCAT(r.firstName, ' ', r.lastName) AS readerFullName,

            l.loanDate AS loanDate,
            l.observation AS observation,
            
            NULL AS items
        FROM Loan l
        JOIN Reader r ON r.id = l.readerId
        WHERE l.id = :id
    """)
    Optional<LoanDetailResponse> findDetailById(Long id);
}
