package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class ViewCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        assertCommandSuccess(prepareViewCommand(INDEX_FIRST_PERSON), model,
                String.format(ViewCommand.MESSAGE_SUCCESS,
                        expectedModel.getFilteredPersonList()
                                .get(INDEX_FIRST_PERSON.getZeroBased()).getName()),
                expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandFailure(
            prepareViewCommand(INDEX_SECOND_PERSON), model,
            String.format(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX));
    }

    /**
     * Generates a {@code ViewCommand}.
     */
    private ViewCommand prepareViewCommand(Index index) {
        ViewCommand viewCommand = new ViewCommand(index);
        viewCommand.setDependencies(model, new UndoRedoStack());
        return viewCommand;
    }
}
