package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OUTSTANDING_FEES_AFTER_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Money;
import seedu.address.model.person.Person;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

class PaidCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    private Money payment = new Money(VALID_PAYMENT);

    @Test
    public void constructor_nullAmount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> preparePaidCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_LESSON, null));
    }

    @Test
    public void execute_validPersonWithoutExistingLessons_failure() {
        PaidCommand paidCommand = preparePaidCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, payment);
        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PaidCommand paidCommand = preparePaidCommand(outOfBoundIndex, INDEX_FIRST_LESSON, payment);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        PaidCommand paidCommand = preparePaidCommand(outOfBoundIndex, INDEX_FIRST_LESSON, payment);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unfilteredList_success() {
        Lesson paidLesson = new LessonBuilder().withOutstandingFees(VALID_OUTSTANDING_FEES_AFTER_PAYMENT).build();

        Person personBeforeLessonPaid = new PersonBuilder(firstPerson)
                .withLessons(new LessonBuilder().build()).build();

        Lesson formerLesson = personBeforeLessonPaid
                .getLessons().stream().collect(Collectors.toList())
                .get(INDEX_FIRST_LESSON.getZeroBased());

        model.setPerson(firstPerson, personBeforeLessonPaid);
        Person personAfterLessonPaid = new PersonBuilder(firstPerson).withLessons(paidLesson).build();

        Money payment = new Money(VALID_PAYMENT);
        PaidCommand paidCommand = preparePaidCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, payment);

        String expectedMessage = String.format(PaidCommand.MESSAGE_PAID_LESSON_SUCCESS, personAfterLessonPaid.getName(),
                formerLesson, paidLesson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
               personAfterLessonPaid);

        assertCommandSuccess(paidCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a {@code PaidCommand} with parameters {@code index}, {@code indexToEdit}
     * and {@code payment}.
     */
    private PaidCommand preparePaidCommand(Index index, Index indexToEdit, Money payment) {
        PaidCommand paidCommand = new PaidCommand(index, indexToEdit, payment);
        paidCommand.setDependencies(model, new UndoRedoStack());
        return paidCommand;
    }
}
