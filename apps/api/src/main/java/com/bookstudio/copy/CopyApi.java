package com.bookstudio.copy;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the copy module.
 */
public interface CopyApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
