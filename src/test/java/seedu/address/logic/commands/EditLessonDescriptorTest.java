package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MAKEUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.testutil.EditLessonDescriptorBuilder;

public class EditLessonDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditLessonDescriptor descriptorWithSameValues = new EditLessonDescriptor(DESC_RECURRING);
        assertTrue(DESC_RECURRING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_RECURRING.equals(DESC_RECURRING));

        // null -> returns false
        assertFalse(DESC_RECURRING.equals(null));

        // different types -> returns false
        assertFalse(DESC_RECURRING.equals(5));

        // different values -> returns false
        assertFalse(DESC_RECURRING.equals(DESC_MAKEUP));

        // different date -> returns false
        EditLessonDescriptor editedRecurring =
            new EditLessonDescriptorBuilder(DESC_RECURRING).withDate(VALID_DATE_TUE).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different end date -> returns false
        editedRecurring = new EditLessonDescriptorBuilder(DESC_RECURRING).withEndDate(VALID_DATE_FUTURE).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different time range-> returns false
        String testTimeRange = "1900-2000";
        editedRecurring = new EditLessonDescriptorBuilder(DESC_RECURRING).withTimeRange(testTimeRange).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different rate -> returns false
        String testRate = "100";
        editedRecurring = new EditLessonDescriptorBuilder(DESC_RECURRING).withLessonRates(testRate).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different outstanding fees -> returns false
        String testOutstandingFees = "200.50";
        editedRecurring = new EditLessonDescriptorBuilder(DESC_RECURRING)
                .withOutstandingFees(testOutstandingFees).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different subject -> returns false
        String testSubject = "Gastronomy";
        editedRecurring = new EditLessonDescriptorBuilder(DESC_RECURRING).withSubject(testSubject).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different homework -> returns false
        String testingHomework = "testtest";
        editedRecurring = new EditLessonDescriptorBuilder(DESC_RECURRING).withHomeworkSet(testingHomework).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));
    }
}
