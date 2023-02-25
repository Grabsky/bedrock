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
package cloud.grabsky.bedrock.components;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

/**
 * Utility class containing quality-of-life methods for sending messages.
 * All messages sent using methods defined within that class, are <b>SYSTEM</b> type.
 *
 * @see <a href=https://docs.advntr.dev/minimessage/index.html>MiniMessage Documentation</a>
 * @see <a href=https://gist.github.com/kennytv/ed783dd244ca0321bbd882c347892874>Chat Signing Overview</a> by <a href=https://github.com/kennytv>kennytv</a>
 */
// TO-DO: Address deprecated calls.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SystemMessenger {

    /**
     * Sends provided {@link String} {@code (message)}, serialized using {@link GlobalComponentSerializer#get()},
     * to specific {@link Audience} {@code (audience)}. Empty {@code ("")} or {@code null} messages are ignored.
     */
    public static void sendMessage(final Audience audience, final String message) {
        if (message != null && "".equals(message) == false)
            audience.sendMessage(GlobalComponentSerializer.get().deserialize(message), MessageType.SYSTEM);
    }

    /**
     * Sends provided {@link String} {@code (message)}, serialized using {@link GlobalComponentSerializer#get()},
     * to specific {@link Audience} {@code (audience)}. Empty {@code ("")} or {@code null} messages are ignored.
     */
    public static void sendMessage(final Audience audience, final String message, final TagResolver... resolvers) {
        if (message != null && "".equals(message) == false)
            audience.sendMessage(GlobalComponentSerializer.get().deserialize(message, resolvers), MessageType.SYSTEM);
    }

    /**
     * Sends provided {@link String} {@code (message)}, serialized using {@link GlobalComponentSerializer#get()},
     * to specific {@link Audience} {@code (audience)} as {@link Identity} {@code (identity)}.
     * Empty {@code ("")} or {@code null} messages are ignored.
     */
    public static void sendMessage(final Audience sender, final Identity identity, final String message) {
        if (message != null && "".equals(message) == false)
            sender.sendMessage(identity, GlobalComponentSerializer.get().deserialize(message), MessageType.SYSTEM);
    }

    /**
     * Sends provided {@link String} {@code (message)}, serialized using {@link GlobalComponentSerializer#get()},
     * to specific {@link Audience} {@code (audience)} as {@link Identity} {@code (identity)}.
     * Empty {@code ("")} or {@code null} messages are ignored.
     */
    public static void sendMessage(final Audience sender, final Identity identity, final String message, final TagResolver... resolvers) {
        if (message != null && "".equals(message) == false)
            sender.sendMessage(identity, GlobalComponentSerializer.get().deserialize(message, resolvers), MessageType.SYSTEM);
    }

    /**
     * Sends provided {@link Component} {@code (message)} to specific {@link Audience} {@code (audience)}.
     */
    public static void sendMessage(final Audience audience, final Component message) {
        if (message != null && message != Component.empty())
            audience.sendMessage(message, MessageType.SYSTEM);
    }

    /**
     * Sends provided {@link Component} {@code (message)} to specific {@link Audience} {@code (audience)} as {@link Identity} {@code (identity)}.
     */
    public static void sendMessage(final Audience audience, final Identity identity, final Component message) {
        if (message != null && message != Component.empty())
            audience.sendMessage(identity, message, MessageType.SYSTEM);
    }

}
