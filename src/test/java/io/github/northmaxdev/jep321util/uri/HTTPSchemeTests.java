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
