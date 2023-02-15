package io.github.northmaxdev.jep321util.uri;

/**
 * Models the port subcomponent of a URI authority as per
 * <a href=https://www.rfc-editor.org/rfc/rfc3986#section-3.2.3>RFC 3986 section 3.2.3</a>.
 */
public final class Port {

    private final int value;

    /**
     * Primary static factory method.
     *
     * @param value a value within [0, 65535]
     * @return a {@code Port} instance
     * @throws IllegalArgumentException if the given integer value is outside the allowed range
     */
    public static Port of(int value) throws IllegalArgumentException {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }
        return new Port(value);
    }

    private Port(int value) {
        this.value = value;
    }

    /**
     * Checks whether this port is equal to the given object.
     *
     * @param obj an arbitrary object, {@code null} is allowed
     * @return {@code true} if and only if the provided object is a non-{@code null} {@code Port} with the same value
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Computes a hash code for this port.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@code String} representation of this port.
     *
     * @return a string representation <i>(not necessarily just the number itself)</i>
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}
