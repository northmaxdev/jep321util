/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util.uri;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RegisteredNameTests {

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(RegisteredName.class)
                .verify();
    }
}
