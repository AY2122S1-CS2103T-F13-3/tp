package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** Schedule should be shown to the user. */
    private final boolean isShowSchedule;

    /** Reminder of upcoming lessons should be shown to the user. */
    private final boolean isShowReminder;

    /** Tag list should be shown to the user instead of the default person list. */
    private final boolean isShowTagList;

    /** The application should exit. */
    private final boolean isExit;

    /** Lesson information of student should be shown to the user. */
    private final Person student;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser, boolean isShowHelp,
            boolean isShowTagList, boolean isShowSchedule, boolean isShowReminder,
            boolean isExit, Person student) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = isShowHelp;
        this.isExit = isExit;
        this.isShowTagList = isShowTagList;
        this.isShowSchedule = isShowSchedule;
        this.isShowReminder = isShowReminder;
        this.student = student;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp,
            boolean isShowTagList, boolean isShowSchedule, boolean isShowReminder, boolean isExit) {
        this(feedbackToUser, isShowHelp, isShowTagList, isShowSchedule, isShowReminder, isExit, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code student},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Person student) {
        this(feedbackToUser, false, false, false, false, false, student);
    }

    /**
     * Returns the feedback to user from command execution.
     *
     * @return Feedback to user from command execution.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns true if the command is a help command.
     *
     * @return True if the command is a help command.
     */
    public boolean isShowHelp() {
        return isShowHelp;
    }

    /**
     * Returns true if the command is a tag command.
     *
     * @return True if the command is a tag command.
     */
    public boolean isShowTagList() {
        return isShowTagList;
    }

    /**
     * Returns true if the command is a schedule command.
     *
     * @return True if the command is a schedule command.
     */
    public boolean isShowSchedule() {
        return isShowSchedule;
    }

    public boolean isShowReminder() {
        return isShowReminder;
    }

    /**
     * Returns an Optional of student.
     *
     * @return Optional of student.
     */
    public Optional<Person> getStudent() {
        return Optional.ofNullable(student);
    }

    /**
     * Returns true if the command is an exit command.
     *
     * @return True if the command is an exit command.
     */
    public boolean isExit() {
        return isExit;
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
                && isShowHelp == otherCommandResult.isShowHelp
                && isShowTagList == otherCommandResult.isShowTagList
                && isShowSchedule == otherCommandResult.isShowSchedule
                && isShowReminder == otherCommandResult.isShowReminder
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isShowTagList, isShowSchedule,
                isShowReminder, isExit);
    }

    @Override
    public String toString() {
        return "Feedback To User: " + feedbackToUser + "\n"
                + "Show Help: " + isShowHelp + "\n" + "Show Tag List: " + isShowTagList + "\n"
                + "Show Schedule: " + isShowSchedule + "\n" + "Show Reminder: " + isShowReminder
                + "\n" + "Exit: " + isExit;
    }
}
