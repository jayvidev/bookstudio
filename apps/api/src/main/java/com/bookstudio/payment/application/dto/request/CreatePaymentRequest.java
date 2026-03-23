package com.bookstudio.payment.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.bookstudio.payment.domain.model.type.PaymentMethod;
import com.bookstudio.shared.validation.NoNullElements;
import com.bookstudio.shared.validation.ValidEnum;
import com.bookstudio.shared.validation.ValidIds;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record CreatePaymentRequest(
    @NotNull(message = "Reader ID is required")
    @Min(value = 1, message = "Reader ID must be at least 1")
    Long readerId,

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0.00")
    BigDecimal amount,

    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date must be in the past or present")
    LocalDate paymentDate,

    @NotBlank(message = "Method is required")
    @ValidEnum(enumClass = PaymentMethod.class, message = "Invalid method")
    String method,

    @NotEmpty(message = "Fine IDs cannot be empty")
    @NoNullElements(message = "Fine IDs cannot contain null values")
    @ValidIds(message = "Fine IDs must be greater than or equal to 1")
    List<Long> fineIds
) {}
