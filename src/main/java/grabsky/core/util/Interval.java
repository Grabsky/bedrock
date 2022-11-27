package grabsky.core.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@link Interval} is light-weight (and not very extensible) object that simplifies
 * unit conversion and creation of human-readable 'elapsed time' strings.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Interval {

    private final long interval;

    /**
     * Returns {@link Interval} object constructed from provided {@link Float float} {@code (interval)}.
     * It is expected that provided value is <u>already</u> a difference between two timestamps.
     */
    public Interval of(final long interval, final Unit unit) {
        return new Interval(interval * unit.factor);
    }

    /**
     * Returns {@link Interval} of time between {@code n} and {@code m}.
     */
    public Interval between(final long n, final long m, final Unit unit) {
        return new Interval((n - m) * unit.factor);
    }

    /**
     * Returns interval converted to specified {@link Unit} {@code (unit)}. <br />
     * <pre>
     * Interval.of(1500, Interval.Unit.MILLISECONDS).as(Interval.Unit.SECONDS) // 1.5F
     * Interval.of(300, Interval.Unit.SECONDS).as(Interval.Unit.MINUTES) // 5F
     * </pre>
     */
    public float as(final Unit unit) {
        return (float) (interval / unit.factor);
    }

    /**
     * Returns formatted {@link String} expressing this {@link Interval}.
     * <pre>
     * final Interval i = Interval.between(lastJoinedMillis, currentTimeMillis, Interval.Unit.MILLISECONDS);
     * System.out.println(i.toString()) + " ago"; // eg. '1d 7h 32min 10s ago'
     * </pre>
     */
    @Override
    public String toString() {
        // returning 0s for values below 1000 (ms)
        if (interval < 1000) return "0s";
        // calculating some values (the ugly way)
        final long totalSeconds = interval / 1000;
        final long days = totalSeconds / 86400;
        final long hours = totalSeconds % 86400 / 3600;
        final long minutes = totalSeconds % 86400 % 3600 / 60;
        final long seconds = totalSeconds % 86400 % 3600 % 60;
        // working on the output
        final StringBuilder builder = new StringBuilder();
        // appending to the output
        if (days > 0L) builder.append(days).append("d ");
        if (hours > 0L) builder.append(hours).append("h ");
        if (minutes > 0L) builder.append(minutes).append("min ");
        if (seconds > 0L) builder.append(seconds).append("s");
        // removing whitespace if present
        if (builder.charAt(builder.length() - 1) == ' ')
            builder.deleteCharAt(builder.length() - 1);
        // returning
        return builder.toString();
    }

    @Accessors(fluent = true)
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Unit {
        MILLISECONDS(1L),
        TICKS(50L),
        SECONDS(1000L),
        MINUTES(60000L),
        HOURS(3600000L),
        DAYS(86400000L);

        @Getter(AccessLevel.PUBLIC)
        private final long factor;

    }

}
