package com.bookstudio.shared.paging;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Generic filters for list endpoints. Each one is {@link Specification#unrestricted()}
 * when its value is absent, so callers combine them with
 * {@link Specification#allOf} and only present filters reach the SQL.
 */
public final class Specs {
    private static final char ESCAPE = '\\';

    private Specs() {
    }

    public static <T> Specification<T> equal(String attribute, Object value) {
        if (value == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get(attribute), value);
    }

    public static <T> Specification<T> notEqual(String attribute, Object value) {
        if (value == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.notEqual(root.get(attribute), value);
    }

    public static <T> Specification<T> onOrAfter(String attribute, LocalDate from) {
        if (from == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), from);
    }

    public static <T> Specification<T> onOrBefore(String attribute, LocalDate to) {
        if (to == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), to);
    }

    /**
     * Any of {@code attributes} contains {@code text}, ignoring case. LIKE wildcards
     * typed by the user ({@code %}, {@code _}) are matched literally.
     */
    public static <T> Specification<T> containsIgnoreCase(String text, String... attributes) {
        if (text == null || text.isBlank()) {
            return Specification.unrestricted();
        }
        String pattern = "%" + escapeLike(text.trim().toLowerCase()) + "%";
        return (root, query, cb) -> cb.or(Arrays.stream(attributes)
                .map(attribute -> cb.like(cb.lower(root.<String>get(attribute)), pattern, ESCAPE))
                .toArray(Predicate[]::new));
    }

    /**
     * {@code first + ' ' + last} contains {@code text}, ignoring case.
     */
    public static <T> Specification<T> fullNameContains(String text, String firstAttribute, String lastAttribute) {
        if (text == null || text.isBlank()) {
            return Specification.unrestricted();
        }
        String pattern = "%" + escapeLike(text.trim().toLowerCase()) + "%";
        return (root, query, cb) -> {
            Expression<String> fullName = cb.concat(cb.concat(root.<String>get(firstAttribute), " "),
                    root.<String>get(lastAttribute));
            return cb.like(cb.lower(fullName), pattern, ESCAPE);
        };
    }

    static String escapeLike(String text) {
        return text.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
