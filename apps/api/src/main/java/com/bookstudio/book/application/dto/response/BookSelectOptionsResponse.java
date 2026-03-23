package com.bookstudio.book.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record BookSelectOptionsResponse(
    List<OptionResponse> languages,
    List<OptionResponse> publishers,
    List<OptionResponse> categories
) {}
