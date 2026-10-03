package com.bookstudio.inventory.location;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the location module.
 */
public interface LocationApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if the shelf does not exist
     */
    void requireShelfExists(Long shelfId);

    List<OptionResponse> getShelfOptions();
}
