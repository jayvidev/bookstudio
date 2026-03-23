package com.bookstudio.reservation.application.dto.response;

import java.time.LocalDate;

import com.bookstudio.reservation.domain.model.type.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "reader", "copy", "reservationDate", "status" })
public record ReservationListResponse(
    Long id,
    String code,

    @JsonIgnore Long readerId,
    @JsonIgnore String readerCode,
    @JsonIgnore String readerFullName,

    @JsonIgnore String copyCode,

    LocalDate reservationDate,
    ReservationStatus status
) {

    @JsonGetter("reader")
    public Reader getReader() {
        return new Reader(readerId, readerCode, readerFullName);
    }

    @JsonGetter("copy")
    public Copy getCopy() {
        return new Copy(copyCode);
    }

    public record Reader(
        Long id,
        String code,
        String fullName
    ) {}

    public record Copy(
        String code
    ) {}
}
