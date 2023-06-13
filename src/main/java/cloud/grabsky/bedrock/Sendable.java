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
package cloud.grabsky.bedrock;

import cloud.grabsky.bedrock.components.Message;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface Sendable {

    /**
     * Sends message contained by (this) {@link Message} instance to provided {@link Audience}.
     */
    void send(final @NotNull Audience audience);

    /**
     * Sends message contained by (this) {@link Message} instance to provided {@link Audience}.
     */
    void sendTitle(final @NotNull Audience audience, final long fadeIn, final long duration, final long fadeOut);

    /**
     * Sends message contained by (this) {@link Message} instance to provided {@link Audience}.
     */
    void sendSubtitle(final @NotNull Audience audience, final long fadeIn, final long duration, final long fadeOut);

    /**
     * Sends message contained by (this) {@link Message} instance to provided {@link Audience}.
     */
    void sendActionBar(final @NotNull Audience audience);

    /**
     * Sends message contained by (this) {@link Message} instance to all players matching provided {@link Predicate}.
     */
    void broadcast(final @NotNull Predicate<Player> predicate);

    /**
     * Sends message contained by (this) {@link Message} instance to all players.
     */
    void broadcast();

}
