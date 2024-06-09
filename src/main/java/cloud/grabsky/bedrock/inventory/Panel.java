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

import cloud.grabsky.bedrock.util.Interval;
import cloud.grabsky.bedrock.util.Interval.Unit;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import static java.lang.System.currentTimeMillis;

public abstract class Panel implements InventoryHolder {

    public interface ClickAction extends Consumer<InventoryClickEvent> { /* EMPTY */ }

    private static final Consumer<?> EMPTY_CONSUMER = (it) -> { /* EMPTY */ };

    // @Getter creates override for InventoryHolder#getInventory.
    @Getter(value = AccessLevel.PUBLIC)
    protected final @NotNull Inventory inventory;

    protected final @NotNull Consumer<InventoryOpenEvent> onInventoryOpen;
    protected final @NotNull Consumer<InventoryCloseEvent> onInventoryClose;
    protected final @NotNull Consumer<InventoryClickEvent> onInventoryClick;

    protected final @NotNull Map<@NotNull Integer, @Nullable ClickAction> actions;

    protected Panel(
            final @NotNull Component title,
            final @NotNull InventoryType type,
            final @Range(from = 0, to = 5) int rows,
            final @NotNull Consumer<InventoryOpenEvent> onInventoryOpen,
            final @NotNull Consumer<InventoryCloseEvent> onInventoryClose,
            final @NotNull Consumer<InventoryClickEvent> onInventoryClick
    ) {
        this.inventory = (type == InventoryType.CHEST && rows > 0)
                ? Bukkit.createInventory(this, rows * 9, title)
                : Bukkit.createInventory(this, type, title);
        // ...
        this.onInventoryOpen = onInventoryOpen;
        this.onInventoryClose = onInventoryClose;
        this.onInventoryClick = onInventoryClick;
        // ...
        this.actions = new HashMap<>();
    }

    @Accessors(chain = true)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public abstract static class Builder<T extends Panel> {

        protected Component title = InventoryType.CHEST.defaultTitle();

        public @NotNull Builder<T> setTitle(final @NotNull Component title) {
            this.title = title; return this.self();
        }

        protected InventoryType inventoryType = InventoryType.CHEST;

        public @NotNull Builder<T> setInventoryType(final @NotNull InventoryType type) {
            this.inventoryType = type; return this.self();
        }

        protected int rows = 0;

        public @NotNull Builder<T> setRows(final @Range(from = 0, to = 6) int rows) {
            this.rows = rows;
            return this.self();
        }

        protected Consumer<InventoryOpenEvent> onInventoryOpen = (Consumer) EMPTY_CONSUMER;

        public @NotNull Builder<T> setInventoryOpenAction(final @NotNull Consumer<InventoryOpenEvent> onInventoryOpen) {
            this.onInventoryOpen = onInventoryOpen;
            return this.self();
        }

        protected Consumer<InventoryCloseEvent> onInventoryClose = (Consumer) EMPTY_CONSUMER;

        public @NotNull Builder<T> setInventoryCloseAction(final @NotNull Consumer<InventoryCloseEvent> onInventoryClose) {
            this.onInventoryClose = onInventoryClose;
            return this.self();
        }

        protected Consumer<InventoryClickEvent> onInventoryClick = (Consumer) EMPTY_CONSUMER;

        public @NotNull Builder<T> setInventoryClickAction(final @NotNull Consumer<InventoryClickEvent> onInventoryClick) {
            this.onInventoryClick = onInventoryClick;
            return this.self();
        }

        protected abstract @NotNull Builder<T> self();

        public abstract @NotNull T build();

    }

    /**
     * Sets contents of specified slot to given {@link ItemStack}. Optionally {@link ClickAction ClickAction} can be specified.
     */
    public void setItem(final int slot, final @Nullable ItemStack item, final @Nullable ClickAction onClick) {
        inventory.setItem(slot, item);
        actions.put(slot, onClick);
    }

    /**
     * Removes contents of specified slot and associated {@link ClickAction}.
     */
    public void removeItem(final int slot) {
        inventory.setItem(slot, null);
        actions.remove(slot);
    }

    /**
     * Applies specified template on (this) {@link Panel}.
     */
    public void applyTemplate(final @NotNull Consumer<Panel> template, final boolean clearCurrent) {
        // Clearing inventory before applying template (if requested)
        if (clearCurrent == true)
            this.clear();
        // Applying the template.
        template.accept(this);
    }

    /**
     * Clears contents of (this) {@link Panel} including associated click actions.
     */
    public void clear() {
        inventory.clear();
        actions.clear();
    }

    /**
     * Opens (this) {@link Panel} to specific {@link HumanEntity}.
     */
    public void open(final @NotNull HumanEntity human, final @Nullable Predicate<Panel> onPreInventoryOpen) {
        // Cancelling if PreOpenAction returns 'false'
        if (onPreInventoryOpen != null && onPreInventoryOpen.test(this) == false)
            return;
        // Opening inventory to the HumanEntity
        human.openInventory(inventory);
    }

    /**
     * Closes (this) {@link Panel} for all viewers.
     */
    public void close() {
        inventory.close();
    }

    /**
     * Registers inventory listeners to the server. This method must be called in order to make interacting with panels possible.
     */
    public static void registerDefaultListeners(final @NotNull Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(PanelListener.INSTANCE, plugin);
    }

    private enum PanelListener implements Listener {
        /* SINGLETON */ INSTANCE;

        private final HashMap<UUID, Long> cooldowns = new HashMap<>();

        @EventHandler(ignoreCancelled = true)
        public void onPanelInventoryClick(final @NotNull InventoryClickEvent event) {
            // Ignoring clicks outside the inventory.
            if (event.getClickedInventory() == null)
                return;
            // Ignoring non-panel inventories.
            if (event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof final Panel panel) {
                // Cancelling the event to prevent moving items between slots or inventories.
                event.setCancelled(true);
                // Ignoring clicks outside of the Panel inventory.
                if (event.getClickedInventory().getHolder() != panel)
                    return;
                // ...
                final ClickAction onClick = panel.actions.get(event.getSlot());
                // Returning in case no action has been assigned to the clicked slot.
                if (onClick == null)
                    return;
                // Handling cooldown...
                if (Interval.between(currentTimeMillis(), cooldowns.getOrDefault(event.getWhoClicked().getUniqueId(), 0L), Unit.MILLISECONDS).as(Unit.MILLISECONDS) < 200L)
                    return;
                // ...setting cooldown
                cooldowns.put(event.getWhoClicked().getUniqueId(), currentTimeMillis());
                // Executing click actions. Wrapped with a try-catch block to make sure inventory is closed in case of an error.
                try {
                    // Executing "shared" click action.
                    panel.onInventoryClick.accept(event);
                    // Executing slot-specific click action.
                    onClick.accept(event);
                } catch (final Exception e) {
                    e.printStackTrace();
                    panel.close();
                }
            }
        }

        @EventHandler(ignoreCancelled = true)
        public void onPanelInventoryOpen(final @NotNull InventoryOpenEvent event) {
            if (event.getInventory().getHolder() instanceof final Panel panel)
                panel.onInventoryOpen.accept(event);
        }

        @EventHandler(ignoreCancelled = true)
        public void onPanelInventoryClose(final @NotNull InventoryCloseEvent event) {
            if (event.getInventory().getHolder() instanceof final Panel panel)
                panel.onInventoryClose.accept(event);
        }

    }

}