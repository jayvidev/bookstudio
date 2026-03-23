package com.bookstudio.loan.application.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.bookstudio.loan.domain.model.type.LoanItemStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "reader", "loanDate", "observation", "items" })
public record LoanDetailResponse(
    Long id,
    String code,

    @JsonIgnore Long readerId,
    @JsonIgnore String readerCode,
    @JsonIgnore String readerFullName,

    LocalDate loanDate,
    String observation,

    List<LoanItem> items
) {

    public LoanDetailResponse withItems(List<LoanItem> items) {
        return new LoanDetailResponse(
                id, code, readerId, readerCode, readerFullName, loanDate, observation, items);
    }

    @JsonGetter("reader")
    public Reader getReader() {
        return new Reader(readerId, readerCode, readerFullName);
    }

    public record Reader(
        Long id,
        String code,
        String fullName
    ) {}

    public record LoanItem(
        @JsonIgnore Long copyId,
        @JsonIgnore String copyCode,
        @JsonIgnore String copyBarcode,
        @JsonIgnore CopyStatus copyStatus,

        LocalDate dueDate,
        LocalDate returnDate,
        LoanItemStatus status
    ) {

        @JsonGetter("copy")
        public Copy getCopy() {
            return new Copy(copyId, copyCode, copyBarcode, copyStatus);
        }

        public record Copy(
            Long id,
            String code,
            String barcode,
            CopyStatus status
        ) {}
    }
}
