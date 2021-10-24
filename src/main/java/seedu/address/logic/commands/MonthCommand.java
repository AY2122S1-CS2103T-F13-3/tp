package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandResult.DisplayType.MONTH;

/**
 * Displays the user's weekly schedule.
 */
public class MonthCommand extends Command {

    public static final String COMMAND_WORD = "month";

    public static final String COMMAND_ACTION = "View Monthly Schedule";

    public static final String USER_TIP = "To view your monthly schedule, type: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows your monthly schedule.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_MONTH_MESSAGE = "Showing your monthly calendar."
            + " You can go back to list view by typing \""
            + ListCommand.COMMAND_WORD
            + "\" or navigate the calendar with \""
            + NextCommand.COMMAND_WORD
            + "\" and \""
            + BackCommand.COMMAND_WORD
            + "\".";

    @Override
    public CommandResult execute() {
        return new CommandResult(SHOWING_MONTH_MESSAGE, MONTH);
    }
}
