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

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class BedrockScheduler {

    @Getter(AccessLevel.PUBLIC)
    private final @NotNull BedrockPlugin bedrockPlugin;

    /**
     * Schedules a synchronous task to run after {@code delay} of ticks has passed.
     *
     * @param delay ticks to wait before executing the task
     * @param task a task
     */
    public void run(final long delay, final @NotNull Consumer<BukkitTask> task) {
        bedrockPlugin.getServer().getScheduler().runTaskLater(bedrockPlugin, task, delay);
    }

    /**
     * Schedules an asynchronous task to run after {@code delay} of ticks has passed.
     *
     * @param delay ticks to wait before executing the task
     */
    public void runAsync(final long delay, final @NotNull Consumer<BukkitTask> task) {
        bedrockPlugin.getServer().getScheduler().runTaskLaterAsynchronously(bedrockPlugin, task, delay);
    }

    /**
     * Schedules a synchronous task to run repeatedly until iterations / cycles limit {@code (cycles)} is reached.
     * Returning {@code false} inside the {@link Predicate} {@code (task)} cancels execution of next iteration(s).
     *
     * @param delay ticks to wait before starting the task
     * @param period ticks to wait in-between tasks
     * @param cycles max iterations
     */
    public @NotNull BukkitTask repeat(final long delay, final long period, final long cycles, final @NotNull Predicate<Integer> task) {
        return new BukkitRunnable() {
            int cycle = 1;

            @Override
            public void run() {
                // Executing the code and cancelling the task if 'false' is returned.
                if (task.test(cycle) == false)
                    this.cancel();
                // Cancelling the task if iterations limit is reached.
                if (cycle++ > cycles)
                    this.cancel();
            }

        }.runTaskTimer(bedrockPlugin, delay, period);
    }

    /**
     * Schedules a synchronous task to run repeatedly until iterations / cycles limit {@code (cycles)} is reached.
     * Returning {@code false} inside the {@link BiPredicate} {@code (task)} cancels execution of next iteration(s).
     *
     * @param delay ticks to wait before starting the task
     * @param period ticks to wait in-between tasks
     * @param cycles max iterations
     */
    public @NotNull BukkitTask repeat(final long delay, final long period, final long cycles, final @NotNull BiPredicate<BukkitRunnable, Integer> task) {
        return new BukkitRunnable() {
            int cycle = 1;

            @Override
            public void run() {
                // Executing the code and cancelling the task if 'false' is returned.
                if (task.test(this, cycle) == false)
                    this.cancel();
                // Cancelling the task if iterations limit is reached.
                if (cycle++ > cycles)
                    this.cancel();
            }

        }.runTaskTimer(bedrockPlugin, delay, period);
    }

    /**
     * Schedules an asynchronous task to run repeatedly until iterations / cycles limit {@code (cycles)} is reached.
     * Returning {@code false} inside the {@link Predicate} {@code (task)} cancels execution of next iteration(s).
     *
     * @param delay ticks to wait before starting the task
     * @param period ticks to wait in-between tasks
     * @param cycles max iterations
     */
    public @NotNull BukkitTask repeatAsync(final long delay, final long period, final long cycles, final @NotNull Predicate<Integer> task) {
        return new BukkitRunnable() {
            int cycle = 1;

            @Override
            public void run() {
                // Executing the code and cancelling the task if 'false' is returned.
                if (task.test(cycle) == false)
                    this.cancel();
                // Cancelling the task if iterations limit is reached.
                if (cycle++ > cycles)
                    this.cancel();
            }

        }.runTaskTimerAsynchronously(bedrockPlugin, delay, period);
    }

    /**
     * Schedules an asynchronous task to run repeatedly until iterations / cycles limit {@code (cycles)} is reached.
     * Returning {@code false} inside the {@link BiPredicate} {@code (task)} cancels execution of next iteration(s).
     *
     * @param delay ticks to wait before starting the task
     * @param period ticks to wait in-between tasks
     * @param cycles max iterations
     */
    public @NotNull BukkitTask repeatAsync(final long delay, final long period, final long cycles, final @NotNull BiPredicate<BukkitRunnable, Integer> task) {
        return new BukkitRunnable() {
            int cycle = 1;

            @Override
            public void run() {
                // Executing the code and cancelling the task if 'false' is returned.
                if (task.test(this, cycle) == false)
                    this.cancel();
                // Cancelling the task if iterations limit is reached.
                if (cycle++ > cycles)
                    this.cancel();
            }

        }.runTaskTimerAsynchronously(bedrockPlugin, delay, period);
    }

}
