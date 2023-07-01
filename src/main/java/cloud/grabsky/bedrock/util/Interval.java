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
package cloud.grabsky.bedrock.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.time.Instant;
import java.util.Date;

import static cloud.grabsky.bedrock.util.Interval.Unit.*;

/**
 * {@link Interval} is simple (but not very extensible) object that provides methods for
 * unit conversion and creation of human-readable 'elapsed time' strings.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Interval {

    private final long value;

    /**
     * Returns {@link Interval} object of current time.
     */
    public static @NotNull Interval now() {
        return new Interval(System.currentTimeMillis());
    }

    /**
     * Returns {@link Interval} object constructed from provided {@link Float float} {@code (interval)}.
     * It is expected that provided value is <u>already</u> a difference between two timestamps.
     */
    public static @NotNull Interval of(final long interval, final @NotNull Unit unit) {
        return new Interval(interval * unit.factor);
    }

    /**
     * Returns {@link Interval} of time between {@code n} and {@code m}.
     */
    public static @NotNull Interval between(final long n, final long m, final @NotNull Unit unit) {
        return new Interval((n - m) * unit.factor);
    }

    /**
     * Returns interval converted to specified {@link Unit} {@code (unit)}. <br />
     * <pre>
     * Interval.of(1500, Interval.Unit.MILLISECONDS).as(Interval.Unit.SECONDS) // 1.5F
     * Interval.of(300, Interval.Unit.SECONDS).as(Interval.Unit.MINUTES) // 5F
     * </pre>
     */
    public double as(final @NotNull Unit unit) {
        return (double) (value / unit.factor);
    }

    /**
     * Returns a copy of (this) {@link Interval} with {@code n} of {@link Unit} added.
     */
    public @NotNull Interval add(final @NotNull Interval other) {
        return new Interval(this.value + other.value);
    }

    /**
     * Returns a copy of (this) {@link Interval} with {@code n} of {@link Unit} added.
     */
    public @NotNull Interval add(final long n, final @NotNull Unit unit) {
        return new Interval(this.value + (n * unit.factor));
    }

    /**
     * Returns a copy of (this) {@link Interval} with {@code n} of {@link Unit} removed.
     */
    public @NotNull Interval remove(final @NotNull Interval other) {
        return new Interval(this.value - other.value);
    }

    /**
     * Returns a copy of (this) {@link Interval} with {@code n} of {@link Unit} removed.
     */
    public @NotNull Interval remove(final long n, final @NotNull Unit unit) {
        return new Interval(this.value - (n * unit.factor));
    }

    /**
     * Returns new {@link Date} created from (this) {@link Interval}.
     */
    public @NotNull Date toDate() {
        return new Date(this.value);
    }

    /**
     * Returns new {@link Instant} created from (this) {@link Interval}.
     */
    public @NotNull Instant toInstant() {
        return Instant.ofEpochMilli(this.value);
    }

    /**
     * Returns formatted {@link String} expressing this {@link Interval}.
     * <pre>
     * final Interval i = Interval.between(lastJoinedMillis, currentTimeMillis, Interval.Unit.MILLISECONDS);
     * System.out.println(i.toString()) + " ago"; // eg. '1d 7h 32min 10s ago'
     * </pre>
     */
    @Override
    public @NotNull String toString() {
        // Returning 0s for values below 1000. (less than one second)
        if (value < 1000) return "0s";
        // Calculation values, the ugly way.
        final long years = value / YEARS.getFactor();
        final long months = value % YEARS.getFactor() / MONTHS.getFactor();
        final long days = value % YEARS.getFactor() % MONTHS.getFactor() / DAYS.getFactor();
        final long hours = value % YEARS.getFactor() % MONTHS.getFactor() % DAYS.getFactor() / HOURS.getFactor();
        final long minutes = value % YEARS.getFactor() % MONTHS.getFactor() % DAYS.getFactor() % HOURS.getFactor() / MINUTES.getFactor();
        final long seconds = value % YEARS.getFactor() % MONTHS.getFactor() % DAYS.getFactor() % HOURS.getFactor() % MINUTES.getFactor() / SECONDS.getFactor();
        // Creating a new output StringBuilder object.
        final StringBuilder builder = new StringBuilder();
        // Appending to the StringBuilder.
        if (years > 0L) builder.append(years).append("y ");
        if (months > 0L) builder.append(months).append("m ");
        if (days > 0L) builder.append(days).append("d ");
        if (hours > 0L) builder.append(hours).append("h ");
        if (minutes > 0L) builder.append(minutes).append("min ");
        if (seconds > 0L) builder.append(seconds).append("s");
        // Removing last character if a whitespace.
        if (builder.charAt(builder.length() - 1) == ' ')
            builder.deleteCharAt(builder.length() - 1);
        // Building a String and returning.
        return builder.toString();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Unit {
        MILLISECONDS(1L),
        TICKS(50L),
        SECONDS(1_000L),
        MINUTES(60_000L),
        HOURS(3_600_000L),
        DAYS(86_400_000L),
        MONTHS(2_629_800_000L),
        YEARS(31_557_600_000L);

        @Getter(AccessLevel.PUBLIC)
        private final long factor;

    }

}
