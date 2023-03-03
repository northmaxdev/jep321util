/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
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
 * <a href="https://www.ietf.org/rfc/rfc2396">RFC 2396</a>. The former obsoletes the latter, which thus may or may not
 * lead to compatibility/consistency issues.
 */
public final class URIBuilder {

    private HTTPScheme scheme;
    private Host host;
    private Port port;
    private final List<String> pathSegments;
    private final Map<String, String> params;

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
    public URIBuilder(Host host) {
        this.scheme = HTTPScheme.SECURE;
        this.host = host;
        this.port = null;
        this.pathSegments = new LinkedList<>(); /* Insertion order is crucial here */
        this.params = new HashMap<>();
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
    public URIBuilder host(Host h) {
        this.host = Objects.requireNonNull(h, "Host cannot be null");
        return this;
    }

    /**
     * Sets the URI's port.
     *
     * @param p a {@link Port} instance, {@code null} is allowed
     * @return this builder
     */
    public URIBuilder port(Port p) {
        this.port = p;
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
     */
    public URI build() {
        StringBuilder sb = new StringBuilder()
                .append(scheme.toString())
                .append("://")
                .append(host.formatted());

        if (port != null) {
            sb.append(':');
            sb.append(port.getValue());
        }

        sb.append('/');

        if (!pathSegments.isEmpty()) {
            String path = pathSegments.stream()
                    .map(URIBuilder::percentEncoded)
                    .collect(joining("/"));
            sb.append(path);
        }

        if (!params.isEmpty()) {
            String query = params.entrySet()
                    .stream()
                    .map(entry -> percentEncoded(entry.getKey()) + '=' + percentEncoded(entry.getValue()))
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

    private static String percentEncoded(String s) {
        /* TODO: Implement */
        return s;
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
                && Objects.equals(this.host, other.host)
                && Objects.equals(this.port, other.port)
                && Objects.equals(this.pathSegments, other.pathSegments)
                && Objects.equals(this.params, other.params);
    }

    /**
     * Computes a suitable hash code for this builder.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(scheme, host, port, pathSegments, params);
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
                ", host=" + host +
                ", port=" + port +
                ", pathSegments=" + pathSegments +
                ", params=" + params +
                '}';
    }
}
