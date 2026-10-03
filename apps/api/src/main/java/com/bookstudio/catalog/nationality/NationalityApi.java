package com.bookstudio.catalog.nationality;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the nationality module.
 */
public interface NationalityApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
