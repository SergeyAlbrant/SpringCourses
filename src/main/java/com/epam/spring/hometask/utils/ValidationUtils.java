package com.epam.spring.hometask.utils;

import java.util.Collection;
import java.util.Objects;

public class ValidationUtils {

    public static boolean isValidId(final Long id) {
        return id != null && id > 0;
    }

    public static boolean isNotEmpty(final String string) {
        return string != null && !string.isEmpty();
    }

}
