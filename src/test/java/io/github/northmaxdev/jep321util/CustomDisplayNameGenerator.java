/* SPDX-License-Identifier: Unlicense */

package io.github.northmaxdev.jep321util;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Map;

public class CustomDisplayNameGenerator implements DisplayNameGenerator {

    private static final String TOP_LEVEL_CLASS_NAME_SUFFIX = "Tests";
    private static final Map<String, String> MAGIC_NESTED_CLASS_NAMES_TO_DISPLAY_NAMES = Map.of(
            "ToString", "Method: toString"
    );
    private static final Map<String, String> MAGIC_METHOD_NAMES_TO_DISPLAY_NAMES = Map.of(
            "equalsAndHashCode", "equals/hashCode contract"
    );

    @Override
    public String generateDisplayNameForClass(Class<?> aClass) {
        String testClassName = aClass.getSimpleName();

        int suffixIndex = testClassName.indexOf(TOP_LEVEL_CLASS_NAME_SUFFIX);
        /*
         * If the top-level test class name does NOT follow the convention of {TESTED_CLASS_NAME + "Tests"},
         * simply return it as-is with some kind of visual marker to warn the user.
         */
        if (suffixIndex == -1) {
            return markedAsIs(testClassName);
        }

        return "Tests for: [" + testClassName.substring(0, suffixIndex) + ']';
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> aClass) {
        String nestedClassName = aClass.getSimpleName();
        return MAGIC_NESTED_CLASS_NAMES_TO_DISPLAY_NAMES.getOrDefault(nestedClassName, markedAsIs(nestedClassName));
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> aClass, Method method) {
        String methodName = method.getName();
        return MAGIC_METHOD_NAMES_TO_DISPLAY_NAMES.getOrDefault(methodName, markedAsIs(methodName));
    }

    private static String markedAsIs(String name) {
        return "<As-is> " + name;
    }
}
