package grabsky.core;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CoreScheduler {

    private final CorePlugin corePlugin;

    /**
     * Schedules a synchronous task to run after {@code delay} of ticks has passed.
     *
     * @param delay ticks to wait before executing the task
     * @param task a task
     */
    public void run(final long delay, final Consumer<BukkitTask> task) {
        corePlugin.getServer().getScheduler().runTaskLater(corePlugin, task, delay);
    }

    /**
     * Schedules an asynchronous task to run after {@code delay} of ticks has passed.
     *
     * @param delay ticks to wait before executing the task
     */
    public void runAsync(final long delay, final Consumer<BukkitTask> task) {
        corePlugin.getServer().getScheduler().runTaskLaterAsynchronously(corePlugin, task, delay);
    }

    /**
     * Schedules a synchronous task to run repeatedly until iterations / cycles limit {@code (cycles)} is reached.
     * Returning {@code false} inside the {@link Predicate} {@code (task)} cancells execution of next iteration(s).
     *
     * @param delay ticks to wait before starting the task
     * @param period ticks to wait in-between tasks
     * @param cycles max iterations
     */
    public void repeat(final long delay, final long period, final long cycles, final Predicate<Integer> task) {
        new BukkitRunnable() {
            int cycle = 1;

            @Override
            public void run() {
                // Executing the code and cancelling the task if 'false' is returned
                if (task.test(cycle) == true)
                    this.cancel();
                // Cancelling the task if iterations limit is reached
                if (cycle++ > cycles)
                    this.cancel();
            }
        }.runTaskTimer(corePlugin, delay, period);
    }

    /**
     * Schedules an asynchronous task to run repeatedly until iterations / cycles limit {@code (cycles)} is reached.
     * Returning {@code false} inside the {@link Predicate} {@code (task)} cancells execution of next iteration(s).
     *
     * @param delay ticks to wait before starting the task
     * @param period ticks to wait in-between tasks
     * @param cycles max iterations
     */
    public void repeatAsync(final long delay, final long period, final long cycles, final Predicate<Integer> task) {
        new BukkitRunnable() {
            int cycle = 1;

            @Override
            public void run() {
                // Executing the code and cancelling the task if 'false' is returned
                if (task.test(cycle) == true)
                    this.cancel();
                // Cancelling the task if iterations limit is reached
                if (cycle++ > cycles)
                    this.cancel();
            }
        }.runTaskTimerAsynchronously(corePlugin, delay, period);
    }

}
