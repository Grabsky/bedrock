package grabsky.core.components;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

import java.util.function.Function;

/**
 * {@link GlobalComponentSerializer} is a {@link MiniMessage} wrapper which provides
 * a way to 'modify' the underlaying {@link MiniMessage} instance by other plugins.
 */
public final class GlobalComponentSerializer {

    private static MiniMessage.Builder builder = MiniMessage.builder()
            .editTags((adder) -> {
                adder.resolver(StandardTags.color());
                adder.resolver(StandardTags.decorations());
                adder.resolver(StandardTags.keybind());
                adder.resolver(StandardTags.newline());
                adder.resolver(StandardTags.reset());
                adder.resolver(StandardTags.selector());
                adder.resolver(StandardTags.clickEvent());
                adder.resolver(StandardTags.hoverEvent());
            });

    /**
     * Returns currently held instance of {@link MiniMessage}.
     */
    @Accessors(fluent = true)
    @Getter(AccessLevel.PUBLIC)
    private static MiniMessage get = builder.build();

    /**
     * Applies modifcations to private {@link MiniMessage.Builder} and re-builds
     * underlaying {@link MiniMessage} instance accessible by {@link GlobalComponentSerializer#get()}
     */
    public static void inject(final Function<MiniMessage.Builder, MiniMessage.Builder> function) {
        // applying modifications on the current builder...
        builder = function.apply(builder);
        // rebuilding...
        get = builder.build();
    }

}
