/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

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

    private final String strForm;

    HTTPScheme(String strForm) {
        this.strForm = strForm;
    }

    /**
     * Static factory method to fetch the corresponding instance for the given string value, e.g. a value of
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
        return strForm;
    }
}
