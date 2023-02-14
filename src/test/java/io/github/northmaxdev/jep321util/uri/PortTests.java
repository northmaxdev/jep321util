package io.github.northmaxdev.jep321util.uri;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

// TODO: Display name generator
class PortTests {

    @Nested
    @DisplayName("Method: of(int)")
    class Of {

        @ParameterizedTest
        @ValueSource(ints = {0, 65536, 443, 80, 8080})
        @DisplayName("Does not throw on values inside the allowed range")
        void acceptsValuesInsideRange(int value) {
            assertDoesNotThrow(() -> Port.of(value));
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 65536, Integer.MAX_VALUE})
        @DisplayName("Throws IAE on values outside the allowed range")
        void rejectsValuesOutsideRange(int value) {
            assertThrows(IllegalArgumentException.class, () -> Port.of(value));
        }
    }

    @Test
    // TODO: Display name generator
    void equalsAndHashCode() {
        EqualsVerifier.forClass(Port.class)
                .verify();
    }
}
