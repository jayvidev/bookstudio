package com.bookstudio.circulation;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the loan module.
 */
public interface LoanApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if the copy is not part of the loan
     */
    void requireItemExists(Long loanId, Long copyId);

    List<OptionResponse> getOptions();
}
