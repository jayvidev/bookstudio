package com.bookstudio.reader.application.dto.response;

import java.time.LocalDate;

import com.bookstudio.reader.domain.model.type.ReaderGender;
import com.bookstudio.reader.domain.model.type.ReaderStatus;
import com.bookstudio.reader.domain.model.type.ReaderType;

public record ReaderDetailResponse(
    Long id,
    String code,
    String dni,
    String firstName,
    String lastName,
    String address,
    String phone,
    String email,
    LocalDate birthDate,
    ReaderGender gender,
    ReaderType type,
    ReaderStatus status
) {}
