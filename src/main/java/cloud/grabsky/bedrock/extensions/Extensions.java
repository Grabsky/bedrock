package cloud.grabsky.bedrock.extensions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Extensions for use with Lombok's {@link lombok.experimental.ExtensionMethod @ExtensionMethod} annotation.
 */
// NOTE: Only the most verbose and annoying methods are added here. Everything is subject to change.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Extensions {

    /**
     * Returns plain text content of this {@link Component}.
     */
    public static @NotNull String content(final @NotNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

}
