package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * Represents a Lesson's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd MMM yyyy "
            + "and adhere to the following constraints:\n"
            + "1. dd and yyyy are numerical characters.\n"
            + "2. MMM are alphabetical characters. e.g. Jan, Feb, ..., Dec\n"
            + "3. Must be a valid date for the year.";

    /*
    Date strings should be formatted as d MMM uuuu, where d and uuuu are digits.
    and MMM are alphabets e.g. Jan, Mar, Nov, etc.
     */
    public static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMM uuuu")
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    public final String value;

    private final LocalDate localDate;
    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid Date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date.toUpperCase();
        localDate = LocalDate.parse(value, FORMATTER);
    }

    /**
     * Returns if a given string is a valid date.
     *
     * @param test The string to be tested.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the LocalDate representation of the date.
     *
     * @return LocalDate representation of date.
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }

    /**
     * Update the lesson date to the same day on the most recent week
     * that has yet to be pass.
     *
     * @return newDate The date of the same day on the week that has yet to pass.
     */
    public Date updateDate() {
        LocalDate updatedDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(getDayOfWeek()));
        Date newDate = new Date(updatedDate.format(FORMATTER));
        return newDate;
    }

    /**
     * Check if the date has passed.
     *
     * @return true if date is earlier than now.
     */
    public boolean isOver() {
        return getLocalDate().isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compares this Date object with the other Date object.
     *
     * @param other The Date object to compare with.
     * @return 1, if this is later than other;0 if equal; -1 if this is earlier.
     */
    @Override
    public int compareTo(Date other) {
        return getLocalDate().compareTo(other.getLocalDate());
    }
}

