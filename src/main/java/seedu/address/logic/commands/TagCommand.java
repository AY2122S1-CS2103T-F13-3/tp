package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class TagCommand extends Command {
    public static final String COMMAND_ACTION = "View Tags";

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_SUCCESS = "Displayed all tags! \n"
            + "To view student list, type: \"list\".\n"
            + "To view schedule, type: \"schedule\".";

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false, false);
    }
}
