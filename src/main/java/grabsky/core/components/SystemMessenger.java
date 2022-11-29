package grabsky.core.components;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

/**
 * Utility class containing quality-of-life methods for sending messages.
 * All messages sent using methods defined within that class, are <b>SYSTEM</b>
 *
 * @see <a href=https://docs.adventure.kyori.net/minimessage/format.html>MiniMessage Documentation</a>
 * @see <a href=https://gist.github.com/kennytv/ed783dd244ca0321bbd882c347892874>Chat Signing Overview</a> by <a href=https://github.com/kennytv>kennytv</a>
 */
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
