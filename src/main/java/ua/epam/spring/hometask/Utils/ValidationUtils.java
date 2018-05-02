package ua.epam.spring.hometask.Utils;

import java.util.Collection;
import java.util.Objects;

public class ValidationUtils {

    public static boolean isValidId(final Long id) {
        return id != null && id > 0;
    }

    public static boolean isValidPrice(final Double price) {
        return price >= 0;
    }

    public static boolean isNotEmpty(final String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isValid(final Collection<?> collection) {
        return collection != null || !collection.isEmpty() || collection.stream().anyMatch(Objects::isNull);
    }

    public static boolean hasEmptyElements(final Collection<?> collection) {
        return collection.stream().anyMatch(Objects::isNull);
    }
}
