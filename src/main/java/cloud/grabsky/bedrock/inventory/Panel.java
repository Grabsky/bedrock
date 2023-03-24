package cloud.grabsky.bedrock.inventory;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Panel implements InventoryHolder {

    public interface ClickAction extends Consumer<InventoryClickEvent> { /* EMPTY */ }

    @Getter private final Inventory inventory; // Overrides `InventoryHolder#getInventory`
    @Getter private final Map<Integer, ClickAction> actions;
    @Getter private final ClickAction clickAction;

    public Panel(@NotNull final Component title, final int size, final ClickAction onClickCommon) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.actions = new HashMap<>(size);
        this.clickAction = onClickCommon;
    }

    public Panel setItem(final int slot, @Nullable final ItemStack item, @Nullable final ClickAction onClick) {
        inventory.setItem(slot, item);
        actions.put(slot, onClick);
        // ...
        return this;
    }

    public Panel removeItem(final int slot) {
        inventory.setItem(slot, null);
        actions.remove(slot);
        // ...
        return this;
    }

    public Panel applyTemplate(final Consumer<Panel> template, final boolean clearCurrent) {
        if (clearCurrent == true) {
            inventory.clear();
        }
        // Applying the view
        template.accept(this);
        // ...
        return this;
    }

    public Panel clear() {
        inventory.clear();
        actions.clear();
        // ...
        return this;
    }

    public Panel open(final HumanEntity human, @Nullable final Predicate<Panel> onPreOpen) {
        // Cancelling if PreOpenAction returns 'false'
        if (onPreOpen != null && onPreOpen.test(this) == false)
            return this;
        // Opening inventory to the HumanEntity
        human.openInventory(inventory);
        // ...
        return this;
    }

    public Panel close() {
        inventory.close();
        // ...
        return this;
    }

    public static void registerListener(final Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {

            private final HashMap<UUID, Long> cooldowns = new HashMap<>(); // TO-DO: Name it better.

            @EventHandler
            public void onPanelInventoryClick(final InventoryClickEvent event) {
                // Ignoring clicks outside inventory
                if (event.getClickedInventory() == null) return;
                // Ignoring non-panel inventories
                if (event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof final Panel panel) {
                    // Since we're dealing with a custom inventory, cancelling the event is necessary
                    event.setCancelled(true);
                    // Ignoring clicks outside of the Panel inventory
                    if (event.getClickedInventory().getHolder() != panel) return;
                    // Returning if no acction is associated with clicked slot
                    if (panel.getActions().get(event.getSlot()) == null) return;
                    // Handling cooldown
                    final long lastClick = cooldowns.getOrDefault(event.getWhoClicked().getUniqueId(), 0L);
                    if (lastClick != 0L && (System.currentTimeMillis() - lastClick) < 150L) return;
                    // Updating cooldown
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