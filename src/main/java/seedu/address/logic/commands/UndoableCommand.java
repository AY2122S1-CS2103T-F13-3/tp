package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

// Solution adapted from
// https://github.com/nus-cs2103-AY1718S2/
// addressbook-level4/blob/master/src/main/java/seedu/
// address/logic/commands/UndoableCommand.java

/**
 * A command that can be undone or redone
 */
public abstract class UndoableCommand extends Command {
    public static final String MESSAGE_FAILURE = "The command has been successfully executed previously; "
            + "it should not fail now.";
    public final String commandType;

    protected UndoableCommand(String commandType) {
        this.commandType = commandType;
    }

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    @Override
    public CommandResult execute() throws CommandException {
        return executeUndoableCommand();
    }

    protected abstract Person undo() throws AssertionError;
    protected abstract Person redo() throws AssertionError;

    protected Index setToDefinitiveIndex(Person personToUndo) {
        ObservableList<Person> mainList = model.getAddressBook().getPersonList();
        Index definitiveIndex = Index.fromZeroBased(mainList.indexOf(personToUndo));
        return definitiveIndex;
    }

    protected void checkValidity(Person person) throws AssertionError {
        if (!model.hasPerson(person)) {
            throw new AssertionError(MESSAGE_FAILURE);
        }
    }
}
