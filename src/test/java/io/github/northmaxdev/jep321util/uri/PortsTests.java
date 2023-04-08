/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class PortsTests {

    @Nested
    @DisplayName("Method: isValid(int)")
    class IsValid {

        @ParameterizedTest
        @ValueSource(ints = {0, 65535, 443, 80, 8080})
        @DisplayName("Returns \"true\" when given a legal value")
        void trueOnLegalValues(int port) {
            assertThat(Ports.isValid(port)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 65536, Integer.MAX_VALUE})
        @DisplayName("Returns \"false\" when given an illegal value")
        void falseOnIllegalValues(int port) {
            assertThat(Ports.isValid(port)).isFalse();
        }
    }

    @Test
    @DisplayName("Stream of all legal ports contains expected values only")
    void streamContainsLegalValuesOnly() {
        Iterable<Integer> legalValues = IntStream.rangeClosed(0, 65535)
                .boxed()
                .toList();

        assertThat(Ports.allLegalValues()).containsExactlyInAnyOrderElementsOf(legalValues);
    }
}
