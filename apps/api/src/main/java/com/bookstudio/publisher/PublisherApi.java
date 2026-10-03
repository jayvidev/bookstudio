package com.bookstudio.publisher;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the publisher module.
 */
public interface PublisherApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
