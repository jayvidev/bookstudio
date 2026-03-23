package com.bookstudio.author.application.dto.response;

import java.time.LocalDate;

import com.bookstudio.shared.type.Status;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "nationality", "birthDate", "biography", "status", "photoUrl" })
public record AuthorDetailResponse(
    Long id,
    String name,

    @JsonIgnore Long nationalityId,
    @JsonIgnore String nationalityCode,
    @JsonIgnore String nationalityName,

    LocalDate birthDate,
    String biography,
    Status status,
    String photoUrl
) {

    @JsonGetter("nationality")
    public Nationality getNationality() {
        return new Nationality(nationalityId, nationalityCode, nationalityName);
    }

    public record Nationality(
        Long id,
        String code,
        String name
    ) {}
}
