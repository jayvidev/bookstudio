package com.bookstudio.membership;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the reader module.
 */
public interface ReaderApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();

    /**
     * Ids of readers whose full name contains {@code text}, ignoring case.
     */
    List<Long> findIdsByName(String text);
}
