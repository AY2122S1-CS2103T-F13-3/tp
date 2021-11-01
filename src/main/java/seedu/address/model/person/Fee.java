package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's outstanding fees in TAB.
 * Guarantees: immutable; is valid as declared in {@link #isValidFee(String)}
 */
public class Fee {

    public static final String MESSAGE_CONSTRAINTS =
            "Fees should be formatted with a decimal point '.' as a separator between the dollars and cents, "
            + "and adhere to the following constraints:\n"
            + "1. Fees should only contain numbers and at most one decimal point.\n"
            + "2. Fees should not start or end with a decimal point and should have at most two decimal places.";

    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    public final String value;

    /**
     * Constructs an outstanding {@code Fee}.
     *
     * @param fee A valid outstanding fee.
     */
    public Fee(String fee) {
        requireNonNull(fee);
        checkArgument(isValidFee(fee), MESSAGE_CONSTRAINTS);
        value = formatFee(fee);
    }

    /**
     * Removes leading zeroes and postfixes decimal places.
     *
     * @param fee A valid fee.
     * @return The formatted fee.
     */
    private String formatFee(String fee) {
        String formattedFee = fee;
        if (formattedFee.startsWith("0")) { // remove all leading zeroes
            formattedFee = formattedFee.replaceFirst("^0+", "");
        }
        if (formattedFee.startsWith(".")) { // prefix missing zero that was removed
            formattedFee = "0" + formattedFee;
        }
        if (!formattedFee.isEmpty() && !formattedFee.contains(".")) { // postfix missing decimal places
            formattedFee = formattedFee + ".00";
        }
        int length = fee.length();
        if (length >= 2 && fee.charAt(length - 2) == '.') { // postfix missing zero
            formattedFee = formattedFee + "0";
        }
        return fillEmptyString(formattedFee);
    }

    private String fillEmptyString(String fee) {
        return fee.isEmpty() ? "0.00" : fee;
    }

    /**
     * Returns true if fee value is empty.
     *
     * @return True if fee value is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }


    /**
     * Returns true if a given string is a valid fee.
     */
    public static boolean isValidFee(String test) {
        return test.isEmpty() || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fee // instanceof handles nulls
                && value.equals(((Fee) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
