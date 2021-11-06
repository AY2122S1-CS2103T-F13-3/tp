package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NO_PARAMS;
import static seedu.address.logic.commands.CommandResult.DisplayType.TAGS;

import seedu.address.logic.commands.exceptions.CommandException;

public class TagCommand extends Command {
    public static final String COMMAND_ACTION = "View Tags";

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_SUCCESS = "Displayed all tags! \n"
            + "To view student list, type: \"list\".\n"
            + "To view calendar, type: \"calendar\".";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all tags.\n" + MESSAGE_NO_PARAMS;

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS, TAGS);
    }
}
