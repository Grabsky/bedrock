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
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.valueOf;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed;

/**
 * {@link Message} class simplifies creation and sending of context-dependent messages of various types.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class Message<T> permits Message.StringMessage, Message.ComponentMessage {

    /**
     * Returns plain {@link String} currently held by this {@link Message} instance.
     */
    @Getter(AccessLevel.PUBLIC)
    protected @Nullable T message;

    /**
     * Sends message contained by (this) {@link Message} instance to provided {@link Audience}.
     */
    public abstract void send(final @NotNull Audience audience);

    /**
     * Sends message contained by (this) {@link Message} instance to all players.
     */
    public abstract void broadcast();


    public static Message<String> of(final @Nullable String message) {
        return new StringMessage(message);
    }

    public static final class StringMessage extends Message<String> {

        private final TagResolver.Builder resolverBuilder = TagResolver.builder();

        private StringMessage(final @Nullable String message) {
            super(message);
        }

        /**
         * Replaces all occurences of <b>{@code target}</b> with <b>{@code to}</b>.
         */
        public StringMessage replace(final @NotNull String target, final @NotNull String replacement) {
            if (message == null)
                return this;
            // ...
            this.message = message.replace(target, replacement);
            return this;
        }

        /**
         * Creates and adds {@link Placeholder} of <b>{@code name}</b> to be replaced with <b>{@code value}</b>.
         */
        public StringMessage placeholder(final @NotNull String name, final @NotNull String value) {
            resolverBuilder.resolver(unparsed(name, value));
            return this;
        }

        /**
         * Creates and adds {@link Placeholder} of <b>{@code name}</b> to be replaced with <b>{@code value}</b>.
         */
        public StringMessage placeholder(final @NotNull String name, final @NotNull Component value) {
            resolverBuilder.resolver(component(name, value));
            return this;
        }

        /**
         * Creates and adds {@link Placeholder} of <b>{@code name}</b> to be replaced with result of {@link Key#asString Key#asString} called on {@link Keyed#key Keyed#key}.
         */
        // TO-DO: Replace with enhanced swtich once it leaves preview.
        public StringMessage placeholder(final @NotNull String name, final @NotNull Keyed value) {
            resolverBuilder.resolver(unparsed(name, value.key().asString()));
            return this;
        }

        /**
         * Creates and adds {@link Placeholder} of <b>{@code name}</b> to be replaced with result of {@link Player#getName Player#getName} called on <b>{@code value}</b>.
         */
        // TO-DO: Replace with enhanced swtich once it leaves preview.
        public StringMessage placeholder(final @NotNull String name, final @NotNull Player value) {
            resolverBuilder.resolver(unparsed(name, value.getName()));
            return this;
        }

        /**
         * Creates and adds {@link Placeholder} of <b>{@code name}</b> to be replaced with result of {@link Object#toString Object#toString} called on <b>{@code value}</b>.
         */
        // TO-DO: Replace with enhanced swtich once it leaves preview.
        public StringMessage placeholder(final @NotNull String name, final @NotNull Object value) {
            resolverBuilder.resolver(unparsed(name, valueOf(value)));
            return this;
        }

        /**
         * Adds array of {@link TagResolver} to this {@link Message}.
         */
        public StringMessage resolvers(final @NotNull TagResolver... resolvers) {
            resolverBuilder.resolvers(resolvers);
            return this;
        }

        @Override
        public void send(final @NotNull Audience audience) {
            // Ignoring empty/blank messages.
            if (message == null || ("").equals(message) == true)
                return;
            // Parsing using MiniMessage instance provided by GlobalComponentSerializer.
            final Component component = GlobalComponentSerializer.get().deserialize(message, resolverBuilder.build());
            // Ignoring empty/blank messages.
            if (Component.empty().equals(component) == true)
                return;
            // Sending message to the provided audience.
            audience.sendMessage(component);
        }

        @Override
        public void broadcast() {
            // Ignoring empty/blank messages.
            if (message == null || ("").equals(message) == true)
                return;
            // Parsing using MiniMessage instance provided by GlobalComponentSerializer.
            final Component component = GlobalComponentSerializer.get().deserialize(message, resolverBuilder.build());
            // Ignoring empty/blank messages.
            if (Component.empty().equals(component) == true)
                return;
            // Broadcasting message to the server.
            Bukkit.broadcast(component);
        }

    }


    public static Message<Component> of(final @Nullable Component message) {
        return new ComponentMessage(message);
    }

    public static final class ComponentMessage extends Message<Component> {

        private ComponentMessage(final @Nullable Component message) {
            super(message);
        }

        @Override
        public void send(final @NotNull Audience audience) {
            // Ignoring empty/blank messages.
            if (message == null || Component.empty().equals(message) == true)
                return;
            // Sending message to the provided audience.
            audience.sendMessage(message);
        }

        @Override
        public void broadcast() {
            // Ignoring empty/blank messages.
            if (message == null || Component.empty().equals(message) == true)
                return;
            // Broadcasting message to the server.
            Bukkit.broadcast(message);
        }

    }

}
