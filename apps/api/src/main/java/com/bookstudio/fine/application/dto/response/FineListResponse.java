package com.bookstudio.fine.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookstudio.fine.domain.model.type.FineStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "loan", "copy", "amount", "daysLate", "issuedAt", "status" })
public record FineListResponse(
    Long id,
    String code,

    @JsonIgnore Long loanId,
    @JsonIgnore String loanCode,

    @JsonIgnore Long copyId,
    @JsonIgnore String copyCode,

    BigDecimal amount,
    Integer daysLate,
    LocalDate issuedAt,
    FineStatus status
) {

    @JsonGetter("loan")
    public Loan getLoan() {
        return new Loan(loanId, loanCode);
    }

    @JsonGetter("copy")
    public Copy getCopy() {
        return new Copy(copyId, copyCode);
    }

    public record Loan(
        Long id,
        String code
    ) {}

    public record Copy(
        Long id,
        String code
    ) {}
}
