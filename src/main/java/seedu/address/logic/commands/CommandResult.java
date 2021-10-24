package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public enum DisplayType {
        HELP, // Help information should be shown to the user.
        STUDENTS,
        WEEK, // Weekly Schedule should be shown to the user.
        MONTH, // Monthly Schedule should be shown to the user.
        NEXT, // Go forwards to the next week schedule.
        BACK, // Go back to the previous week schedule.
        EXIT // The application should exit.
    }

    private final String feedbackToUser;

    private final DisplayType displayType;
    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, DisplayType displayType) {
        requireAllNonNull(feedbackToUser, displayType);
        this.feedbackToUser = feedbackToUser;
        this.displayType = displayType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, DisplayType.STUDENTS);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public boolean isShowHelp() {
        return displayType == DisplayType.HELP;
    }

    public boolean isExit() {
        return displayType == DisplayType.EXIT;
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
