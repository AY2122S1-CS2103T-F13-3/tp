package seedu.address.model.lesson;

/**
 * Represents the amount per lesson payable in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMonetaryField(String)} (String)
 */
public class LessonRates extends Money {

    /**
     * Constructs a {@code LessonRates}.
     *
     * @param lessonRates A valid lesson rate.
     */
    public LessonRates(String lessonRates) {
        super(lessonRates);
    }

    @Override
    public String toString() {
        return "$" + value;
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
