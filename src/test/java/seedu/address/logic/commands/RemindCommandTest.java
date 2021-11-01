package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemindCommand.SHOWING_REMIND_MESSAGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class RemindCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_remind_success() {
        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_REMIND_MESSAGE, CommandResult.DisplayType.REMINDER);
        assertCommandSuccess(prepareRemindCommand(), model, expectedCommandResult, expectedModel);
    }

    /**
     * Generates a {@code RemindCommand}.
     */
    private RemindCommand prepareRemindCommand() {
        RemindCommand remindCommand = new RemindCommand();
        remindCommand.setDependencies(model, new UndoRedoStack());
        return remindCommand;
    }
}
