package cloud.grabsky.bedrock.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Iterables {

    public static <T> @NotNull T[] merge(final @Nullable T[] original, final @NotNull T extra) {
        // ...
        final T[] newArray = java.util.Arrays.copyOf(original, original.length + 1);
        // ...
        newArray[original.length] = extra;
        // ...
        return newArray;
    }

    public static <T> @NotNull T[] merge(final @Nullable T[] original, final @NotNull T[] extra) {
        // ...
        final T[] newArray = java.util.Arrays.copyOf(original, original.length + extra.length);
        // ... (manual copy)
        for (int i = 0; i < extra.length; i++) {
            newArray[original.length + i] = extra[i];
        }
        // ...
        return newArray;
    }

    public static @NotNull <T> List<T> merge(final @Nullable List<T> original, @NotNull final T extra) {
        if (original == null) return List.of(extra);
        // ...
        final ArrayList<T> result = new ArrayList<T>(original);
        // ...
        result.add(extra);
        // ...
        return result;
    }

    public static <T> @NotNull List<T> merge(final @Nullable Collection<T> original, final @NotNull Collection<T> extra) {
        if (original == null) {
            // ...
            if (extra instanceof List<T>) return (List<T>) extra;
            // ...
            return new ArrayList<>(extra);
        }
        // ...
        final ArrayList<T> result = new ArrayList<T>(original);
        // ...
        result.addAll(extra);
        // ...
        return result;
    }

    public static @NotNull <T> List<T> toList(final @NotNull T[] array) {
        return java.util.Arrays.asList(array);
    }

    public static @NotNull <T, K> List<K> toList(final @NotNull T[] array, final @NotNull Function<T, K> func) {
        final List<K> newList = new ArrayList<K>(array.length);
        // ...
        for (final T element : array) {
            newList.add(func.apply(element));
        }
        // ...
        return newList;
    }
}