/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URIBuilderTests {

    @Disabled
    @ParameterizedTest
    @MethodSource("provideConfigsAndExpectedResults")
    @DisplayName("Builder configurations produce the correct URIs")
    void configsAndExpectedResultsMatch(URIBuilder builder, URI expectedResult) {
        URI actualResult = builder.build();

        assertEquals(expectedResult, actualResult);
    }

    Stream<Arguments> provideConfigsAndExpectedResults() {
        return Stream.of(); /* TODO */
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(URIBuilder.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
