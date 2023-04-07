/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HTTPSchemeTests {

    @Nested
    class ToString {

        @Test
        @DisplayName("SECURE returns \"https\"")
        void httpsOnSecure() {
            String actual = HTTPScheme.SECURE.toString();

            assertThat(actual).isEqualTo("https");
        }

        @Test
        @DisplayName("UNSECURE returns \"http\"")
        void httpOnUnsecure() {
            String actual = HTTPScheme.UNSECURE.toString();

            assertThat(actual).isEqualTo("http");
        }
    }

    @Nested
    @DisplayName("Method: valueOf(boolean)")
    class ValueOfBoolean {

        @Test
        @DisplayName("true returns SECURE")
        void secureOnTrue() {
            HTTPScheme actual = HTTPScheme.valueOf(true);

            assertThat(actual).isEqualTo(HTTPScheme.SECURE);
        }

        @Test
        @DisplayName("false returns UNSECURE")
        void unsecureOnFalse() {
            HTTPScheme actual = HTTPScheme.valueOf(false);

            assertThat(actual).isEqualTo(HTTPScheme.UNSECURE);
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
            Optional<HTTPScheme> actual = HTTPScheme.instanceOf(invalidValue);

            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("\"https\" returns SECURE")
        void secureOnHttps() {
            Optional<HTTPScheme> actual = HTTPScheme.instanceOf("https");

            assertThat(actual).contains(HTTPScheme.SECURE);
        }

        @Test
        @DisplayName("\"http\" returns UNSECURE")
        void unsecureOnHttp() {
            Optional<HTTPScheme> actual = HTTPScheme.instanceOf("http");

            assertThat(actual).contains(HTTPScheme.UNSECURE);
        }
    }
}
