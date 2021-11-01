package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Lesson's TimeRange in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeRange(String)}
 */
public class TimeRange implements Comparable<TimeRange> {
    public static final String MESSAGE_CONSTRAINTS = "Lesson time range should be formatted as HHmm-HHmm "
            + "and adhere to the following constraints:\n"
            + "1. Start time must be before end time.\n"
            + "2. Lesson should be conducted between 8am and 10pm, inclusive.";

    public static final String VALIDATION_REGEX = "^(([01]?[0-9]|2[0-3])[0-5][0-9])-(([01]?[0-9]|2[0-3])[0-5][0-9])";
    public static final LocalTime DAY_START = LocalTime.of(8, 0, 0);
    public static final LocalTime DAY_END = LocalTime.of(22, 0, 0);

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");


    public final String value;

    private final LocalTime start;
    private final LocalTime end;

    /**
     * Constructs a TimeRange object.
     *
     * @param value A valid time range.
     */
    public TimeRange(String value) {
        requireNonNull(value);
        checkArgument(isValidTimeRange(value), MESSAGE_CONSTRAINTS);
        this.value = value;
        String[] startEndTimes = value.split("-", 2);
        start = LocalTime.parse(startEndTimes[0], DATE_TIME_FORMAT);
        end = LocalTime.parse(startEndTimes[1], DATE_TIME_FORMAT);
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    /**
     * Checks if the start is earlier than the end and
     * the range is between 8am and 10pm, inclusive.
     *
     * @param test The value to be tested.
     * @return True if start is earlier than or same time as end.
     */
    public static boolean isValidTimeRange(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String[] startEndTimes = test.split("-", 2);
        if (startEndTimes.length < 2) { // There must be a '-'
            return false;
        }
        try {
            LocalTime startTime = LocalTime.parse(startEndTimes[0], DATE_TIME_FORMAT);
            LocalTime endTime = LocalTime.parse(startEndTimes[1], DATE_TIME_FORMAT);
            return endTime.compareTo(startTime) > 0 // End cannot be same or before start
                    && startTime.compareTo(DAY_START) >= 0 // Same or later than 8am
                    && endTime.compareTo(DAY_END) <= 0; // Same or earlier than 10pm
        } catch (DateTimeParseException e) { // Double check even though regex already ensures values can be parsed
            return false;
        }
    }

    /**
     * Checks if the given time range overlaps with this time range.
     *
     * @param other The TimeRange to be tested.
     */
    public boolean isClashing(TimeRange other) {
        if (start.compareTo(other.start) <= 0 && end.compareTo(other.end) >= 0) {
            return true; // starts before other and ends after other
        }
        if (start.compareTo(other.start) > 0 && start.compareTo(other.end) < 0) {
            return true; // start time is within the other time range
        }
        if (end.compareTo(other.start) > 0 && end.compareTo(other.end) < 0) {
            return true; // end time is within the other time range
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeRange) // instanceof handles nulls
                && start.equals(((TimeRange) other).start)
                && end.equals(((TimeRange) other).end); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    /**
     * Compares this TimeRange object with the other TimeRange object.
     *
     * @param other The TimeRange object to compare with.
     * @return 1, if this is later than other;0 if clashing; -1 if this is earlier.
     */
    @Override
    public int compareTo(TimeRange other) {
        /*
        start.compareTo(other.start) will not return 0 because it will
        be labelled as clashing with this time range.
         */
        return isClashing(other) ? 0 : start.compareTo(other.start);
    }
}
