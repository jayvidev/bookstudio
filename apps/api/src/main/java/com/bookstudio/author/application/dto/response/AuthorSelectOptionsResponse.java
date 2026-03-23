package com.bookstudio.author.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record AuthorSelectOptionsResponse(
    List<OptionResponse> nationalities
) {}
