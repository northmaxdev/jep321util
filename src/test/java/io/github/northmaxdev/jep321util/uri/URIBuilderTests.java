/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import com.google.common.net.HostSpecifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class URIBuilderTests {

    static final HostSpecifier DUMMY_HOST = HostSpecifier.fromValid("example.com");

    @ParameterizedTest
    @MethodSource("provideConfigsAndExpectedResults")
    @DisplayName("Builder configurations produce the correct URIs")
    void configsAndExpectedResultsMatch(URIBuilder builderConfig, Iterable<URI> possibleResults) {
        URI actual = builderConfig.build();

        assertThat(actual).isIn(possibleResults);
    }

    Stream<Arguments> provideConfigsAndExpectedResults() {
        URIBuilder config1 = new URIBuilder(DUMMY_HOST)
                .https()
                .defaultPort()
                .pathSegment("reports")
                .param("year", 2010)
                .param("month", 5);
        Iterable<URI> config1PossibleResults = List.of(
                URI.create("https://example.com/reports?year=2010&month=5"),
                URI.create("https://example.com/reports?month=5&year=2010")
        );

        URIBuilder config2 = new URIBuilder(DUMMY_HOST)
                .http()
                .port(8080)
                .param("foo", true);
        Iterable<URI> config2PossibleResults = List.of(
                URI.create("http://example.com:8080/?foo=true")
        );

        URIBuilder config3 = new URIBuilder(DUMMY_HOST);
        Iterable<URI> config3PossibleResults = List.of(
                URI.create("https://example.com/")
        );

        URIBuilder config4 = new URIBuilder(DUMMY_HOST)
                .pathSegment("products")
                .pathSegment("laptops")
                .pathSegment(12345);
        Iterable<URI> config4PossibleResults = List.of(
                URI.create("https://example.com/products/laptops/12345")
        );

        return Stream.of(
                arguments(config1, config1PossibleResults),
                arguments(config2, config2PossibleResults),
                arguments(config3, config3PossibleResults),
                arguments(config4, config4PossibleResults)
        );
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(URIBuilder.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
