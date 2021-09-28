package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Lesson's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd MMM yyyy "
            + "and adhere to the following constraints:\n"
            + "1. dd and yyyy are numerical characters.\n"
            + "2. MMM are alphabetical characters. e.g. Jan, Feb, ..., Dec";

    /*
    Date strings should be formatted as dd MMM uuuu, where dd and uuuu are digits.
    and MMM are alphabets e.g. Jan, Mar, Nov, etc.
     */
    public static final String VALIDATION_REGEX = "^[0-9]{2}\\s[a-zA-Z]{3}\\s[0-9]{4}";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter
        .ofPattern("dd MMM uuuu")
        .withResolverStyle(ResolverStyle.STRICT);

    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid Date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     *
     * @param test The string to be tested.
     */
    public static boolean isValidDate(String test) {
        boolean isValid = true;
        try {
            LocalDate.parse(test, FORMATTER);
        } catch (DateTimeParseException e) {
            isValid = false;
        } finally {
            return test.matches(VALIDATION_REGEX) && isValid;
        }
    }

    /**
     * Returns the LocalDate representation of the date.
     *
     * @return LocalDate representation of date.
     */
    public LocalDate getLocalDate() {
        return LocalDate.parse(this.value, FORMATTER);
    }

    /**
     * Check if the date has passed.
     *
     * @return true if date is earlier than now.
     */
    public boolean isOver() {
        return getLocalDate().compareTo(LocalDate.now()) < 0;
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

