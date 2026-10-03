package com.bookstudio.payment.infrastructure.repository;

import com.bookstudio.payment.application.dto.response.PaymentDetailResponse;
import com.bookstudio.payment.application.dto.response.PaymentListResponse;
import com.bookstudio.payment.domain.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    String LIST_SELECT = """
        SELECT
            p.id AS id,
            p.code AS code,
            COUNT(fineId) AS fineCount,

            r.id AS readerId,
            r.code AS readerCode,
            CONCAT(r.firstName, ' ', r.lastName) AS readerFullName,

            p.amount AS amount,
            p.paymentDate AS paymentDate,
            p.method AS method
        FROM Payment p
        JOIN Reader r ON r.id = p.readerId
        JOIN p.fineIds fineId
        """;

    String LIST_GROUP_BY = """
        GROUP BY p.id, p.code, r.id, r.code, r.firstName, r.lastName, p.amount, p.paymentDate, p.method
        """;

    @Query(LIST_SELECT + LIST_GROUP_BY + "ORDER BY p.id DESC")
    List<PaymentListResponse> findList();

    @Query(LIST_SELECT + "WHERE p.id = :id " + LIST_GROUP_BY)
    Optional<PaymentListResponse> findListItemById(Long id);

    @Query("""
        SELECT 
            p.id AS id,
            p.code AS code,

            r.id AS readerId,
            r.code AS readerCode,
            CONCAT(r.firstName, ' ', r.lastName) AS readerFullName,

            p.amount AS amount,
            p.paymentDate AS paymentDate,
            p.method AS method,
            
            NULL AS fines
        FROM Payment p
        JOIN Reader r ON r.id = p.readerId
        WHERE p.id = :id
    """)
    Optional<PaymentDetailResponse> findDetailById(Long id);

    @Query("""
        SELECT
            f.id AS id,
            f.code AS code,
            f.amount AS amount,
            f.status AS status
        FROM Payment p
        JOIN p.fineIds fineId
        JOIN Fine f ON f.id = fineId
        WHERE p.id = :id
        ORDER BY f.id
    """)
    List<PaymentDetailResponse.FineItem> findFineItemsByPaymentId(Long id);
}
