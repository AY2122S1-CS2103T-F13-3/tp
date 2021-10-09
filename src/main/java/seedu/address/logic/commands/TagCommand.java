package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_SUCCESS = "Displayed all tags!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getFilteredTagList();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}
