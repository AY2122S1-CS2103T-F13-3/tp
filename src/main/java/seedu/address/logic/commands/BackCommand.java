package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandResult.DisplayType.BACK;

/**
 * Navigates backwards in the calendar.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String COMMAND_ACTION = "Navigate backwards the calendar.";

    public static final String USER_TIP = "To navigate backward in the calendar, type: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Goes backwards in the calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String BACK_MESSAGE = "Went backwards in the calendar."
            + " You can jump back to today by typing \""
            + WeekCommand.COMMAND_WORD
            + "\" or \""
            + MonthCommand.COMMAND_WORD
            + "\" or navigate forwards using \""
            + NextCommand.COMMAND_WORD
            + "\".";

    @Override
    public CommandResult execute() {
        return new CommandResult(BACK_MESSAGE, BACK);
    }
}
