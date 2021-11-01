package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUE;
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
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.util.CommandUtil;
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
        assertThrows(NullPointerException.class, () -> prepareLessonAddCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_validPersonValidLesson_success() {
        Lesson sampleLesson = new LessonBuilder().build();
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased()))
            .withLessons(sampleLesson).build();

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, sampleLesson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS,
                editedPerson.getName(), sampleLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            editedPerson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPersonValidLessonDateRange_success() {
        Lesson sampleLesson = new LessonBuilder()
                .withDate(VALID_DATE_PAST)
                .withEndDate(VALID_DATE_FUTURE).buildRecurring();
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLessons(sampleLesson).build();

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, sampleLesson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS,
                editedPerson.getName(), sampleLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPersonInvalidLesson_success() {
        Lesson sampleLesson = new LessonBuilder()
                .withDate(VALID_DATE_FUTURE)
                .withEndDate(VALID_DATE_PAST).buildRecurring();
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLessons(sampleLesson).build();

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, sampleLesson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_INVALID_DATE_RANGE,
            editedPerson.getName(), sampleLesson);

        assertCommandFailure(lessonAddCommand, model, expectedMessage);
    }

    @Test
    public void execute_clashingLessonUnfilteredList_failure() {
        Lesson lesson = new LessonBuilder().build();
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person person = new PersonBuilder(firstPerson).withLessons(lesson).build();

        model.setPerson(firstPerson, person);

        // Add a different lesson on the same time slot
        Lesson clashingLesson = new LessonBuilder().withHomeworkSet("Test").buildRecurring();
        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, clashingLesson);

        assertCommandFailure(lessonAddCommand, model, LessonAddCommand.MESSAGE_CLASHING_LESSON
            + CommandUtil.lessonsToString(model.getClashingLessonsString(clashingLesson)));
    }

    @Test
    public void execute_nonClashingLessonUnfilteredList_success() {
        // overlapping date ranges
        Lesson lesson = new LessonBuilder()
            .withDate(VALID_DATE_MON)
            .withEndDate("19 OCT 2021")
            .withCancelledDatesSet("18 Oct 2021")
            .buildRecurring();

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person person = new PersonBuilder(firstPerson).withLessons(lesson).build();

        model.setPerson(firstPerson, person);

        // Add a different lesson on the same time slot but not clashing due to cancelled dates
        Lesson nonClashingLesson = new LessonBuilder()
                .withDate("18 Oct 2021")
                .buildRecurring();

        Person editedPerson = new PersonBuilder(person).withLessons(lesson, nonClashingLesson).build();

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, nonClashingLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            editedPerson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS,
            editedPerson.getName(), nonClashingLesson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonOverlappingLessonUnfilteredList_success() {

        // Non-overlapping date ranges
        Lesson lesson = new LessonBuilder()
            .withDate(VALID_DATE_MON)
            .withEndDate("19 OCT 2021")
            .withCancelledDatesSet("18 Oct 2021")
            .buildRecurring();

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person person = new PersonBuilder(firstPerson).withLessons(lesson).build();

        model.setPerson(firstPerson, person);

        // Add a different lesson on the same time slot but not clashing due to cancelled dates
        Lesson nonClashingLesson = new LessonBuilder()
            .withDate("18 Oct 2021")
            .buildRecurring();

        Person editedPerson = new PersonBuilder(person).withLessons(lesson, nonClashingLesson).build();

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, nonClashingLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            editedPerson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS,
            editedPerson.getName(), nonClashingLesson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overlappingParallelLessonsUnfilteredList_success() {

        // Non-overlapping date ranges
        Lesson lesson = new LessonBuilder()
            .withDate(VALID_DATE_MON)
            .buildRecurring();

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person person = new PersonBuilder(firstPerson).withLessons(lesson).build();

        model.setPerson(firstPerson, person);

        // Add a different lesson on the same time slot but not clashing due to cancelled dates
        Lesson nonClashingLesson = new LessonBuilder()
            .withDate(VALID_DATE_TUE)
            .buildRecurring();

        Person editedPerson = new PersonBuilder(person).withLessons(lesson, nonClashingLesson).build();

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, nonClashingLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            editedPerson);

        String expectedMessage = String.format(LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS,
            editedPerson.getName(), nonClashingLesson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clashingLessonFilteredList_failure() {
        Lesson lesson = new LessonBuilder().build();
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person person = new PersonBuilder(secondPerson).withLessons(lesson).build();

        model.setPerson(secondPerson, person);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        /*
        Add a different lesson to the only person in the filtered list on a clashing time
        slot as the second person in the unfiltered list.
         */
        Lesson clashingLesson = new LessonBuilder().withHomeworkSet("Test").buildRecurring();
        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, clashingLesson);

        assertCommandFailure(lessonAddCommand, model, LessonAddCommand.MESSAGE_CLASHING_LESSON
            + CommandUtil.lessonsToString(model.getClashingLessonsString(clashingLesson)));
    }

    @Test
    public void execute_clashingRecurringLessonFilteredList_failure() {
        Lesson lesson = new LessonBuilder().buildRecurring();
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person person = new PersonBuilder(secondPerson).withLessons(lesson).build();

        model.setPerson(secondPerson, person);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        /*
        Add a different lesson to the only person in the filtered list on a clashing time
        slot as the second person in the unfiltered list.
         */
        Lesson clashingLesson = new LessonBuilder().withHomeworkSet("Test").buildRecurring();
        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON, clashingLesson);

        assertCommandFailure(lessonAddCommand, model, LessonAddCommand.MESSAGE_CLASHING_LESSON
            + CommandUtil.lessonsToString(model.getClashingLessonsString(clashingLesson)));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Lesson lesson = new LessonBuilder().buildRecurring();
        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(outOfBoundIndex, lesson);

        assertCommandFailure(lessonAddCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        LessonAddCommand lessonAddCommand = prepareLessonAddCommand(outOfBoundIndex, new LessonBuilder().build());

        assertCommandFailure(lessonAddCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Lesson sampleLesson = new LessonBuilder().build();
        LessonAddCommand addSampleLessonCommand = prepareLessonAddCommand(INDEX_FIRST_PERSON,
                sampleLesson);
        LessonAddCommand addSampleLessonCommand2 = prepareLessonAddCommand(INDEX_SECOND_PERSON,
                sampleLesson);

        // same object -> returns true
        assertTrue(addSampleLessonCommand.equals(addSampleLessonCommand));

        // same values -> returns true
        LessonAddCommand addSampleLessonCommandCopy = prepareLessonAddCommand(INDEX_FIRST_PERSON,
                sampleLesson);
        assertTrue(addSampleLessonCommand.equals(addSampleLessonCommandCopy));

        // different types -> returns false
        assertFalse(addSampleLessonCommand.equals(1));

        // null -> returns false
        assertFalse(addSampleLessonCommand.equals(null));

        // different person -> returns false
        assertFalse(addSampleLessonCommand.equals(addSampleLessonCommand2));
    }

    /**
     * Generates a {@code LessonAddCommand} with parameters {@code index} and {@code lesson}.
     */
    private LessonAddCommand prepareLessonAddCommand(Index index, Lesson lesson) {
        LessonAddCommand lessonAddCommand = new LessonAddCommand(index, lesson);
        lessonAddCommand.setDependencies(model, new UndoRedoStack());
        return lessonAddCommand;
    }
}
