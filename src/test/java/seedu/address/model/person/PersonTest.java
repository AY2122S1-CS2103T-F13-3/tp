package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_STREAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_STREAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ZOOM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalTags.TAG_FORGETFUL;
import static seedu.address.testutil.TypicalTags.TAG_ZOOM;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Name validName = new Name(VALID_NAME_AMY);
        Address validAddress = new Address(VALID_ADDRESS_AMY);
        Phone validPhone = new Phone(VALID_PHONE_AMY);
        Email validEmail = new Email(VALID_EMAIL_AMY);
        School validSchool = new School(VALID_SCHOOL_AMY);
        AcadStream validAcadStream = new AcadStream(VALID_ACAD_STREAM_AMY);
        AcadLevel validAcadLevel = new AcadLevel(VALID_ACAD_LEVEL_AMY);
        Remark validRemark = new Remark(VALID_REMARK_AMY);
        Set<Tag> validTagSet = new HashSet<>();
        validTagSet.add(new Tag(VALID_TAG_ZOOM));
        Set<Lesson> validLessonSet = new TreeSet<>();
        validLessonSet.add(new LessonBuilder().build());


        // null name
        assertThrows(NullPointerException.class, () -> new Person(null, validPhone, validEmail, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null phone
        assertThrows(NullPointerException.class, () -> new Person(validName, null, validEmail, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null email
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, null, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null parent phone
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, null,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null parent email
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                null, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null address
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, null, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null school
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, validAddress, null, validAcadStream, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null acad stream
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, validAddress, validSchool, null, validAcadLevel, validRemark,
                validTagSet, validLessonSet));

        // null acad level
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, null, validRemark,
                validTagSet, validLessonSet));

        // null remark
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, null,
                validTagSet, validLessonSet));

        // null tag set
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                null, validLessonSet));

        // null lesson
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validPhone,
                validEmail, validAddress, validSchool, validAcadStream, validAcadLevel, validRemark,
                validTagSet, null));

    }


    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void hasContactField() {
        // all contact fields present -> returns true
        assertTrue(ALICE.hasContactField());

        // some contact fields not present -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(EMPTY_FIELD).withEmail(EMPTY_FIELD).build();
        assertTrue(editedAlice.hasContactField());

        //all contact fields missing -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(EMPTY_FIELD).withEmail(EMPTY_FIELD)
                .withParentPhone(EMPTY_FIELD).withParentEmail(EMPTY_FIELD).build();
        assertFalse(editedAlice.hasContactField());
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withParentPhone(VALID_PARENT_PHONE_BOB).withParentEmail(VALID_PARENT_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_ZOOM).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different parent phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withParentPhone(VALID_PARENT_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different parent email -> returns false
        editedAlice = new PersonBuilder(ALICE).withParentEmail(VALID_PARENT_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different school -> returns false
        editedAlice = new PersonBuilder(ALICE).withSchool(VALID_SCHOOL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different acad level -> returns false
        editedAlice = new PersonBuilder(ALICE).withAcadLevel(VALID_ACAD_LEVEL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different acad stream -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ACAD_STREAM_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_ZOOM).build();
        assertFalse(ALICE.equals(editedAlice));

        // different remark -> returns false
        editedAlice = new PersonBuilder(ALICE).withRemark(VALID_REMARK_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void testHashCode() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // same object -> returns true
        assertEquals(ALICE.hashCode(), ALICE.hashCode());

        // different person -> returns false
        assertNotEquals(BOB.hashCode(), ALICE.hashCode());

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different parent phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withParentPhone(VALID_PARENT_PHONE_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different parent email -> returns false
        editedAlice = new PersonBuilder(ALICE).withParentEmail(VALID_PARENT_EMAIL_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different school -> returns false
        editedAlice = new PersonBuilder(ALICE).withSchool(VALID_SCHOOL_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different acad level -> returns false
        editedAlice = new PersonBuilder(ALICE).withAcadLevel(VALID_ACAD_LEVEL_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different acad stream -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ACAD_STREAM_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_ZOOM).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());

        // different remark -> returns false
        editedAlice = new PersonBuilder(ALICE).withRemark(VALID_REMARK_BOB).build();
        assertNotEquals(editedAlice.hashCode(), ALICE.hashCode());
    }

    @Test
    public void addTagsToTagList_nullTagList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ALICE.addTagsToTagList(null));
    }

    @Test
    public void addTagsToTagList_success() {
        UniqueTagList expectedTagList = new UniqueTagList();
        UniqueTagList actualTagList = new UniqueTagList();

        BOB.addTagsToTagList(actualTagList);
        expectedTagList.addTag(TAG_ZOOM);
        expectedTagList.addTag(TAG_FORGETFUL);
        assertEquals(expectedTagList, actualTagList);
    }

    @Test
    public void removeTagsFromTagList_nullTagList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ALICE.removeTagsFromTagList(null));
    }

    @Test
    public void removeTagsToTagList_nonEmptyResultList_success() {
        UniqueTagList expectedTagList = new UniqueTagList();
        UniqueTagList actualTagList = new UniqueTagList();
        actualTagList.addTag(TAG_FORGETFUL);
        actualTagList.addTag(TAG_ZOOM);

        ALICE.removeTagsFromTagList(actualTagList);
        expectedTagList.addTag(TAG_ZOOM);
        assertEquals(expectedTagList, actualTagList);
    }

    @Test
    public void removeTagsToTagList_emptyResultList_success() {
        UniqueTagList expectedTagList = new UniqueTagList();
        UniqueTagList actualTagList = new UniqueTagList();
        actualTagList.addTag(TAG_FORGETFUL);

        ALICE.removeTagsFromTagList(actualTagList);
        assertEquals(expectedTagList, actualTagList);
    }
}
