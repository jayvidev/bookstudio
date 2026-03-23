package com.bookstudio.fine.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.fine.application.dto.response.FineDetailResponse;
import com.bookstudio.fine.application.dto.response.FineListResponse;
import com.bookstudio.fine.domain.model.Fine;
import com.bookstudio.shared.response.OptionResponse;

public interface FineRepository extends JpaRepository<Fine, Long> {
    @Query("""
        SELECT 
            f.id AS id,
            f.code AS code,

            l.id AS loanId,
            l.code AS loanCode,

            c.id AS copyId,
            c.code AS copyCode,

            f.amount AS amount,
            f.daysLate AS daysLate,
            f.issuedAt AS issuedAt,
            f.status AS status
        FROM Fine f
        JOIN f.loanItem li
        JOIN li.loan l
        JOIN li.copy c
        ORDER BY f.id DESC
    """)
    List<FineListResponse> findList();

    @Query("""
        SELECT
            f.id AS value,
            f.code AS label
        FROM Fine f
        WHERE f.status = 'PENDIENTE'
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            f.id AS id,
            f.code AS code,

            c.id AS loanItemCopyId,
            c.code AS loanItemCopyCode,
            c.barcode AS loanItemCopyBarcode,
            c.status AS loanItemCopyStatus,

            li.dueDate AS loanItemDueDate,
            li.returnDate AS loanItemReturnDate,
            li.status AS loanItemStatus,

            f.amount AS amount,
            f.daysLate AS daysLate,
            f.status AS status,
            f.issuedAt AS issuedAt
        FROM Fine f
        JOIN f.loanItem li
        JOIN li.copy c
        WHERE f.id = :id
    """)
    Optional<FineDetailResponse> findDetailById(Long id);
}
