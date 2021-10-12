package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LessonAddCommand.
 */
public class LessonAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonAddCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_validPersonValidLesson_success() {
        Lesson sampleLesson = new LessonBuilder().build();
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased()))
            .withLessons(sampleLesson).build();

        LessonAddCommand lessonAddCommand = new LessonAddCommand(INDEX_FIRST_PERSON, sampleLesson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS,
            sampleLesson, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            editedPerson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clashingLessonUnfilteredList_failure() {
        Lesson lesson = new LessonBuilder().build();
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person person = new PersonBuilder(firstPerson).withLessons(lesson).build();

        Model modelCopy = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        modelCopy.setPerson(firstPerson, person);

        // Add a different lesson on the same time slot
        Lesson clashingLesson = new LessonBuilder().withHomeworkSet("Test").buildRecurring();
        LessonAddCommand lessonAddCommand = new LessonAddCommand(INDEX_FIRST_PERSON, clashingLesson);

        assertCommandFailure(lessonAddCommand, modelCopy, LessonAddCommand.MESSAGE_CLASHING_LESSON);
    }

    @Test
    public void execute_clashingLessonFilteredList_failure() {
        Lesson lesson = new LessonBuilder().build();
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person person = new PersonBuilder(secondPerson).withLessons(lesson).build();

        Model modelCopy = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        modelCopy.setPerson(secondPerson, person);

        showPersonAtIndex(modelCopy, INDEX_FIRST_PERSON);
        /*
        Add a different lesson to the only person in the filtered list on a clashing time
        slot as the second person in the unfiltered list.
         */
        Lesson clashingLesson = new LessonBuilder().withHomeworkSet("Test").buildRecurring();
        LessonAddCommand lessonAddCommand = new LessonAddCommand(INDEX_FIRST_PERSON, clashingLesson);

        assertCommandFailure(lessonAddCommand, modelCopy, LessonAddCommand.MESSAGE_CLASHING_LESSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Lesson lesson = new LessonBuilder().buildRecurring();
        LessonAddCommand lessonAddCommand = new LessonAddCommand(outOfBoundIndex, lesson);

        assertCommandFailure(lessonAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        LessonAddCommand lessonAddCommand = new LessonAddCommand(outOfBoundIndex, new LessonBuilder().build());

        assertCommandFailure(lessonAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Lesson sampleLesson = new LessonBuilder().build();
        LessonAddCommand addSampleLessonCommand = new LessonAddCommand(INDEX_FIRST_PERSON,
                sampleLesson);
        LessonAddCommand addSampleLessonCommand2 = new LessonAddCommand(INDEX_SECOND_PERSON,
                sampleLesson);

        // same object -> returns true
        assertTrue(addSampleLessonCommand.equals(addSampleLessonCommand));

        // same values -> returns true
        LessonAddCommand addSampleLessonCommandCopy = new LessonAddCommand(INDEX_FIRST_PERSON,
                sampleLesson);
        assertTrue(addSampleLessonCommand.equals(addSampleLessonCommandCopy));

        // different types -> returns false
        assertFalse(addSampleLessonCommand.equals(1));

        // null -> returns false
        assertFalse(addSampleLessonCommand.equals(null));

        // different person -> returns false
        assertFalse(addSampleLessonCommand.equals(addSampleLessonCommand2));
    }
}
