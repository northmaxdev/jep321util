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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class URIBuilderTests {

    static final HostSpecifier DUMMY_HOST = HostSpecifier.fromValid("example.com");

    @ParameterizedTest
    @MethodSource("provideConfigsAndExpectedResults")
    @DisplayName("Builder configurations produce the correct URIs")
    void configsAndExpectedResultsMatch(URIBuilder builderConfig, URI expected) {
        URI actual = builderConfig.build();

        assertThat(actual).isEqualTo(expected);
    }

    Stream<Arguments> provideConfigsAndExpectedResults() {
        URIBuilder config1 = new URIBuilder(DUMMY_HOST)
                .https()
                .defaultPort()
                .pathSegment("reports")
                .param("year", 2010)
                .param("month", 5);
        URI config1Expected = URI.create("https://example.com/reports?year=2010&month=5");

        URIBuilder config2 = new URIBuilder(DUMMY_HOST)
                .http()
                .port(8080)
                .param("foo", true);
        URI config2Expected = URI.create("http://example.com:8080/?foo=true");

        URIBuilder config3 = new URIBuilder(DUMMY_HOST);
        URI config3Expected = URI.create("https://example.com/");

        URIBuilder config4 = new URIBuilder(DUMMY_HOST)
                .pathSegment("products")
                .pathSegment("laptops")
                .pathSegment(12345);
        URI config4Expected = URI.create("https://example.com/products/laptops/12345");

        URIBuilder config5 = new URIBuilder(DUMMY_HOST)
                .pathSegment("search")
                .param("query", "Что такое JVM?");
        URI config5Expected = URI.create("https://example.com/search?query=%D0%A7%D1%82%D0%BE%20%D1%82%D0%B0%D0%BA%D0%BE%D0%B5%20JVM%3F");

        URIBuilder config6 = new URIBuilder(DUMMY_HOST)
                .pathSegment("заказы");
        URI config6Expected = URI.create("https://example.com/%D0%B7%D0%B0%D0%BA%D0%B0%D0%B7%D1%8B");

        return Stream.of(
                arguments(config1, config1Expected),
                arguments(config2, config2Expected),
                arguments(config3, config3Expected),
                arguments(config4, config4Expected),
                arguments(config5, config5Expected),
                arguments(config6, config6Expected)
        );
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(URIBuilder.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
