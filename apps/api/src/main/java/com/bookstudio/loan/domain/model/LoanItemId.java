package com.bookstudio.loan.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanItemId implements Serializable {
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "copy_id")
    private Long copyId;
}
