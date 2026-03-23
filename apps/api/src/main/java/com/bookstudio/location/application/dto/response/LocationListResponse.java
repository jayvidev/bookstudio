package com.bookstudio.location.application.dto.response;

public record LocationListResponse(
    Long id,
    String name,
    String description,
    Long shelfCount,
    Long bookCount,
    Long copyCount
) {}
