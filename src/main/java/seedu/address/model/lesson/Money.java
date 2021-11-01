package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents the amount of money and money-related fields in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMonetaryField(String)} (String)}
 */
public class Money {

    public static final String MESSAGE_CONSTRAINTS =
            "Money-related fields should be formatted with a decimal point '.' "
                    + "as a separator between the dollars and cents, "
                    + "and adhere to the following constraints:\n"
                    + "1. Money-related fields should only contain numbers and at most one decimal point.\n"
                    + "2. Money-related fields should not start or end with a decimal point"
                    + " and should have at most two decimal places.";
    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    public final String value;

    /**
     * Constructs a {@code Money}.
     *
     * @param amount Amount of money.
     */
    public Money(String amount) {
        requireNonNull(amount);
        checkArgument(isValidMonetaryField(amount));
        value = formatValue(fillEmptyString(amount));
    }

    private String fillEmptyString(String amount) {
        return amount.isEmpty() ? "0.00" : amount;
    }

    /**
     * Removes leading zeroes and postfixes decimal places.
     *
     * @param value A valid value that represents Money.
     * @return The formatted value.
     */
    public static String formatValue(String value) {
        String formattedValue = value;

        if (formattedValue.startsWith(".")) { // prefix missing zero that was removed
            formattedValue = "0" + formattedValue;
        }
        if (!formattedValue.isEmpty() && !formattedValue.contains(".")) { // postfix missing decimal places
            formattedValue = formattedValue + ".00";
        }
        int length = value.length();
        if (length >= 2 && value.charAt(length - 2) == '.') { // postfix missing zero
            formattedValue = formattedValue + "0";
        }
        return formattedValue;
    }

    /**
     * Returns true if a given string is a valid format for monetary fields.
     */
    public static boolean isValidMonetaryField(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public float getMonetaryValueInFloat() {
        return Float.parseFloat(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && value.equals(((Money) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
