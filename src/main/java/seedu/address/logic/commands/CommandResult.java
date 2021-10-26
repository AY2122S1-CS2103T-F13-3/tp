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

    /** Person list will be shown to the user. */
    private final boolean isShowPersonList;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** Tag list should be shown to the user instead of the default person list. */
    private final boolean isShowTagList;

    /** Schedule should be shown to the user. */
    private final boolean isShowSchedule;

    /** The application should exit. */
    private final boolean isExit;

    /** Lesson information of student should be shown to the user. */
    private final Person student;

    /** Check if command was an undo or redo command */
    private final boolean isUndoOrRedo;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser, boolean isShowPersonList, boolean isShowHelp,
                          boolean isShowTagList, boolean isShowSchedule, boolean isUndoOrRedo,
                          boolean isExit, Person student) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowPersonList = isShowPersonList;
        this.isShowHelp = isShowHelp;
        this.isUndoOrRedo = isUndoOrRedo;
        this.isExit = isExit;
        this.isShowTagList = isShowTagList;
        this.isShowSchedule = isShowSchedule;
        this.student = student;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowPersonList, boolean isShowHelp,
                         boolean isShowTagList, boolean isShowSchedule, boolean isExit) {
        this(feedbackToUser, isShowPersonList, isShowHelp, isShowTagList,
            isShowSchedule, false, isExit, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, true, false, false,
            false, false, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code student},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Person student) {
        this(feedbackToUser, true, false, false, false, false,false, student);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code student},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean isUndoOrRedo) {
        this(feedbackToUser, true, false, false,
            false, isUndoOrRedo, false, null);
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

    /**
     * Returns an Optional of student.
     *
     * @return Optional of student.
     */
    public Optional<Person> getStudent() {
        return Optional.ofNullable(student);
    }

    /**
     * Returns true if the command is an undo or redo command.
     *
     * @return True if the command is an undo or redo command.
     */
    public boolean isUndoOrRedo() {
        return isUndoOrRedo;
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
                && isShowPersonList == otherCommandResult.isShowPersonList
                && isShowHelp == otherCommandResult.isShowHelp
                && isShowTagList == otherCommandResult.isShowTagList
                && isShowSchedule == otherCommandResult.isShowSchedule
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowPersonList, isShowHelp, isShowTagList, isShowSchedule, isExit);
    }

    @Override
    public String toString() {
        return "Feedback To User: " + feedbackToUser + "\n" + "Show Student List: " + isShowHelp + "\n"
                + "Show Help: " + isShowHelp + "\n" + "Show Tag List: " + isShowTagList + "\n"
                + "Show Schedule: " + isShowSchedule + "\n" + "Exit: " + isExit;
    }
}
