package com.bookstudio.shared.api;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Stable JSON contract for a page of results. Spring's {@link Page} is not
 * serialized directly: its JSON shape is an implementation detail.
 */
@Schema(description = "A page of results")
public record PageResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

    /**
     * Keeps the page metadata of {@code page} but replaces its content.
     */
    public static <S, T> PageResponse<T> from(Page<S> page, Function<List<S>, List<T>> content) {
        return new PageResponse<>(
                content.apply(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
