/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

/**
 * Utility class for the URI port subcomponent as per
 * <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.2.3">RFC 3986 section 3.2.3</a>.
 * The range of valid port numbers is defined by
 * <a href="https://www.rfc-editor.org/rfc/rfc6335#section-6">RFC 6335 section 6</a>.
 */
public final class Ports {

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
}
