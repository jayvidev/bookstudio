package com.bookstudio.shared.paging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SpecsTest {

    @Test
    void escapesLikeWildcardsSoTheyMatchLiterally() {
        assertThat(Specs.escapeLike("50%_off\\x")).isEqualTo("50\\%\\_off\\\\x");
    }
}
