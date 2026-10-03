package com.bookstudio.fine;

import java.util.Collection;

/**
 * Public API of the fine module.
 */
public interface FineApi {

    /**
     * Marks the fines as paid.
     *
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException naming the first missing id
     */
    void markPaid(Collection<Long> fineIds);
}
