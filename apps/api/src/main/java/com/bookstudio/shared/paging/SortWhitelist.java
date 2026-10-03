package com.bookstudio.shared.paging;

import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bookstudio.shared.exception.BadRequestException;

/**
 * Restricts the {@code sort} request parameter to known properties, so clients
 * cannot sort by arbitrary (unindexed or internal) attributes.
 */
public record SortWhitelist(Set<String> allowed) {

    public static SortWhitelist of(String... properties) {
        return new SortWhitelist(Set.of(properties));
    }

    /**
     * @throws BadRequestException naming the first property that is not allowed
     */
    public Pageable validate(Pageable pageable) {
        for (Sort.Order order : pageable.getSort()) {
            if (!allowed.contains(order.getProperty())) {
                throw new BadRequestException(
                        "Cannot sort by '%s'. Allowed: %s".formatted(order.getProperty(), allowed.stream().sorted().toList()));
            }
        }
        return pageable;
    }
}
