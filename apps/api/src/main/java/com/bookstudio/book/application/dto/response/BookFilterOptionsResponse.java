package com.bookstudio.book.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record BookFilterOptionsResponse(
    List<OptionResponse> categories,
    List<OptionResponse> publishers,
    List<OptionResponse> languages
) {}
