package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the amount per lesson payable in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLessonRates(String)}
 */
public class LessonRates {
    public static final String MESSAGE_CONSTRAINTS =
            "Lesson Rates should be formatted with a decimal point '.' as a separator between the dollars and cents, "
                    + "and adhere to the following constraints:\n"
                    + "1. Lesson Rates should only contain numbers and at most one decimal point.\n"
                    + "2. Lesson Rates should not start or end with a decimal point"
                    + " and should have at most two decimal places.";

    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    public final String value;

    /**
     * Constructs a {@code LessonRates}.
     *
     * @param lessonRates A valid lesson rate.
     */
    public LessonRates(String lessonRates) {
        requireNonNull(lessonRates);
        checkArgument(isValidLessonRates(lessonRates));
        value = formatLessonRates(lessonRates);
    }

    /**
     * This is taken exactly from implementation of {@code Fee#formateFee(String fee)}
     * Removes leading zeroes and postfixes decimal places.
     *
     * @param lessonRates A valid lesson rate.
     * @return The formatted lesson rates.
     */
    private String formatLessonRates(String lessonRates) {
        String formattedRates = lessonRates;
        if (formattedRates.startsWith("0")) { // remove all leading zeroes
            formattedRates = formattedRates.replaceFirst("^0+", "");
        }
        if (formattedRates.startsWith(".")) { // prefix missing zero that was removed
            formattedRates = "0" + formattedRates;
        }
        if (!formattedRates.isEmpty() && !formattedRates.contains(".")) { // postfix missing decimal places
            formattedRates = formattedRates + ".00";
        }
        int length = lessonRates.length();
        if (length >= 2 && lessonRates.charAt(length - 2) == '.') { // postfix missing zero
            formattedRates = formattedRates + "0";
        }
        return formattedRates;
    }

    public static boolean isValidLessonRates(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof LessonRates //instanceof handles nulls
                && value.equals(((LessonRates) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
