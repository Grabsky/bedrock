/*
 * MIT License
 *
 * Copyright (c) 2023 Grabsky <44530932+Grabsky@users.noreply.github.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * HORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

    public static <T> @NotNull List<T> merge(final @Nullable List<T> original, final @NotNull T extra) {
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