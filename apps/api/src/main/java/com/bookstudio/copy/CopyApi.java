package com.bookstudio.copy;

import com.bookstudio.shared.response.OptionResponse;

import java.util.Collection;
import java.util.List;

/**
 * Public API of the copy module.
 */
public interface CopyApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();

    /**
     * Marks available copies as on loan.
     *
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if a copy does not exist
     * @throws com.bookstudio.shared.exception.BusinessRuleException if a copy is not available
     */
    void lend(Collection<Long> copyIds);

    /**
     * Makes copies available again (returned, or removed from a loan).
     */
    void release(Collection<Long> copyIds);

    void markLost(Collection<Long> copyIds);
}
