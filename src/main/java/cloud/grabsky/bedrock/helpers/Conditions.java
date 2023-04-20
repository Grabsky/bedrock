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
package cloud.grabsky.bedrock.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Conditions {

    /**
     * Returns {@link T} ({@code value}) if not {@code null} or throws {@link E} ({@code exception}) otherwise.
     */
    public static <T, E extends Throwable> @NotNull T requirePresent(@Nullable final T value, final @NotNull E exception) throws E {
        // Throwing provided exception in case provided value is 'null'.
        if (value == null)
            throw exception;
        // Returning the value otherwise.
        return value;
    }

    /**
     * Returns {@link T} ({@code value}) if not {@code null} or {@link T} ({@code def}) otherwise.
     */
    public static <T> @NotNull T requirePresent(final @Nullable T value, final @NotNull T def) {
        return (value != null) ? value : def;
    }

    /**
     * Returns {@code true} if {@code num} is in range between {@code rangeFrom} and {@code rangeTo}.
     */
    public static boolean inRange(final int num, final int rangeFrom, final int rangeTo) {
        return num >= rangeFrom && num <= rangeTo;
    }

}
