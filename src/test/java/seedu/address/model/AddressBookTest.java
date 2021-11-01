package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ZOOM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.LastUpdatedDateUtil.VALID_LAST_UPDATED_DATE_TIME;
import static seedu.address.testutil.TypicalLessons.MAKEUP_LESSON;
import static seedu.address.testutil.TypicalLessons.RECURRING_LESSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.TAG_ZOOM;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.ClashingLessonException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_ZOOM)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withClashingLessons_throwsClashingLessonException() {
        // Two Lessons with clashing time range fields
        Person editedAlice = new PersonBuilder(ALICE).withLessons(RECURRING_LESSON).build();
        Person editedBob = new PersonBuilder(BOB).withLessons(RECURRING_LESSON).build();
        List<Person> newPersons = Arrays.asList(editedAlice, editedBob);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(ClashingLessonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_ZOOM)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasClashingLesson_noLessonInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasClashingLesson(RECURRING_LESSON));
    }

    @Test
    public void hasClashingLesson_lessonInAddressBook_returnsTrue() {
        Person aliceWithLesson = new PersonBuilder(ALICE).withLessons(RECURRING_LESSON).build();
        addressBook.addPerson(aliceWithLesson);
        assertTrue(addressBook.hasClashingLesson(RECURRING_LESSON));
    }

    @Test
    public void hasClashingLesson_lessonWithClashingTimeRangeInAddressBook_returnsTrue() {
        Person aliceWithLesson = new PersonBuilder(ALICE).withLessons(RECURRING_LESSON).build();
        addressBook.addPerson(aliceWithLesson);
        assertTrue(addressBook.hasClashingLesson(MAKEUP_LESSON));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getUpcomingLessons_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getUpcomingLessons().remove(0));
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTagList().remove(0));
    }

    @Test
    public void getTagCounter_modifyMap_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTagCounter().put(TAG_ZOOM, 1));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final LastUpdatedDate lastUpdatedDate = new LastUpdatedDate(VALID_LAST_UPDATED_DATE_TIME);

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public LastUpdatedDate getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableMap<Tag, Integer> getTagCounter() {
            return FXCollections.observableHashMap();
        }
    }

}
