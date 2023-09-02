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

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Getter;

import static cloud.grabsky.bedrock.util.Iterables.merge;
import static cloud.grabsky.bedrock.util.Iterables.toList;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

// TO-DO: Use Paper's Item Property API when available. (https://github.com/PaperMC/Paper/pull/8711)
public final class ItemBuilder {

    private final ItemStack item;

    @Getter(AccessLevel.PUBLIC)
    private final ItemMeta meta;

    private static final UUID EMPTY_UUID = UUID.nameUUIDFromBytes(new byte[0]);

    public ItemBuilder(final @NotNull Material material, final @Range(from = 1, to = 64) int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(final @NotNull ItemStack item) {
        this.item = new ItemStack(item);
        this.meta = item.getItemMeta();
    }

    public @NotNull ItemBuilder setName(final @NotNull Component name) {
        meta.displayName(name);
        return this;
    }

    public @NotNull ItemBuilder setName(final @NotNull String name) {
        meta.displayName(miniMessage().deserialize(name));
        return this;
    }

    public @NotNull ItemBuilder setLore(final @NotNull String... lines) {
        meta.lore(
                Stream.of(lines)
                        .map(s -> miniMessage().deserialize(s))
                        .toList()
        );
        return this;
    }

    public @NotNull ItemBuilder setLore(final @NotNull Component... lines) {
        meta.lore(toList(lines));
        return this;
    }

    public @NotNull ItemBuilder addLore(final @NotNull Component... lines) {
        meta.lore(merge(meta.lore(), toList(lines)));
        return this;
    }

    public @NotNull ItemBuilder addLore(final @NotNull String... lines) {
        meta.lore(merge(meta.lore(), toList(lines, (e) -> miniMessage().deserialize(e))));
        return this;
    }

    public @NotNull ItemBuilder setCustomModelData(final int value) {
        meta.setCustomModelData(value);
        return this;
    }

    public @NotNull ItemBuilder setItemFlags(final @NotNull ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        return this;
    }

    public @NotNull ItemBuilder addEnchantment(final @NotNull Enchantment enchantment, final int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public @NotNull ItemBuilder setSkullTexture(final @NotNull PlayerProfile profile) {
        if (meta instanceof SkullMeta skullMeta) {
            // Updating PlayerProfile inside the SkullMeta (casted ItemMeta)
            skullMeta.setPlayerProfile(profile);
        }
        return this;
    }

    public @NotNull ItemBuilder setSkullTexture(final @NotNull String value) {
        if (meta instanceof SkullMeta skullMeta) {
            final PlayerProfile profile = Bukkit.createProfile(EMPTY_UUID);
            // ...
            profile.setProperty(new ProfileProperty("textures", value));
            // Updating PlayerProfile inside the SkullMeta (casted ItemMeta)
            skullMeta.setPlayerProfile(profile);
        }
        return this;
    }

    public @NotNull ItemBuilder setSkullTexture(final @NotNull Player player) {
        if (meta instanceof SkullMeta skullMeta && player.isOnline() == true) {
            skullMeta.setOwningPlayer(player);
        }
        return this;
    }

    public <T, Z> ItemBuilder setPersistentData(final @NotNull NamespacedKey key, final @NotNull PersistentDataType<T, Z> type, final @Nullable Z value) {
        // Setting entry...
        if (value != null)
            meta.getPersistentDataContainer().set(key, type, value);
            // Removing entry if 'null' is provided.
        else if (meta.getPersistentDataContainer().has(key, type) == true)
            meta.getPersistentDataContainer().remove(key);
        return this;
    }

    public @NotNull ItemBuilder edit(final @NotNull Consumer<ItemMeta> consumer) {
        consumer.accept(meta);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends ItemMeta> @NotNull ItemBuilder edit(final @NotNull Class<T> type, final @NotNull Consumer<T> consumer) {
        consumer.accept((T) meta);
        return this;
    }

    public @NotNull ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}