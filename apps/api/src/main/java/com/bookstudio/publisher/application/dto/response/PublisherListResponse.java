package com.bookstudio.publisher.application.dto.response;

import com.bookstudio.shared.type.Status;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "photoUrl", "name", "nationality", "website", "address", "status" })
public record PublisherListResponse(
    Long id,
    String photoUrl,
    String name,

    @JsonIgnore Long nationalityId,
    @JsonIgnore String nationalityCode,
    @JsonIgnore String nationalityName,

    String website,
    String address,
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
