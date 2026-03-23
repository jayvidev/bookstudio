package com.bookstudio.fine.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.bookstudio.fine.domain.model.type.FineStatus;
import com.bookstudio.loan.domain.model.type.LoanItemStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "loanItem", "amount", "daysLate", "status", "issuedAt" })
public record FineDetailResponse(
    Long id,
    String code,

    @JsonIgnore Long loanItemCopyId,
    @JsonIgnore String loanItemCopyCode,
    @JsonIgnore String loanItemCopyBarcode,
    @JsonIgnore CopyStatus loanItemCopyStatus,

    @JsonIgnore LocalDate loanItemDueDate,
    @JsonIgnore LocalDate loanItemReturnDate,
    @JsonIgnore LoanItemStatus loanItemStatus,

    BigDecimal amount,
    Integer daysLate,
    FineStatus status,
    LocalDate issuedAt
) {

    @JsonGetter("loanItem")
    public LoanItem getLoanItem() {
        Copy copy = new Copy(loanItemCopyId, loanItemCopyCode, loanItemCopyBarcode, loanItemCopyStatus);
        return new LoanItem(copy, loanItemDueDate, loanItemReturnDate, loanItemStatus);
    }

    public record LoanItem(
        Copy copy,
        LocalDate dueDate,
        LocalDate returnDate,
        LoanItemStatus status
    ) {}

    public record Copy(
        Long id,
        String code,
        String barcode,
        CopyStatus status
    ) {}
}
