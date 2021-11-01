package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Calculator.getStudentTotalFees;

import java.text.DecimalFormat;
import java.util.Set;

import seedu.address.model.lesson.Lesson;

/**
 * Represents a Person's outstanding fees in TAB.
 * Calculated by summing up all outstanding fees from lesson.
 */
public class Fee {

    public static final String MESSAGE_CONSTRAINTS =
            "Fees should be formatted with a decimal point '.' as a separator between the dollars and cents, "
            + "and adhere to the following constraints:\n"
            + "1. Fees should only contain numbers and at most one decimal point.\n"
            + "2. Fees should not start or end with a decimal point and should have at most two decimal places.";

    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final float value;

    /**
     * Constructs an outstanding {@code Fee}.
     *
     * @param lessons A set of lessons to calculate current outstanding fees.
     */
    public Fee(Set<Lesson> lessons) {
        requireNonNull(lessons);
        value = getStudentTotalFees(lessons);
    }

    @Override
    public String toString() {
        return "$" + df.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fee // instanceof handles nulls
                && value == ((Fee) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }
}
