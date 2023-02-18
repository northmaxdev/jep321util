/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPSchemeTests {

    @Nested
    class ToString {

        @Test
        @DisplayName("SECURE returns \"https\"")
        void httpsOnSecure() {
            String expected = "https";
            String actual = HTTPScheme.SECURE.toString();

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("UNSECURE returns \"http\"")
        void httpOnUnsecure() {
            String expected = "http";
            String actual = HTTPScheme.UNSECURE.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("Method: valueOf(boolean)")
    class ValueOfBoolean {

        @Test
        @DisplayName("true returns SECURE")
        void secureOnTrue() {
            HTTPScheme expected = HTTPScheme.SECURE;
            HTTPScheme actual = HTTPScheme.valueOf(true);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("false returns UNSECURE")
        void unsecureOnFalse() {
            HTTPScheme expected = HTTPScheme.UNSECURE;
            HTTPScheme actual = HTTPScheme.valueOf(false);

            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("Method: instanceOf(String)")
    class InstanceOf {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"hello world", "HttpS"})
        @DisplayName("Invalid value returns an empty Optional")
        void invalidValueReturnsEmpty(String invalidValue) {
            Optional<HTTPScheme> expected = Optional.empty();
            Optional<HTTPScheme> actual = HTTPScheme.instanceOf(invalidValue);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("\"https\" returns SECURE")
        void secureOnHttps() {
            Optional<HTTPScheme> expected = Optional.of(HTTPScheme.SECURE);
            Optional<HTTPScheme> actual = HTTPScheme.instanceOf("https");

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("\"http\" returns UNSECURE")
        void unsecureOnHttp() {
            Optional<HTTPScheme> expected = Optional.of(HTTPScheme.UNSECURE);
            Optional<HTTPScheme> actual = HTTPScheme.instanceOf("http");

            assertEquals(expected, actual);
        }
    }
}
