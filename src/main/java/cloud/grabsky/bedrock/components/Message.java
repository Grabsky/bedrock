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

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public sealed class Message<T> permits Message.StringMessage, Message.ComponentMessage {

    @Getter(AccessLevel.PUBLIC)
    protected @Nullable T message;

    public static StringMessage of(final @Nullable String message) {
        return new StringMessage(message);
    }

    public static ComponentMessage of(final @Nullable Component message) {
        return new ComponentMessage(message);
    }

    // For (String) messages that requires deserialization.
    public static final class StringMessage extends Message<String> {

        private final TagResolver.Builder resolverBuilder = TagResolver.builder();

        private StringMessage(final @Nullable String message) {
            super(message);
        }

        /**
         * Replaces all occurences of <b>{@code target}</b> with <b>{@code to}</b>.
         */
        public StringMessage replace(final @NotNull String target, final @Nullable String replacement) {
            if (message == null)
                return this;
            // ...
            this.message = this.message.replace(target, (replacement != null) ? replacement : "");
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
         * Creates and adds {@link Placeholder} of <b>{@code name}</b> to be replaced with result of {@link Key#asString Key#asString} called on <b>{@code value}</b>.
         */
        // TO-DO: Replace with enhanced swtich once it leaves preview.
        public StringMessage placeholder(final @NotNull String name, final @NotNull Key value) {
            resolverBuilder.resolver(unparsed(name, value.asString()));
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

        /**
         * Parses using {@link GlobalComponentSerializer}, and sends this {@link Message} to {@link Player}. Empty or {@code null} messages are not sent.
         */
        public void send(final @NotNull Audience sender) {
            if (message == null || "".equals(message) == true)
                return;
            // ...
            final Component component = GlobalComponentSerializer.get().deserialize(message, resolverBuilder.build());
            // ...
            if (component == Component.empty())
                return;
            // ...
            sender.sendMessage(component);
        }

        /**
         * Parses using {@link GlobalComponentSerializer}, and sends this {@link Message} to all players. Empty or {@code null} messages are not sent.
         */
        public void broadcast() {
            if (message == null || "".equals(message) == true)
                return;
            // ...
            final Component component = GlobalComponentSerializer.get().deserialize(message, resolverBuilder.build());
            // ...
            if (component == Component.empty())
                return;
            // ...
            Bukkit.broadcast(component);
        }

    }

    // For already deserialized (Component) messages.
    public static final class ComponentMessage extends Message<Component> {

        public ComponentMessage(final @Nullable Component message) {
            super(message);
        }

        /**
         * Parses using {@link GlobalComponentSerializer}, and sends this {@link Message} to {@link Player}. Empty or {@code null} messages are not sent.
         */
        public void send(final @NotNull Audience sender) {
            if (message == null || message == Component.empty())
                return;
            // ...
            sender.sendMessage(message);
        }

        /**
         * Parses using {@link GlobalComponentSerializer}, and sends this {@link Message} to all players. Empty or {@code null} messages are not sent.
         */
        public void broadcast() {
            if (message == null || message == Component.empty())
                return;
            // ...
            Bukkit.broadcast(message);
        }

    }

}
