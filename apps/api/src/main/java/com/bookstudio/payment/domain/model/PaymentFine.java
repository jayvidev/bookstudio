package com.bookstudio.payment.domain.model;

import com.bookstudio.fine.domain.model.Fine;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_fines")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFine {
    @EmbeddedId
    private PaymentFineId id;

    @ManyToOne
    @MapsId("paymentId")
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @MapsId("fineId")
    @JoinColumn(name = "fine_id")
    private Fine fine;
}
