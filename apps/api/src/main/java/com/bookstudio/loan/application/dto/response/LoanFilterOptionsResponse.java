package com.bookstudio.loan.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record LoanFilterOptionsResponse(
    List<OptionResponse> readers
) {}
