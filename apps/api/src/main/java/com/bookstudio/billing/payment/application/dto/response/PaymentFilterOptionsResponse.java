package com.bookstudio.billing.payment.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record PaymentFilterOptionsResponse(
    List<OptionResponse> readers
) {}
