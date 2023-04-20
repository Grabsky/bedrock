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
package cloud.grabsky.bedrock.inventory;

import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Panel implements InventoryHolder {

    public interface ClickAction extends Consumer<InventoryClickEvent> { /* EMPTY */

    }

    @Getter(AccessLevel.PUBLIC)
    private final Inventory inventory; // @Getter creates override for InventoryHolder#getInventory.

    @Getter(AccessLevel.PUBLIC)
    private final Map<Integer, ClickAction> actions;

    @Getter(AccessLevel.PUBLIC)
    private final ClickAction clickAction;

    public Panel(final @NotNull Component title, final @Range(from = 9, to = 54) int size, final @Nullable ClickAction onClickShared) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.actions = new HashMap<>(size);
        this.clickAction = onClickShared;
    }

    public @NotNull Panel setItem(final int slot, final @Nullable ItemStack item, final @Nullable ClickAction onClick) {
        inventory.setItem(slot, item);
        actions.put(slot, onClick);
        return this;
    }

    public @NotNull Panel removeItem(final int slot) {
        inventory.setItem(slot, null);
        actions.remove(slot);
        return this;
    }

    public @NotNull Panel applyTemplate(final @NotNull Consumer<Panel> template, final boolean clearCurrent) {
        // Clearing inventory before applying template (if requested)
        if (clearCurrent == true)
            inventory.clear();
        // Applying the template.
        template.accept(this);
        return this;
    }

    public @NotNull Panel clear() {
        inventory.clear();
        actions.clear();
        return this;
    }

    public @NotNull Panel open(final @NotNull HumanEntity human, final @Nullable Predicate<Panel> onPreOpen) {
        // Cancelling if PreOpenAction returns 'false'
        if (onPreOpen != null && onPreOpen.test(this) == false)
            return this;
        // Opening inventory to the HumanEntity
        human.openInventory(inventory);
        return this;
    }

    public @NotNull Panel close() {
        inventory.close();
        return this;
    }

    public static void registerListener(final @NotNull Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {

            private final HashMap<UUID, Long> cooldowns = new HashMap<>();

            @EventHandler
            public void onPanelInventoryClick(final @NotNull InventoryClickEvent event) {
                // Ignoring clicks outside inventory
                if (event.getClickedInventory() == null)
                    return;
                // Ignoring non-panel inventories
                if (event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof final Panel panel) {
                    // Since we're dealing with a custom inventory, cancelling the event is necessary
                    event.setCancelled(true);
                    // Ignoring clicks outside of the Panel inventory
                    if (event.getClickedInventory().getHolder() != panel)
                        return;
                    // Returning if no acction is associated with clicked slot
                    if (panel.getActions().get(event.getSlot()) == null)
                        return;
                    // Handling cooldown...
                    final long lastClick = cooldowns.getOrDefault(event.getWhoClicked().getUniqueId(), 0L);
                    if (lastClick != 0L && (System.currentTimeMillis() - lastClick) < 150L)
                        return;
                    // Updating cooldown...
                    cooldowns.put(event.getWhoClicked().getUniqueId(), System.currentTimeMillis());
                    // PLaying sound if allowed
                    if (panel.getClickAction() != null) {
                        panel.getClickAction().accept(event);
                    }
                    // Executing action associated with clicked slot (aka clicked item)
                    panel.getActions().get(event.getSlot()).accept(event);
                }
            }

        }, plugin);
    }

}