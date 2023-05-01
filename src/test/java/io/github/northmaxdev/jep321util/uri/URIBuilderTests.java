/*
 * MIT License
 *
 * Copyright (c) 2023 Maxim Altoukhov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.northmaxdev.jep321util.uri;

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

    @ParameterizedTest
    @MethodSource("provideConfigsAndExpectedResults")
    @DisplayName("Builder configurations produce the correct URIs")
    void configsAndExpectedResultsMatch(URIBuilder builderConfig, URI expected) {
        URI actual = builderConfig.build();

        assertThat(actual).isEqualTo(expected);
    }

    Stream<Arguments> provideConfigsAndExpectedResults() {
        URIBuilder config1 = URIBuilder.withValidHost("example.com")
                .https()
                .defaultPort()
                .pathSegment("foo")
                .param("bar", 42)
                .param("baz", true);
        URI config1Expected = URI.create("https://example.com/foo?bar=42&baz=true");

        URIBuilder config2 = URIBuilder.withValidHost("example.com");
        URI config2Expected = URI.create("https://example.com/");

        URIBuilder config3 = URIBuilder.withLocalhost()
                .http()
                .port(8080);
        URI config3Expected = URI.create("http://localhost:8080/");

        URIBuilder config4 = URIBuilder.withLocalhost()
                .pathSegment("поиск")
                .param("q", "Джава");
        URI config4Expected = URI.create("https://localhost/%D0%BF%D0%BE%D0%B8%D1%81%D0%BA?q=%D0%94%D0%B6%D0%B0%D0%B2%D0%B0");

        return Stream.of(
                arguments(config1, config1Expected),
                arguments(config2, config2Expected),
                arguments(config3, config3Expected),
                arguments(config4, config4Expected)
        );
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(URIBuilder.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
