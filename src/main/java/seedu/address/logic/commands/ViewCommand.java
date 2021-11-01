package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

public class ViewCommand extends Command {
    public static final String COMMAND_ACTION = "View Student's Lessons";

    public static final String COMMAND_WORD = "view";

    public static final String COMMAND_PARAMETERS = "INDEX";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Displayed list of lessons for %1$s!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the list of lessons "
        + " of the student identified by the index number used in the displayed student list.\n"
        + "Parameters: " + COMMAND_PARAMETERS + "\n"
        + "Example: " + COMMAND_EXAMPLE;

    private final Index index;

    /**
     * @param index of the person in the filtered person list to view
     */
    public ViewCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Person studentToView = lastShownList.get(index.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                studentToView.getName()), studentToView);
    }
}
