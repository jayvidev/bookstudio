package com.bookstudio.author.application.dto.response;

import java.time.LocalDate;

import com.bookstudio.shared.type.Status;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "photoUrl", "name", "nationality", "birthDate", "status" })
public record AuthorListResponse(
    Long id,
    String photoUrl,
    String name,

    @JsonIgnore Long nationalityId,
    @JsonIgnore String nationalityCode,
    @JsonIgnore String nationalityName,

    LocalDate birthDate,
    Status status
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
