package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

public class LessonDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> prepareLessonDeleteCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_validPersonWithoutExistingLessons_failure() {
        LessonDeleteCommand lessonDeleteCommand = prepareLessonDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON);
        assertCommandFailure(lessonDeleteCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPersonWithAnExistingLesson_success() {
        Lesson lesson = new LessonBuilder().build();
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withLessons(lesson).build();
        model.setPerson(firstPerson, editedPerson);

        LessonDeleteCommand lessonDeleteCommand = prepareLessonDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON);

        String expectedMessage = String.format(
            LessonDeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, editedPerson.getName(), lesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            firstPerson);

        assertCommandSuccess(lessonDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LessonDeleteCommand lessonDeleteCommand = prepareLessonDeleteCommand(outOfBoundIndex, INDEX_FIRST_LESSON);

        assertCommandFailure(lessonDeleteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        // filter list to show only the first person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LessonDeleteCommand lessonDeleteCommand = prepareLessonDeleteCommand(outOfBoundIndex, INDEX_FIRST_LESSON);

        assertCommandFailure(lessonDeleteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LessonDeleteCommand deleteSampleLessonCommand = prepareLessonDeleteCommand(INDEX_FIRST_PERSON,
            INDEX_FIRST_LESSON);
        LessonDeleteCommand deleteSampleLessonCommand2 = prepareLessonDeleteCommand(INDEX_SECOND_PERSON,
            INDEX_FIRST_LESSON);

        // same object -> returns true
        assertTrue(deleteSampleLessonCommand.equals(deleteSampleLessonCommand));

        // same values -> returns true
        LessonDeleteCommand deleteSampleLessonCommandCopy = prepareLessonDeleteCommand(INDEX_FIRST_PERSON,
            INDEX_FIRST_LESSON);
        assertTrue(deleteSampleLessonCommand.equals(deleteSampleLessonCommandCopy));

        // different types -> returns false
        assertFalse(deleteSampleLessonCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSampleLessonCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteSampleLessonCommand.equals(deleteSampleLessonCommand2));
    }

    /**
     * Generates a {@code LessonDeleteCommand} with parameters {@code personIndex} and {@code lessonIndex}.
     */
    private LessonDeleteCommand prepareLessonDeleteCommand(Index personIndex, Index lessonIndex) {
        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(personIndex, lessonIndex);
        lessonDeleteCommand.setDependencies(model, new UndoRedoStack());
        return lessonDeleteCommand;
    }
}
