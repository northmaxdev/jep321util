/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PortsTests {

    @Nested
    @DisplayName("Method: isValid(int)")
    class IsValid {

        @ParameterizedTest
        @ValueSource(ints = {0, 65535, 443, 80, 8080})
        @DisplayName("Returns \"true\" when given a legal value")
        void trueOnLegalValues(int port) {
            assertTrue(Ports.isValid(port));
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 65536, Integer.MAX_VALUE})
        @DisplayName("Returns \"false\" when given an illegal value")
        void falseOnIllegalValues(int port) {
            assertFalse(Ports.isValid(port));
        }
    }
}
