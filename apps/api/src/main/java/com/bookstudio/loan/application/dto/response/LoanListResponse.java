package com.bookstudio.loan.application.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "reader", "loanDate", "itemCount", "statusCounts" })
public record LoanListResponse(
    Long id,
    String code,

    @JsonIgnore Long readerId,
    @JsonIgnore String readerCode,
    @JsonIgnore String readerFullName,

    LocalDate loanDate,
    Long itemCount,

    @JsonIgnore Long borrowedCount,
    @JsonIgnore Long returnedCount,
    @JsonIgnore Long overdueCount,
    @JsonIgnore Long lostCount,
    @JsonIgnore Long canceledCount
) {

    @JsonGetter("reader")
    public Reader getReader() {
        return new Reader(readerId, readerCode, readerFullName);
    }

    @JsonGetter("statusCounts")
    public StatusCounts getStatusCounts() {
        return new StatusCounts(borrowedCount, returnedCount, overdueCount, lostCount, canceledCount);
    }

    public record Reader(
        Long id,
        String code,
        String fullName
    ) {}

    public record StatusCounts(
        Long borrowed,
        Long returned,
        Long overdue,
        Long lost,
        Long canceled
    ) {}
}
