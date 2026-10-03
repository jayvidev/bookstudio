package com.bookstudio.inventory.copy.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record CopySelectOptionsResponse(
    List<OptionResponse> books,
    List<OptionResponse> shelves
) {}
