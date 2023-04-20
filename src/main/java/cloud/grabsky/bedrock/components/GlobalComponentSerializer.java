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
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * {@link GlobalComponentSerializer} is a {@link MiniMessage} wrapper which provides
 * a way to 'modify' the underlaying {@link MiniMessage} instance by other plugins.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalComponentSerializer {

    private static MiniMessage instance = MiniMessage.miniMessage();

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
    public static @NotNull MiniMessage get() {
        return instance;
    }

    /**
     * Applies modifications to private {@link MiniMessage.Builder} and re-builds
     * underlying {@link MiniMessage} instance accessible by {@link GlobalComponentSerializer#get()}
     */
    public static void inject(final @NotNull Function<MiniMessage.Builder, MiniMessage.Builder> function) {
        // applying modifications on the current builder...
        builder = function.apply(builder);
        // rebuilding...
        instance = builder.build();
    }

}
