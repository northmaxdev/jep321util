/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HostTests {

    @Test
    @DisplayName("Localhost implementation formats to \"localhost\"")
    void localhostFormatsCorrectly() {
        Host host = Host.localhost();

        assertEquals("localhost", host.formatted());
    }
}
