package com.bookstudio.shared.paging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bookstudio.shared.exception.BadRequestException;

class SortWhitelistTest {

    private final SortWhitelist whitelist = SortWhitelist.of("id", "loanDate");

    @Test
    void acceptsAllowedProperties() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("loanDate").descending().and(Sort.by("id")));

        assertThat(whitelist.validate(pageable)).isSameAs(pageable);
    }

    @Test
    void acceptsUnsortedRequests() {
        assertThat(whitelist.validate(Pageable.unpaged())).isEqualTo(Pageable.unpaged());
    }

    @Test
    void rejectsUnknownProperty() {
        assertThatThrownBy(() -> whitelist.validate(PageRequest.of(0, 20, Sort.by("observation"))))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Cannot sort by 'observation'. Allowed: [id, loanDate]");
    }
}
