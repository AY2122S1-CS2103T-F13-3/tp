package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemindCommand.SHOWING_REMIND_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class RemindCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_remind_success() {
        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_REMIND_MESSAGE, CommandResult.DisplayType.REMINDER);
        assertCommandSuccess(new RemindCommand(), model, expectedCommandResult, expectedModel);
    }
}
