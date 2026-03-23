package com.bookstudio.category.application.dto.response;

import com.bookstudio.category.domain.model.type.CategoryLevel;
import com.bookstudio.shared.type.Status;

public record CategoryListResponse(
    Long id,
    String name,
    CategoryLevel level,
    String description,
    Status status
) {}
