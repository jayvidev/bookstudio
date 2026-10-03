package com.bookstudio.staff.role;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

/**
 * Public API of the role module.
 */
public interface RoleApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException if it does not exist
     */
    void requireExists(Long id);

    List<OptionResponse> getOptions();
}
