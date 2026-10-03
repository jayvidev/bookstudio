package com.bookstudio.catalog.category.application.dto.response;

import com.bookstudio.catalog.category.domain.model.type.CategoryLevel;
import com.bookstudio.shared.type.Status;

public record CategoryDetailResponse(
    Long id,
    String name,
    CategoryLevel level,
    String description,
    Status status
) {}
