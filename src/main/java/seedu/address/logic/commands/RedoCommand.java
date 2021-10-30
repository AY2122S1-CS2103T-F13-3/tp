package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redoes the last Command that has been undone. \n";

    public static final String MESSAGE_SUCCESS = "%1$s command for %2$s has been redone.";
    public static final String MESSAGE_FAILURE = "No commands to redo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        UndoableCommand commandToRedo = undoRedoStack.popRedo();
        Person studentModified = commandToRedo.redo();

        if (commandToRedo.commandType.equals(ClearCommand.COMMAND_ACTION)
                || commandToRedo.commandType.equals(DeleteCommand.COMMAND_ACTION)) {
            String successMessage = commandToRedo.commandType + " command has been redone.";
            return new CommandResult(successMessage);
        }

        String successMessage = String.format(MESSAGE_SUCCESS, commandToRedo.commandType, studentModified.getName());
        return new CommandResult(successMessage, studentModified);
    }

    @Override
    public void setDependencies(Model model, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
