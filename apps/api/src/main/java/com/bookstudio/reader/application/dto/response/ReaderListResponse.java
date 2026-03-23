package com.bookstudio.reader.application.dto.response;

import com.bookstudio.reader.domain.model.type.ReaderStatus;
import com.bookstudio.reader.domain.model.type.ReaderType;

public record ReaderListResponse(
    Long id,
    String code,
    String fullName,
    String phone,
    String email,
    ReaderType type,
    ReaderStatus status
) {}