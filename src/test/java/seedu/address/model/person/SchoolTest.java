package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    void isEmpty() {
        School emptySchool = new School("");
        School nonEmptySchool = new School("SKSS");

        assertTrue(emptySchool.isEmpty());
        assertFalse(nonEmptySchool.isEmpty());
    }

    @Test
    void testToString() {
        School emptySchool = new School("");
        School nonEmptySchool = new School("SKSS");

        assertEquals("", emptySchool.toString());
        assertEquals("SKSS", nonEmptySchool.toString());
    }

    @Test
    public void equals() {
        School actualSchool = new School("SKSS");
        School actualSchoolCopy = new School("SKSS");
        School diffSchool = new School("SJSS");

        // null -> false
        assertNotEquals(null, actualSchool);

        // different type -> false
        assertNotEquals(5, actualSchool);

        // different value -> false
        assertNotEquals(diffSchool, actualSchool);

        // same object -> true
        assertEquals(actualSchool, actualSchool);

        // same value -> true
        assertEquals(actualSchoolCopy, actualSchool);
    }

    @Test
    public void testHashCode() {
        School actualSchool = new School("SKSS");
        School actualSchoolCopy = new School("SKSS");
        School diffSchool = new School("SJSS");

        // different value -> false
        assertNotEquals(diffSchool.hashCode(), actualSchool.hashCode());

        // same object -> true
        assertEquals(actualSchool.hashCode(), actualSchool.hashCode());

        // same value -> true
        assertEquals(actualSchool.hashCode(), actualSchoolCopy.hashCode());
    }
}
