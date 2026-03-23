package com.bookstudio.payment.infrastructure.repository;

import com.bookstudio.payment.application.dto.response.PaymentDetailResponse;
import com.bookstudio.payment.domain.model.Payment;
import com.bookstudio.payment.domain.model.PaymentFine;
import com.bookstudio.payment.domain.model.PaymentFineId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentFineRepository extends JpaRepository<PaymentFine, PaymentFineId> {
    @Query("""
        SELECT 
            f.id AS id,
            f.code AS code,
            f.amount AS amount,
            f.status AS status
        FROM PaymentFine pf
        JOIN pf.fine f
        WHERE pf.payment.id = :id
    """)
    List<PaymentDetailResponse.FineItem> findFinesItemsByPaymentId(Long id);

    Long countByPayment(Payment payment);
    void deleteAllByPayment(Payment payment);
}