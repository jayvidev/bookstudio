package com.bookstudio.payment.infrastructure.repository;

import com.bookstudio.payment.application.dto.response.PaymentDetailResponse;
import com.bookstudio.payment.application.dto.response.PaymentListResponse;
import com.bookstudio.payment.domain.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("""
        SELECT 
            p.id AS id,
            p.code AS code,
            COUNT(f) AS fineCount,

            r.id AS readerId,
            r.code AS readerCode,
            CONCAT(r.firstName, ' ', r.lastName) AS readerFullName,

            p.amount AS amount,
            p.paymentDate AS paymentDate,
            p.method AS method
        FROM Payment p
        JOIN p.reader r
        JOIN PaymentFine pf ON pf.payment = p
        JOIN pf.fine f
        GROUP BY p.id, p.code, r.id, r.code, r.firstName, r.lastName, p.amount, p.paymentDate, p.method
        ORDER BY p.id DESC
    """)
    List<PaymentListResponse> findList();


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
        JOIN p.reader r
        WHERE p.id = :id
    """)
    Optional<PaymentDetailResponse> findDetailById(Long id);
}
