package com.bookstudio.genre;

import java.util.Collection;

/**
 * Public API of the genre module.
 */
public interface GenreApi {

    /**
     * @throws com.bookstudio.shared.exception.ResourceNotFoundException naming the first missing id
     */
    void requireAllExist(Collection<Long> ids);
}
