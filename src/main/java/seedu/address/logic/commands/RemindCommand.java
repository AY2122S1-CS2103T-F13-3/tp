package seedu.address.logic.commands;

/**
 * Displays a list of upcoming lessons within two days from current time.
 */
public class RemindCommand extends Command {
    public static final String COMMAND_WORD = "remind";

    public static final String COMMAND_ACTION = "View Upcoming Lessons";

    public static final String USER_TIP = "To view your upcoming lessons, type: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows your upcoming lessons within these two days.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_REMIND_MESSAGE = "Opened reminder window.";

    @Override
    public CommandResult execute() {
        return new CommandResult(SHOWING_REMIND_MESSAGE, false, false, false, true, false);
    }
}
