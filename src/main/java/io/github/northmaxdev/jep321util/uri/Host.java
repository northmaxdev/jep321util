/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

/**
 * The host subcomponent of a URI as per
 * <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.2.2">RFC 3986 section 3.2.2</a>.
 */
public interface Host {

    /**
     * Formats this host as per its formal syntax.
     * <p>
     * <b>Clarification:</b> if the implementation represents an IPv4 or IPv6 address (merely examples), this method
     * should simply return an IP literal as a string, regardless of how it is stored internally within the class.
     *
     * @return a non-{@code null} string
     */
    String formatted();

    /**
     * Returns a {@code Host} implementation that formats to {@code localhost}.
     *
     * @return a non-{@code null} {@code Host} object
     */
    static Host localhost() {
        return () -> "localhost";
    }
}
