package com.bookstudio.category;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the category module.
 */
public interface CategoryApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
