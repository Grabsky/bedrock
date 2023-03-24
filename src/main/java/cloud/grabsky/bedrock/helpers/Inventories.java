package cloud.grabsky.bedrock.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.NotNull;

import static org.jetbrains.annotations.ApiStatus.Internal;

@Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Inventories {

    /**
     * Returns {@code true} if both items are similar, compared by type, display name and lore.
     */
    public static boolean isSimilar(final @NotNull ItemStack first, final @NotNull ItemStack second) {
        final ItemMeta metaFirst = first.getItemMeta();
        final ItemMeta metaSecond = second.getItemMeta();
        return first.getType() == second.getType()
                && (metaFirst.hasDisplayName() == false && metaSecond.hasDisplayName() == false) || metaFirst.displayName().equals(metaSecond.displayName()) == true
                && (metaFirst.hasLore() == false && metaSecond.hasLore() == false) || metaFirst.lore().equals(metaSecond.lore()) == true;
    }

    @Experimental
    public static void removeSimilarItems(final @NotNull Player player, final @NotNull ItemStack... items) {
        final int[] leftToRemove = new int[items.length];
        // Preparing list of items to remove
        for (int i = 0; i < items.length; i++) {
            leftToRemove[i] = items[i].getAmount();
        }
        // Iterating over all items inside player's inventory
        for (int i = 0; i < items.length; i++) {
            if (leftToRemove[i] == 0) continue;
            for (final ItemStack inventoryItem : player.getInventory().getStorageContents()) {
                if (inventoryItem != null && isSimilar(inventoryItem, items[i]) == true) {
                    int a = Math.min(inventoryItem.getAmount(), leftToRemove[i]);
                    leftToRemove[i] -= a;
                    inventoryItem.setAmount(inventoryItem.getAmount() - a);
                }
            }
        }
    }

    @Experimental
    public static boolean hasSimilarItems(final @NotNull Player player, final ItemStack... items) {
        final int[] conditions = new int[items.length];
        for (final ItemStack inventoryItem : player.getInventory().getStorageContents()) {
            for (int i = 0; i < items.length; i++) {
                if (inventoryItem != null && inventoryItem.isSimilar(items[i])) {
                    conditions[i] += inventoryItem.getAmount();
                }
            }
        }
        for (int i = 0; i < conditions.length; i++) {
            if (conditions[i] < items[i].getAmount()) return false;
        }
        return true;
    }

}
