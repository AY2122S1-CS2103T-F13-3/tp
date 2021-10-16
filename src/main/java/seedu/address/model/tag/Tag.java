package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String ASSERTION_ERROR_NON_POSITIVE_DUPLICATES =
            "Number of students labelled under this tag should be positive.";

    private final String tagName;
    private final int numStudents;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName.toUpperCase();
        this.numStudents = 1;
    }

    /**
     * Constructs a {@code Tag} with specified number of students labelled under this tag.
     *
     * @param tagName A valid tag name.
     * @param numStudents Number of students labelled under this tag.
     */
    private Tag(String tagName, int numStudents) {
        assert numStudents > 0 : ASSERTION_ERROR_NON_POSITIVE_DUPLICATES;
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.numStudents = numStudents;
    }

    /**
     * Returns true if a given string is a valid tag name.
     *
     * @param test String to be checked if it is a valid tag name.
     * @return True if the string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Creates a tag with its original tag name and the given number of students labelled under this tag.
     *
     * @param num Number of students labelled under this tag.
     * @return Tag containing the original tag name and number of students under this tag.
     */
    public Tag createTagWithNum(int num) {
        assert num > 0 : ASSERTION_ERROR_NON_POSITIVE_DUPLICATES;
        return new Tag(tagName, num);
    }

    /**
     * Stringifies the number of students under this tag.
     *
     * @return String representation of the number of students under this tag.
     */
    public String getNumStudentsString() {
        return Integer.toString(numStudents);
    }

    public String getTagName() {
        return tagName;
    }

    public int getNumStudents() {
        return numStudents;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                        && getTagName().equals(((Tag) other).getTagName())); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
