package com.bookstudio.payment.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookstudio.payment.domain.model.type.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "fineCount", "reader", "amount", "paymentDate", "method" })
public record PaymentListResponse(
    Long id,
    String code,
    Long fineCount,

    @JsonIgnore Long readerId,
    @JsonIgnore String readerCode,
    @JsonIgnore String readerFullName,

    BigDecimal amount,
    LocalDate paymentDate,
    PaymentMethod method
) {

    @JsonGetter("reader")
    public Reader getReader() {
        return new Reader(readerId, readerCode, readerFullName);
    }

    public record Reader(
        Long id,
        String code,
        String fullName
    ) {}
}
