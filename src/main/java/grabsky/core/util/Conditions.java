package grabsky.core.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Conditions {

    /**
     * Returns {@link T} ({@code value}) if not {@code null} or throws {@link E} ({@code exception}) otherwise.
     */
    public static <T, E extends Throwable> @NotNull T requirePresent(@Nullable final T value, final E exception) throws E {
        // throwing specified exception if value is null
        if (value == null) throw exception;
        // returning the value otherwise
        return value;
    }

    /**
     * Returns {@link T} ({@code value}) if not {@code null} or {@link T} ({@code def}) otherwise.
     */
    public static <T> @NotNull T requirePresent(@Nullable final T value, final T def) {
        return (value != null) ? value : def;
    }

    /**
     * Returns {@code true} if {@code num} is in range between {@code rangeFrom} and {@code rangeTo}.
     */
    public static boolean inRange(final int num, final int rangeFrom, final int rangeTo) {
        return num >= rangeFrom && num <= rangeTo;
    }

}
