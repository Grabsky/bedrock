package cloud.grabsky.bedrock.components;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextFormat;
import net.kyori.adventure.translation.Translatable;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComponentBuilder {

    private TextComponent.Builder internal;

    public static final Component EMPTY = Component.empty();
    public static final Component EMPTY_NO_ITALIC = Component.empty().decoration(TextDecoration.ITALIC, false);

    public static ComponentBuilder of(final @NotNull String text, final @NotNull TextFormat @Nullable ... format) {
        // Returning new instance of ComponentBuilder with no formatting.
        if (format == null)
            return new ComponentBuilder(Component.text().content(text));
        // Applying last specified text color.
        final @Nullable TextColor color = (TextColor) Stream.of(format).filter(e -> e instanceof TextColor).reduce((first, second) -> second).orElse(null);
        // Applying all text decorations.
        final TextDecoration[] decorations = Stream.of(format).filter(e -> e instanceof TextDecoration).toArray(TextDecoration[]::new);
        // Returning new instance of ComponentBuilder.
        return new ComponentBuilder(Component.text().content(text).decorate(decorations).color(color));
    }

    public static ComponentBuilder of(final @NotNull Component component) {
        // Returning new instance of ComponentBuilder.
        return new ComponentBuilder(Component.text().append(component));
    }

    public ComponentBuilder append(final @NotNull String text, final @NotNull TextFormat @Nullable ... format) {
        if (format != null && format.length > 0) {
            // Creating component with the provided TextFormat.
            internal.append(of(text, format).build());
            // Returning this instance.
            return this;
        }
        // Creating component with no TextFormat specified.
        internal.append(of(text).build());
        // Returning this instance.
        return this;
    }

    public ComponentBuilder append(final @NotNull Component component) {
        internal.append(component);
        // Returning this instance.
        return this;
    }

    public ComponentBuilder appendTranslation(final @NotNull Translatable translatable) {
        internal.append(Component.translatable(translatable.translationKey()));
        // Returning this instance.
        return this;
    }

    public ComponentBuilder appendTranslation(final @NotNull String translationKey) {
        internal.append(Component.translatable(translationKey));
        // Returning this instance.
        return this;
    }

    public ComponentBuilder color(final @NotNull TextColor color) {
        internal.color(color);
        // Returning this instance.
        return this;
    }

    public ComponentBuilder decoration(final @NotNull TextDecoration color, final boolean state) {
        internal.decoration(color, state);
        // Returning this instance.
        return this;
    }

    public Component build() {
        return internal.build();
    }

}
