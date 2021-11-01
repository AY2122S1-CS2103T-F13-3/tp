package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASHING_TIME_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_NEXT_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PREV_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NON_CLASHING_TIME_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.LessonEditCommand.MESSAGE_CLASHING_LESSON;
import static seedu.address.logic.commands.LessonEditCommand.MESSAGE_INVALID_CANCEL_DATE;
import static seedu.address.logic.commands.LessonEditCommand.MESSAGE_INVALID_DATE_RANGE;
import static seedu.address.logic.commands.LessonEditCommand.MESSAGE_INVALID_UNCANCEL_DATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CommandUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

class LessonEditCommandTest {

    private static final String TESTING_SUBJECT = "TESTING";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Lesson editedLesson = new LessonBuilder().build();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(new LessonBuilder().withSubject(TESTING_SUBJECT).build())
                .build();
        Lesson formerLesson = personBeforeLessonEdit
            .getLessons().stream().collect(Collectors.toList())
            .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit
        Person personAfterLessonEdit = new PersonBuilder(firstPerson).withLessons(editedLesson).build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage =
                String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS, personAfterLessonEdit.getName(),
                        formerLesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Lesson editedLesson = new LessonBuilder()
            .withSubject(VALID_SUBJECT)
            .withHomeworkSet(VALID_HOMEWORK_POETRY)
            .build();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(new LessonBuilder().withSubject(TESTING_SUBJECT).build())
            .build();
        Lesson formerLesson = personBeforeLessonEdit
            .getLessons().stream().collect(Collectors.toList())
            .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit
        Person personAfterLessonEdit = new PersonBuilder(firstPerson).withLessons(editedLesson).build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage =
                String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS, personAfterLessonEdit.getName(),
                    formerLesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(new LessonBuilder().withSubject(TESTING_SUBJECT).build())
            .build();
        Lesson formerLesson = personBeforeLessonEdit
            .getLessons().stream().collect(Collectors.toList())
            .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, new EditLessonDescriptor());

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                personBeforeLessonEdit.getName(), formerLesson, formerLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Lesson editedLesson = new LessonBuilder().withHomeworkSet("Test 4").buildRecurring();

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personBeforeLessonEdit = new PersonBuilder(personInFilteredList)
            .withLessons(new LessonBuilder().buildRecurring())
            .build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();

        Lesson formerLesson = personBeforeLessonEdit
                .getLessons().stream().collect(Collectors.toList())
                .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(personInFilteredList, personBeforeLessonEdit); // Ensure at least one lesson to edit
        Person personAfterLessonEdit = new PersonBuilder(personInFilteredList).withLessons(editedLesson).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                personAfterLessonEdit.getName(),
                formerLesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withSubject(TESTING_SUBJECT).build();
        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(outOfBoundIndex, INDEX_FIRST_LESSON, descriptor);

        assertCommandFailure(lessonEditCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LessonEditCommand lessonEditCommand = prepareLessonEditCommand(outOfBoundIndex, INDEX_FIRST_LESSON,
            new EditLessonDescriptorBuilder().withSubject(TESTING_SUBJECT).build());

        assertCommandFailure(lessonEditCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withSubject(TESTING_SUBJECT).build();
        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LESSON, descriptor);

        assertCommandFailure(lessonEditCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDateRangeMakeUp_success() {
        // Makeup lesson
        Lesson lesson = new LessonBuilder()
            .withDate(VALID_DATE_FUTURE)
            .withEndDate(VALID_DATE_FUTURE)
            .build();

        Lesson editedLesson = new LessonBuilder().withDate(VALID_DATE_PAST).build();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(lesson)
            .build();
        Person personAfterLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(editedLesson).build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lesson)
            .withDate(VALID_DATE_PAST).build();

        LessonEditCommand lessonEditCommand =
            prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
            personAfterLessonEdit.getName(),
            lesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validDateRangeRecurring_success() {
        // Recurring lesson
        Lesson lesson = new LessonBuilder()
            .withDate(VALID_DATE_FUTURE)
            .withEndDate(VALID_DATE_FUTURE)
            .buildRecurring();

        Lesson editedLesson = new LessonBuilder()
            .withDate(VALID_DATE_PAST)
            .withEndDate(VALID_DATE_FUTURE)
            .buildRecurring();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(lesson)
            .build();
        Person personAfterLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(editedLesson).build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lesson)
            .withDate(VALID_DATE_PAST).build();

        LessonEditCommand lessonEditCommand =
            prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
            personAfterLessonEdit.getName(),
            lesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateRange_failure() {
        // Recurring lesson
        Lesson lesson = new LessonBuilder()
            .withDate(VALID_DATE_FUTURE)
            .buildRecurring();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(lesson)
            .build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lesson)
            .withEndDate(VALID_DATE_PAST).build();

        LessonEditCommand lessonEditCommand =
            prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LESSON, descriptor);

        assertThrows(CommandException.class, () -> lessonEditCommand.execute(), MESSAGE_INVALID_DATE_RANGE);
    }

    @Test
    public void execute_nonClashingEditedLessonDifferentDateRange_success() {
        Lesson existingLesson = new LessonBuilder()
            .withDate(VALID_DATE_PREV_MON)
            .withEndDate(VALID_DATE_MON)
            .buildRecurring();

        Lesson lessonBeforeEdit = new LessonBuilder()
            .withDate(VALID_DATE_FUTURE)
            .buildRecurring();

        Lesson lessonAfterEdit = new LessonBuilder()
            .withDate(VALID_DATE_TUE)
            .buildRecurring();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(existingLesson, lessonBeforeEdit)
            .build();

        Person personAfterLessonEdit = new PersonBuilder(firstPerson)
            .withLessons(existingLesson, lessonAfterEdit)
            .build();

        model.setPerson(firstPerson, personBeforeLessonEdit);

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lessonBeforeEdit)
            .withDate(VALID_DATE_TUE).build();

        LessonEditCommand lessonEditCommand =
            prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
            personAfterLessonEdit.getName(), lessonBeforeEdit, lessonAfterEdit);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
            personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clashingEditedLesson_failure() {
        Lesson existingLesson = new LessonBuilder()
                .withTimeRange(VALID_TIME_RANGE)
                .build();
        Lesson lessonBeforeEdit = new LessonBuilder().withTimeRange(VALID_NON_CLASHING_TIME_RANGE).build();

        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(existingLesson, lessonBeforeEdit)
                .build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(existingLesson)
                .withTimeRange(VALID_CLASHING_TIME_RANGE).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LESSON, descriptor);

        assertThrows(CommandException.class, () -> lessonEditCommand.execute(), MESSAGE_CLASHING_LESSON);
    }

    @Test
    public void execute_validCancelDateRecurring_success() {
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(new LessonBuilder().withDate(VALID_DATE_MON).buildRecurring())
                .build();
        Lesson formerLesson = personBeforeLessonEdit
                .getLessons().stream().collect(Collectors.toList())
                .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        Lesson editedLesson = new LessonBuilder(formerLesson).withCancelledDatesSet(VALID_DATE_NEXT_MON)
                .buildRecurring();
        Person personAfterLessonEdit = new PersonBuilder(firstPerson).withLessons(editedLesson).build();
        EditLessonDescriptor descriptor =
                new EditLessonDescriptorBuilder().withCancelDates(VALID_DATE_NEXT_MON).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                personAfterLessonEdit.getName(), formerLesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCancelDateRecurring_failure() {
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(new LessonBuilder().withDate(VALID_DATE_TUE).buildRecurring())
                .build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        // cancel date is not on the new recurring lesson day
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withDate(VALID_DATE_MON)
                .withCancelDates(VALID_DATE_TUE).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        assertCommandFailure(lessonEditCommand, model, String.format(MESSAGE_INVALID_CANCEL_DATE, VALID_DATE_TUE));
    }

    @Test
    public void execute_validUnCancelDateRecurring_success() {
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson).withLessons(new LessonBuilder()
                .withDate(VALID_DATE_MON).withCancelledDatesSet(VALID_DATE_NEXT_MON).buildRecurring())
                .build();
        Lesson formerLesson = personBeforeLessonEdit
                .getLessons().stream().collect(Collectors.toList())
                .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withUncancelDates(VALID_DATE_NEXT_MON).build();

        Lesson editedLesson = new LessonBuilder().withDate(VALID_DATE_MON).buildRecurring();
        Person personAfterLessonEdit = new PersonBuilder(firstPerson).withLessons(editedLesson).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                personAfterLessonEdit.getName(), formerLesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUncancelDateRecurring_failure() {
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(new LessonBuilder()
                        .withDate(VALID_DATE_PREV_MON)
                        .withCancelledDatesSet(VALID_DATE_MON).buildRecurring())
                .build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        // uncancel date is not a valid cancelled date
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withUncancelDates(VALID_DATE_NEXT_MON).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        assertCommandFailure(lessonEditCommand, model,
                String.format(MESSAGE_INVALID_UNCANCEL_DATE, VALID_DATE_NEXT_MON));
    }

    @Test
    public void execute_uncancelDateRecurringClash_failure() {
        Lesson lessonWithCancelled = new LessonBuilder().withDate(VALID_DATE_MON)
                .withCancelledDatesSet(VALID_DATE_NEXT_MON).buildRecurring();
        Lesson makeupLesson = new LessonBuilder().withDate(VALID_DATE_NEXT_MON).build();
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson)
                .withLessons(makeupLesson, lessonWithCancelled)
                .build();

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        // uncancel date is not a valid cancelled date
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withUncancelDates(VALID_DATE_NEXT_MON).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LESSON, descriptor);

        assertCommandFailure(lessonEditCommand, model, MESSAGE_CLASHING_LESSON
                + CommandUtil.lessonsToString(model.getClashingLessonsString(makeupLesson)));
    }

    @Test
    public void execute_changeStartDate_filterCancelledDates() {
        // if start date changes, only valid cancelled dates should remain
        Person personBeforeLessonEdit = new PersonBuilder(firstPerson).withLessons(new LessonBuilder()
                .withDate(VALID_DATE_PREV_MON).withCancelledDatesSet(VALID_DATE_PREV_MON, VALID_DATE_MON)
                .buildRecurring())
                .build();
        Lesson formerLesson = personBeforeLessonEdit
                .getLessons().stream().collect(Collectors.toList())
                .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonEdit); // Ensure at least one lesson to edit

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withDate(VALID_DATE_MON).build();

        // VALID_DATE_PREV_MON is removed, left with VALID_DATE_MON
        Lesson editedLesson = new LessonBuilder().withDate(VALID_DATE_MON).withCancelledDatesSet(VALID_DATE_MON)
                .buildRecurring();
        Person personAfterLessonEdit = new PersonBuilder(firstPerson).withLessons(editedLesson).build();

        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                personAfterLessonEdit.getName(), formerLesson, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                personAfterLessonEdit);

        assertCommandSuccess(lessonEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Lesson sampleLesson = new LessonBuilder().build();
        EditLessonDescriptor copyDescriptor = new EditLessonDescriptorBuilder(sampleLesson).build();
        LessonEditCommand lessonEditCommand =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, copyDescriptor);
        LessonEditCommand lessonEditCommand2 =
                prepareLessonEditCommand(INDEX_SECOND_PERSON, INDEX_FIRST_LESSON, copyDescriptor);

        // same object -> returns true
        assertTrue(lessonEditCommand.equals(lessonEditCommand));

        // same values -> returns true
        LessonEditCommand lessonEditCommandCopy =
                prepareLessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, copyDescriptor);
        assertTrue(lessonEditCommand.equals(lessonEditCommandCopy));

        // different types -> returns false
        assertFalse(lessonEditCommand.equals(1));

        // null -> returns false
        assertFalse(lessonEditCommand.equals(null));

        // different person -> returns false
        assertFalse(lessonEditCommand.equals(lessonEditCommand2));
    }

    /**
     * Generates a {@code LessonEditCommand} with parameters {@code index}, {@code indexToEdit}
     * and {@code editLessonDescriptor}.
     */
    private LessonEditCommand prepareLessonEditCommand(Index index, Index indexToEdit,
            EditLessonDescriptor editLessonDescriptor) {
        LessonEditCommand lessonEditCommand = new LessonEditCommand(index, indexToEdit, editLessonDescriptor);
        lessonEditCommand.setDependencies(model, new UndoRedoStack());
        return lessonEditCommand;
    }
}
