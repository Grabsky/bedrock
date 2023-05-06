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
            final @Range(from = 0, to = 6) int rows,
            final @NotNull Consumer<InventoryOpenEvent> onInventoryOpen,
            final @NotNull Consumer<InventoryCloseEvent> onInventoryClose,
            final @NotNull Consumer<InventoryClickEvent> onInventoryClick
    ) {
        super(title, type, rows, onInventoryOpen, onInventoryClose, onInventoryClick);
    }

    public final static class Builder extends Panel.Builder<BedrockPanel> {

        @Override
        protected @NotNull Builder self() {
            return this;
        }

        @Override
        public @NotNull BedrockPanel build() {
            return new BedrockPanel(this.title, this.inventoryType, this.rows, this.onInventoryOpen, this.onInventoryClose, this.onInventoryClick);
        }

    }
}
