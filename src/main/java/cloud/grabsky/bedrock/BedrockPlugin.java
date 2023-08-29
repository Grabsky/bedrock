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

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * {@link BedrockPlugin} provides enhanced features for plugins extending it.
 */
public abstract class BedrockPlugin extends JavaPlugin {

    @Getter(AccessLevel.PUBLIC)
    protected BedrockScheduler bedrockScheduler;

    @Override
    public void onEnable() {
        this.bedrockScheduler = new BedrockScheduler(this);
    }

    /**
     * Defines "safe" plugin reload logic that can be called manually.
     *
     * @apiNote This method <b><u>is not</u></b> called on server start-up or reload by default.
     */
    public abstract boolean onReload();

    /**
     * Returns {@code true} if {@code BEDROCK_DEBUGGING} environment variable is set to {@code 1}.
     */
    public boolean isDebugEnabled() {
        return "1".equalsIgnoreCase(System.getenv("BEDROCK_DEBUGGING"));
    }

}
