package com.bookstudio.author.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record AuthorFilterOptionsResponse(
    List<OptionResponse> nationalities
) {}
