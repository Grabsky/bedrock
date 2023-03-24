package cloud.grabsky.bedrock.helpers;

import cloud.grabsky.bedrock.util.Iterables;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import it.unimi.dsi.fastutil.Pair;
import lombok.AccessLevel;
import lombok.Getter;
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

import java.util.UUID;
import java.util.stream.Stream;

import static cloud.grabsky.bedrock.util.Iterables.merge;
import static cloud.grabsky.bedrock.util.Iterables.toList;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class ItemBuilder {

    private final ItemStack item;

    @Getter(AccessLevel.PUBLIC)
    private final ItemMeta meta;

    private static final UUID EMPTY_UUID = UUID.nameUUIDFromBytes(new byte[0]);

    public ItemBuilder(final @NotNull Material material, final int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(final @NotNull ItemStack item) {
        this.item = new ItemStack(item);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setName(final @NotNull Component name) {
        meta.displayName(name);
        return this;
    }

    public ItemBuilder setName(final @NotNull String name) {
        meta.displayName(miniMessage().deserialize(name));
        return this;
    }

    public ItemBuilder setLore(final String... lines) {
        meta.lore(
                Stream.of(lines)
                        .map(s -> miniMessage().deserialize(s))
                        .toList()
        );
        return this;
    }

    public ItemBuilder setLore(final @NotNull Component... lines) {
        meta.lore(toList(lines));
        return this;
    }

    public ItemBuilder addLore(final @NotNull Component... lines) {
        meta.lore(merge(meta.lore(), toList(lines)));
        return this;
    }

    public ItemBuilder addLore(final @NotNull String... lines) {
        meta.lore(merge(meta.lore(), toList(lines, (e) -> miniMessage().deserialize(e))));
        return this;
    }

    public ItemBuilder setCustomModelData(final int value) {
        meta.setCustomModelData(value);
        return this;
    }

    public ItemBuilder setItemFlags(final @NotNull ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        return this;
    }

    @SafeVarargs
    public final ItemBuilder setEnchantments(final @NotNull Pair<Enchantment, Integer>... enchantments) {
        for (final Pair<Enchantment, Integer> pair : enchantments) {
            meta.addEnchant(pair.first(), pair.second(), true);
        }
        return this;
    }

    public ItemBuilder addEnchantment(final @NotNull Enchantment enchantment, final int level) {
        meta.addEnchant(enchantment, level, true);
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

    public <T, Z> ItemBuilder setPersistentData(final NamespacedKey key, final PersistentDataType<T, Z> persistentDataType, final Z value) {
        meta.getPersistentDataContainer().set(key, persistentDataType, value);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}