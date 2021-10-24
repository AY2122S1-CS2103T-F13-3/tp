package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public enum DisplayType {
        HELP, // Help information should be shown to the user.
        STUDENTS, // Show the list of students to the user.
        WEEK, // Weekly Schedule should be shown to the user.
        MONTH, // Monthly Schedule should be shown to the user.
        NEXT, // Go forwards to the next week schedule.
        BACK, // Go back to the previous week schedule.
        EXIT // The application should exit.
    }

    private final String feedbackToUser;

    private final DisplayType displayType;

    /** Lesson information of student should be shown to the user. */
    private final Person student;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, DisplayType displayType, Person student) {
        requireAllNonNull(feedbackToUser, displayType);
        this.feedbackToUser = feedbackToUser;
        this.displayType = displayType;
        this.student = student;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, DisplayType displayType) {
        this(feedbackToUser, displayType, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Person student) {
        this(feedbackToUser, DisplayType.STUDENTS, student);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, DisplayType.STUDENTS); // We show students by default
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public Optional<Person> getStudent() {
        return Optional.ofNullable(student);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && displayType == otherCommandResult.displayType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, displayType);
    }

    @Override
    public String toString() {
        return "CommandResult: feedbackToUser = " + feedbackToUser + '\'' + ", displayType = " + displayType.name();
    }
}
