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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Inventories {

    /**
     * Returns {@code true} if both items are similar, compared by type, display name, lore and custom model data.
     */
    public static boolean isSimilar(final @NotNull ItemStack first, final @NotNull ItemStack second) {
        final ItemMeta metaFirst = first.getItemMeta();
        final ItemMeta metaSecond = second.getItemMeta();
        // Comparing by types...
        if (first.getType() != second.getType())
            return false;
        // Comparing display names...
        if (metaFirst.hasDisplayName() == true && metaSecond.hasDisplayName() == true && metaFirst.displayName().equals(metaSecond.displayName()) == false)
            return false;
        // Comparing lore...
        if (metaFirst.hasLore() == true && metaSecond.hasLore() == true && metaFirst.lore().equals(metaSecond.lore()) == false)
            return false;
        // Comparing custom model data...
        if (metaFirst.hasCustomModelData() == true && metaSecond.hasCustomModelData() == true && metaFirst.getCustomModelData() != metaSecond.getCustomModelData())
            return false;
        // ...
        return true;
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
    public static boolean hasSimilarItems(final @NotNull Player player, final @NotNull ItemStack... items) {
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
