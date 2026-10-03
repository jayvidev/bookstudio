package com.bookstudio.language;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the language module.
 */
public interface LanguageApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
