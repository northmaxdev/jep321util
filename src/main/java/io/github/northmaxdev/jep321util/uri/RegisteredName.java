// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.jep321util.uri;

/**
 * Models a <i>registered name</i> as per
 * <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.2.2">RFC 3986 section 3.2.2</a>.
 */
public final class RegisteredName implements Host {

    @Override
    public String formatted() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}
