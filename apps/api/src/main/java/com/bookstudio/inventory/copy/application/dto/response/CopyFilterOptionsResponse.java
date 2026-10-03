package com.bookstudio.inventory.copy.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record CopyFilterOptionsResponse(
        List<OptionResponse> books) {
}
