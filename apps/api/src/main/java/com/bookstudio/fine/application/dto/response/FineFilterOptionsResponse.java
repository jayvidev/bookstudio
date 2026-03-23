package com.bookstudio.fine.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record FineFilterOptionsResponse(
    List<OptionResponse> loans,
    List<OptionResponse> copies
) {}
