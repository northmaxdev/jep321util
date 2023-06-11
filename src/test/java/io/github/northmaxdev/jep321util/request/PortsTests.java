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

package io.github.northmaxdev.jep321util.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
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

    @RepeatedTest(100)
    @DisplayName("Randomly generated port is within legal boundaries")
    void randomIsWithinRange() {
        int value = Ports.random();

        assertThat(value).isBetween(0, 65535);
    }
}
