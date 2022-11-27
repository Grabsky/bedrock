package grabsky.core.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registries work in a similar way to maps, in fact, they use a {@link HashMap} as a storage for KEY-VALUE pairs.
 *
 * @param <K> key
 * @param <V> value
 */
public abstract class Registry<K, V> {

    private final Map<K, V> internalMap = new HashMap<>();

    /**
     * Returns {@link V} associated with specified {@link K} {@code (key)} or {@code null}.
     */
    public @Nullable V get(final K key) {
        return internalMap.get(key);
    }

    /**
     * Associates specified {@link K} {@code (key)} with specified {@link V} {@code (value)}. If key already exists, it's value is overriden.
     */
    public @NotNull Registry<K, V> set(final K key, final V value) {
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
