package com.bookstudio.catalog;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the book module.
 */
public interface BookApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
