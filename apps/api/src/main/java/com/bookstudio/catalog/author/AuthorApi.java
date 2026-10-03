package com.bookstudio.catalog.author;

import java.util.Collection;

/**
 * Public API of the author module.
 */
public interface AuthorApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException naming the first missing id
     */
    void requireAllExist(Collection<Long> ids);
}
