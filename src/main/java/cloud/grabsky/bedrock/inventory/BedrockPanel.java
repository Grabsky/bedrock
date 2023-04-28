package cloud.grabsky.bedrock.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.function.Consumer;

public final class BedrockPanel extends Panel {

    private BedrockPanel(
            final @NotNull Component title,
            final @NotNull InventoryType type,
            final @Range(from = 0, to = 5) int rows,
            final @NotNull Consumer<InventoryOpenEvent> onInventoryOpen,
            final @NotNull Consumer<InventoryCloseEvent> onInventoryClose,
            final @NotNull Consumer<InventoryClickEvent> onInventoryClick
    ) {
        super(title, type, rows, onInventoryOpen, onInventoryClose, onInventoryClick);
    }

    public final static class Builder extends Panel.Builder<BedrockPanel> {

        @Override
        public Panel.Builder<BedrockPanel> self() {
            return this;
        }

        @Override
        public BedrockPanel build() {
            return new BedrockPanel(
                    this.getTitle(),
                    this.getInventoryType(),
                    this.getRows(),
                    this.getInventoryOpenAction(),
                    this.getInventoryCloseAction(),
                    this.getInventoryClickAction()
            );
        }
    }
}
