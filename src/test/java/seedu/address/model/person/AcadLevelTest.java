package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AcadLevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AcadLevel(null));
    }

    @Test
    public void constructor_invalidAcadLevel_throwsIllegalArgumentException() {
        String invalidAcadLevel = "abcdefghijklmnop"; // 16 characters
        assertThrows(IllegalArgumentException.class, () -> new AcadLevel(invalidAcadLevel));
    }

    @Test
    public void isValidAcadLevel() {
        // null acad level
        assertThrows(NullPointerException.class, () -> AcadLevel.isValidAcadLevel(null));

        // invalid acad level
        assertFalse(AcadLevel.isValidAcadLevel("abcdefghijklmnop")); // 16 characters
        assertFalse(AcadLevel.isValidAcadLevel("abcdefghijklmn p")); // 16 characters including space
        assertFalse(AcadLevel.isValidAcadLevel("-")); // non-alphanumeric character
        assertFalse(AcadLevel.isValidAcadLevel(" ")); // single space character

        // valid acad level
        assertTrue(AcadLevel.isValidAcadLevel("abcdefghijklmn1")); // 15 characters
        assertTrue(AcadLevel.isValidAcadLevel("abcdefghijklm 1")); // 15 characters including space
        assertTrue(AcadLevel.isValidAcadLevel("1")); // one character
    }

    @Test
    void isEmpty() {
        AcadLevel emptyAcadLevel = new AcadLevel("");
        AcadLevel nonEmptyAcadLevel = new AcadLevel("P1");

        assertTrue(emptyAcadLevel.isEmpty());
        assertFalse(nonEmptyAcadLevel.isEmpty());
    }

    @Test
    void testToString() {
        AcadLevel emptyAcadLevel = new AcadLevel("");
        AcadLevel nonEmptyAcadLevel = new AcadLevel("P1");

        assertEquals("", emptyAcadLevel.toString());
        assertEquals("P1", nonEmptyAcadLevel.toString());
    }

    @Test
    public void equals() {
        AcadLevel actualAcadLevel = new AcadLevel("P1");
        AcadLevel actualAcadLevelCopy = new AcadLevel("P1");
        AcadLevel diffAcadLevel = new AcadLevel("S1");

        // null -> false
        assertNotEquals(null, actualAcadLevel);

        // different type -> false
        assertNotEquals(5, actualAcadLevel);

        // different value -> false
        assertNotEquals(diffAcadLevel, actualAcadLevel);

        // same object -> true
        assertEquals(actualAcadLevel, actualAcadLevel);

        // same value -> true
        assertEquals(actualAcadLevelCopy, actualAcadLevel);
    }

    @Test
    public void testHashCode() {
        AcadLevel actualAcadLevel = new AcadLevel("P1");
        AcadLevel actualAcadLevelCopy = new AcadLevel("P1");
        AcadLevel diffAcadLevel = new AcadLevel("S1");

        // different value -> false
        assertNotEquals(diffAcadLevel.hashCode(), actualAcadLevel.hashCode());

        // same object -> true
        assertEquals(actualAcadLevel.hashCode(), actualAcadLevel.hashCode());

        // same value -> true
        assertEquals(actualAcadLevel.hashCode(), actualAcadLevelCopy.hashCode());
    }
}
