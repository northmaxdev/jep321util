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

import java.util.Objects;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

/**
 * Utility class for the URI port subcomponent as per
 * <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.2.3">RFC 3986 section 3.2.3</a>.
 * The range of valid port numbers is defined by
 * <a href="https://www.rfc-editor.org/rfc/rfc6335#section-6">RFC 6335 section 6</a>.
 */
public final class Ports {

    private static final RandomGenerator DEFAULT_RNG = RandomGenerator.getDefault();

    /**
     * The lowest value allowed as per
     * <a href="https://www.rfc-editor.org/rfc/rfc6335#section-6">RFC 6335 section 6</a>, which is 0.
     */
    public static final int MIN = 0;

    /**
     * The highest value allowed as per
     * <a href="https://www.rfc-editor.org/rfc/rfc6335#section-6">RFC 6335 section 6</a>, which is 65535.
     */
    public static final int MAX = 65535;

    private Ports() {}

    /**
     * Checks whether the given port value is valid.
     *
     * @param port an integer value
     * @return {@code true} if the value is within [{@value MIN}, {@value MAX}] and {@code false} otherwise
     */
    public static boolean isValid(int port) {
        return port >= MIN && port <= MAX;
    }

    /**
     * Generates a random port number within the legal boundaries.
     *
     * @return a random integer within [{@value MIN}, {@value MAX}] (both ends inclusive)
     */
    public static int random() {
        return random(DEFAULT_RNG);
    }

    /**
     * Generates a random port number within the legal boundaries using the provided random number generator.
     *
     * @param generator a non-{@code null} instance of {@link RandomGenerator}
     * @return a random integer within [{@value MIN}, {@value MAX}] (both ends inclusive)
     * @throws NullPointerException if the provided generator is {@code null}
     */
    public static int random(RandomGenerator generator) {
        Objects.requireNonNull(generator, "Generator must not be null");
        return generator.nextInt(MIN, MAX + 1);
    }

    /**
     * Constructs a stream containing all the values within [{@value MIN}, {@value MAX}] (both ends inclusive).
     *
     * @return a non-{@code null} {@link IntStream}
     */
    public static IntStream allLegalValues() {
        return IntStream.rangeClosed(MIN, MAX);
    }
}
