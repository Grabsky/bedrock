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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link Registry} is a container that wraps around {@link HashMap} to provide simplified method calls.
 *
 * @param <K> key type
 * @param <V> value type
 */
public abstract class Registry<K, V> {

    private final HashMap<K, V> internalMap = new HashMap<>();

    /**
     * Returns {@link V} associated with specified {@link K} {@code (key)} or {@code null}.
     */
    public @Nullable V get(final K key) {
        return internalMap.get(key);
    }

    /**
     * Associates specified {@link K} {@code (key)} with specified {@link V} {@code (value)}. If key already exists, it's value is overriden.
     */
    public @NotNull Registry<K, V> set(final @NotNull K key, final @Nullable V value) {
        internalMap.put(key, value);
        return this;
    }

    /**
     * Returns {@link Set} of all registry entries.
     */
    public @NotNull Set<Map.Entry<K, V>> entries() {
        return new HashMap<K, V>(internalMap).entrySet();
    }

}
