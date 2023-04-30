/*
 * MIT License
 *
 * Copyright (c) 2023 Maxim Altoukhov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.northmaxdev.jep321util;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Map;

public final class CustomDisplayNameGenerator implements DisplayNameGenerator {

    private static final Map<String, String> MAGIC_NESTED_CLASS_NAMES_TO_DISPLAY_NAMES = Map.of(
            "ToString", "Method: toString"
    );
    private static final Map<String, String> MAGIC_METHOD_NAMES_TO_DISPLAY_NAMES = Map.of(
            "equalsAndHashCode", "equals/hashCode contract"
    );

    @Override
    public String generateDisplayNameForClass(Class<?> aClass) {
        String testClassName = aClass.getSimpleName();

        int suffixIndex = testClassName.indexOf("Tests");
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
