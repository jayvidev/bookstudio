package com.bookstudio.shared.paging;

import com.bookstudio.shared.api.PageResponse;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Second step of a paged list: the specification query pages entities (and
 * Spring derives the count), then one projection query loads the response rows
 * of that page by id, keeping the page order. This keeps list projections with
 * joins and aggregates out of the Criteria API.
 */
public final class PageProjection {

    private PageProjection() {
    }

    public static <E, R> PageResponse<R> of(
            Page<E> page,
            Function<E, Long> entityId,
            Function<List<Long>, List<R>> loadRowsByIds,
            Function<R, Long> rowId) {
        return PageResponse.from(page, entities -> {
            if (entities.isEmpty()) {
                return List.of();
            }
            Map<Long, R> rows = loadRowsByIds.apply(entities.stream().map(entityId).toList()).stream()
                    .collect(Collectors.toMap(rowId, Function.identity()));
            return entities.stream().map(entity -> rows.get(entityId.apply(entity))).toList();
        });
    }
}
