package com.bookstudio.loan.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record LoanSelectOptionsResponse(
    List<OptionResponse> books,
    List<OptionResponse> readers
) {}
