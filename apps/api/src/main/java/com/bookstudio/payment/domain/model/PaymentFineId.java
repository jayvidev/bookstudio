package com.bookstudio.payment.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFineId implements Serializable {
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "fine_id")
    private Long fineId;
}
