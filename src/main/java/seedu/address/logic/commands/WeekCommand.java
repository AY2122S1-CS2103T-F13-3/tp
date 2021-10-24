package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandResult.DisplayType.WEEK;

/**
 * Displays the user's weekly schedule.
 */
public class WeekCommand extends Command {

    public static final String COMMAND_WORD = "week";

    public static final String COMMAND_ACTION = "View Weekly Schedule";

    public static final String USER_TIP = "To view your weekly schedule, type: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows your weekly schedule.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_WEEK_MESSAGE = "Showing your weekly calendar."
            + " You can go back to list view by typing \""
            + ListCommand.COMMAND_WORD
            + "\" or navigate the calendar with \""
            + NextCommand.COMMAND_WORD
            + "\" and \""
            + BackCommand.COMMAND_WORD
            + "\".";

    @Override
    public CommandResult execute() {
        return new CommandResult(SHOWING_WEEK_MESSAGE, WEEK);
    }
}
