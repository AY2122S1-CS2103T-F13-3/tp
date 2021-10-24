package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandResult.DisplayType.MONTH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MonthCommand.SHOWING_MONTH_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class MonthCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_month_success() {
        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_MONTH_MESSAGE, MONTH);
        assertCommandSuccess(new MonthCommand(), model, expectedCommandResult, expectedModel);
    }

}
