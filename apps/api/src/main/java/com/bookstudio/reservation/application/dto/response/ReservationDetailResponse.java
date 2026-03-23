package com.bookstudio.reservation.application.dto.response;

import java.time.LocalDate;

import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.bookstudio.reservation.domain.model.type.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "reader", "copy", "reservationDate", "status" })
public record ReservationDetailResponse(
    Long id,
    String code,

    @JsonIgnore Long readerId,
    @JsonIgnore String readerCode,
    @JsonIgnore String readerFullName,

    @JsonIgnore Long copyId,
    @JsonIgnore String copyCode,
    @JsonIgnore String copyBarcode,
    @JsonIgnore CopyStatus copyStatus,

    LocalDate reservationDate,
    ReservationStatus status
) {

    @JsonGetter("reader")
    public Reader getReader() {
        return new Reader(readerId, readerCode, readerFullName);
    }

    @JsonGetter("copy")
    public Copy getCopy() {
        return new Copy(copyId, copyCode, copyBarcode, copyStatus);
    }

    public record Reader(
        Long id,
        String code,
        String fullName
    ) {}

    public record Copy(
        Long id,
        String code,
        String barcode,
        CopyStatus status
    ) {}
}
