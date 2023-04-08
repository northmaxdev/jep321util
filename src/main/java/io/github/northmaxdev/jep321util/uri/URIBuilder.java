/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import com.google.common.net.HostSpecifier;
import com.google.common.net.PercentEscaper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

/**
 * Builder for {@link URI} instances.
 *
 * @apiNote This class's API is designed around
 * <a href="https://www.rfc-editor.org/rfc/rfc3986">RFC 3986</a>, while {@link URI} is designed around
 * <a href="https://www.ietf.org/rfc/rfc2396">RFC 2396</a>. Generally speaking, this <b>will not</b>
 * cause any incompatibility issues <i>precisely</i> because it has been taken into account, though
 * this should still be something to be aware of.
 */
public final class URIBuilder {

    private HTTPScheme scheme;
    private String hostAsStr;
    private Integer port;
    private final List<String> pathSegments;
    private final Map<String, String> params;
    private final PercentEscaper percentEscaper;

    /**
     * Constructs an instance with the default configuration as follows:
     * <ul>
     *     <li>Scheme: HTTPS</li>
     *     <li>Host: <i>set to the provided value</i></li>
     *     <li>Port: <i>none</i></li>
     *     <li>Path segments: <i>none</i> (i.e. the root path)</li>
     *     <li>Query parameters: <i>none</i></li>
     * </ul>
     *
     * @param host the URI's host, not {@code null}
     * @throws NullPointerException if the provided host is {@code null}
     */
    public URIBuilder(HostSpecifier host) {
        https();
        host(host);
        defaultPort();

        this.pathSegments = new LinkedList<>(); /* Insertion order is important */
        this.params = new LinkedHashMap<>(); /* Insertion order is important */
        /*
         * Note: Guava v31.1-jre JavaDoc doesn't explicitly specify whether an empty string for safeChars is OK,
         * but it must non-null and there aren't any additional safe characters, so here goes nothing.
         */
        this.percentEscaper = new PercentEscaper("", false);
    }

    /**
     * Sets the URI's scheme.
     *
     * @param s a non-{@code null} {@link HTTPScheme}
     * @return this builder
     * @throws NullPointerException if the provided scheme is {@code null}
     */
    public URIBuilder scheme(HTTPScheme s) {
        this.scheme = Objects.requireNonNull(s, "Scheme cannot be null");
        return this;
    }

    /**
     * Sets the URI's scheme to HTTPS.
     *
     * @return this builder
     */
    public URIBuilder https() {
        this.scheme = HTTPScheme.SECURE;
        return this;
    }

    /**
     * Sets the URI's scheme to HTTP.
     *
     * @return this builder
     */
    public URIBuilder http() {
        this.scheme = HTTPScheme.UNSECURE;
        return this;
    }

    /**
     * Sets the URI's host.
     *
     * @param h a non-{@code null} host
     * @return this builder
     * @throws NullPointerException if the given host is {@code null}
     */
    public URIBuilder host(HostSpecifier h) {
        Objects.requireNonNull(h, "Host cannot be null");
        this.hostAsStr = h.toString();
        return this;
    }

    /**
     * Sets the URI's port.
     *
     * @param p a valid port value
     * @return this builder
     * @throws IllegalArgumentException if the given value is illegal as per {@link Ports#isValid(int)}
     * @see Ports
     */
    public URIBuilder port(int p) {
        if (!Ports.isValid(p)) {
            throw new IllegalArgumentException("Illegal port value: " + p);
        }
        this.port = p;
        return this;
    }

    /**
     * Resets the URI's port.
     *
     * @return this builder
     */
    public URIBuilder defaultPort() {
        this.port = null;
        return this;
    }

    /**
     * Adds a path segment.
     * <p>
     * <b>Clarification:</b> given a path of {@code /foo/bar/baz}, its segments are: {@code foo}, {@code bar} and
     * {@code baz}.
     *
     * @param s a string that is neither {@code null} nor empty
     * @return this builder
     * @throws NullPointerException     if the given string is {@code null}
     * @throws IllegalArgumentException if the given string is empty
     */
    public URIBuilder pathSegment(String s) {
        Objects.requireNonNull(s, "Path segment cannot be null");
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Path segment cannot be empty");
        }
        this.pathSegments.add(s);
        return this;
    }

    /**
     * {@link #pathSegment(String)} overload for values of type {@code byte}. This is useful for working with RESTful
     * web APIs.
     *
     * @param b a {@code byte} value
     * @return this builder
     * @see #pathSegment(short)
     * @see #pathSegment(int)
     * @see #pathSegment(long)
     */
    public URIBuilder pathSegment(byte b) {
        return pathSegment(Byte.toString(b));
    }

    /**
     * {@link #pathSegment(String)} overload for values of type {@code short}. This is useful for working with RESTful
     * web APIs.
     *
     * @param s a {@code short} value
     * @return this builder
     * @see #pathSegment(byte)
     * @see #pathSegment(int)
     * @see #pathSegment(long)
     */
    public URIBuilder pathSegment(short s) {
        return pathSegment(Short.toString(s));
    }

    /**
     * {@link #pathSegment(String)} overload for values of type {@code int}. This is useful for working with RESTful
     * web APIs.
     *
     * @param i a {@code int} value
     * @return this builder
     * @see #pathSegment(byte)
     * @see #pathSegment(short)
     * @see #pathSegment(long)
     */
    public URIBuilder pathSegment(int i) {
        return pathSegment(Integer.toString(i));
    }

    /**
     * {@link #pathSegment(String)} overload for values of type {@code long}. This is useful for working with RESTful
     * web APIs.
     *
     * @param l a {@code long} value
     * @return this builder
     * @see #pathSegment(byte)
     * @see #pathSegment(short)
     * @see #pathSegment(int)
     */
    public URIBuilder pathSegment(long l) {
        return pathSegment(Long.toString(l));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value, must be neither {@code null} nor empty
     * @return this builder
     * @throws NullPointerException     if either the name or value is {@code null}
     * @throws IllegalArgumentException if either the name or value is empty
     */
    public URIBuilder param(String name, String value) {
        Objects.requireNonNull(name, "Parameter name cannot be null");
        Objects.requireNonNull(value, "Parameter value cannot be null");

        if (name.isEmpty() || value.isEmpty()) {
            throw new IllegalArgumentException("Neither the parameter name nor its value may be null");
        }

        this.params.put(name, value);
        return this;
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, byte value) {
        return param(name, Byte.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, short value) {
        return param(name, Short.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, int value) {
        return param(name, Integer.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, long value) {
        return param(name, Long.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, float value) {
        return param(name, Float.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, double value) {
        return param(name, Double.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, char value) {
        return param(name, Character.toString(value));
    }

    /**
     * Sets a query parameter.
     *
     * @param name  the parameter's name, must be neither {@code null} nor empty
     * @param value the parameter's value
     * @return this builder
     * @throws NullPointerException     if either the name is {@code null}
     * @throws IllegalArgumentException if either the name is empty
     */
    public URIBuilder param(String name, boolean value) {
        return param(name, Boolean.toString(value));
    }

    /**
     * Builds a {@link URI} instance. Path segments and query parameters are percent-encoded when required as per
     * <a href="https://www.rfc-editor.org/rfc/rfc3986#section-2.1">RFC 3986 section 2.1</a>.
     *
     * @return a non-{@code null} {@link URI} <i>(fresh from the oven!)</i>
     * @throws IllegalStateException if for whatever reason the builder's configuration produces a malformed URI
     *                               <i>(make sure to read the exception message)</i>
     * @apiNote Query parameters are serialized in the same order as they were added
     */
    public URI build() {
        /*
         * Path segments are percent-encoded. The host is not. The java.lang.URI class JavaDoc specifies
         * these are permitted to be percent-encoded as per RFC 2396, but not only this library is based
         * primarily on RFC 3986 which obsoletes the former, but I also cannot find any explicit mention
         * of this permit in either of them.
         */

        StringBuilder sb = new StringBuilder()
                .append(scheme.toString())
                .append("://")
                .append(hostAsStr);

        if (port != null) {
            sb.append(':');
            sb.append(port);
        }

        sb.append('/');

        if (!pathSegments.isEmpty()) {
            String path = pathSegments.stream()
                    .map(percentEscaper::escape)
                    .collect(joining("/"));
            sb.append(path);
        }

        if (!params.isEmpty()) {
            String query = params.entrySet()
                    .stream()
                    .map(entry -> {
                        String escapedName = percentEscaper.escape(entry.getKey());
                        String escapedValue = percentEscaper.escape(entry.getValue());
                        return escapedName + '=' + escapedValue;
                    })
                    .collect(joining("&", "?", ""));
            sb.append(query);
        }

        try {
            return new URI(sb.toString());
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Attempting to build a URI resulted in malformed syntax. " +
                    "This is most likely a bug and/or the developer's oversight. " +
                    "Please report this to whoever maintains the source code currently.", e);
        }
    }

    /**
     * Checks whether this builder is equal to the provided object.
     *
     * @param obj a possibly {@code null} object
     * @return {@code true} if and only if the provided object is a non-{@code null} builder with the same configuration
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof URIBuilder)) {
            return false;
        }

        URIBuilder other = (URIBuilder) obj;
        return Objects.equals(this.scheme, other.scheme)
                && Objects.equals(this.hostAsStr, other.hostAsStr)
                && Objects.equals(this.port, other.port)
                && Objects.equals(this.pathSegments, other.pathSegments)
                && Objects.equals(this.params, other.params)
                && Objects.equals(this.percentEscaper, other.percentEscaper);
    }

    /**
     * Computes a suitable hash code for this builder.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(scheme, hostAsStr, port, pathSegments, params, percentEscaper);
    }

    /**
     * Returns a string representation of this builder's configuration.
     *
     * @return a non-{@code null} string
     */
    @Override
    public String toString() {
        /* Implementation generated by IntelliJ IDEA */
        return "URIBuilder{" +
                "scheme=" + scheme +
                ", host=" + hostAsStr +
                ", port=" + port +
                ", pathSegments=" + pathSegments +
                ", params=" + params +
                ", percentEscaper=" + percentEscaper +
                '}';
    }
}
