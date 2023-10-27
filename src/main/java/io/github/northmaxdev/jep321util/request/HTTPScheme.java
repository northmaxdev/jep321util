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

import java.util.Optional;

/**
 * Models the URI scheme for the <i>Hypertext Transfer Protocol</i>.
 */
public enum HTTPScheme {

    /**
     * The instance that represents HTTPS.
     */
    SECURE("https"),

    /**
     * The instance that represents HTTP.
     */
    UNSECURE("http");

    private final String value;

    HTTPScheme(String value) {
        this.value = value;
    }

    /**
     * Static factory method to fetch the corresponding instance for the given string value, e.g., a value of
     * {@code "https"} would return {@link #SECURE}. <b>This method is case-sensitive, only lowercase values will do</b>
     * <i>(for the sake of symmetry against {@link #toString()}).</i>
     *
     * @param s the given string value, {@code null} is technically allowed
     * @return an {@link Optional} containing the corresponding instance or an empty one if the string value is
     * unrecognized or is {@code null}
     */
    public static Optional<HTTPScheme> instanceOf(String s) {
        return switch (s) {
            case "https" -> Optional.of(SECURE);
            case "http" -> Optional.of(UNSECURE);
            case null, default -> Optional.empty();
        };
    }

    /**
     * A convenient static factory method to fetch the corresponding instance for the given Boolean value.
     *
     * @param value a {@code boolean} value
     * @return {@link #SECURE} if {@code true}, {@link #UNSECURE} if {@code false}
     */
    public static HTTPScheme valueOf(boolean value) {
        return value ? SECURE : UNSECURE;
    }

    /**
     * Converts this instance to a string value. Both values are lowercase as per
     * <a href=https://www.rfc-editor.org/rfc/rfc3986#section-3.1>RFC 3986 section 3.1</a>.
     *
     * @return the value {@code "https"} for {@link #SECURE} or the value {@code "http"} for {@link #UNSECURE}.
     */
    @Override
    public String toString() {
        return value;
    }
}
