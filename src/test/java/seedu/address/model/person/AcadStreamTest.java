package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AcadStreamTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AcadStream(null));
    }

    @Test
    void isEmpty() {
        AcadStream emptyAcadStream = new AcadStream("");
        AcadStream nonEmptyAcadStream = new AcadStream("O-Level");

        assertTrue(emptyAcadStream.isEmpty());
        assertFalse(nonEmptyAcadStream.isEmpty());
    }

    @Test
    void testToString() {
        AcadStream emptyAcadStream = new AcadStream("");
        AcadStream nonEmptyAcadStream = new AcadStream("O-Level");

        assertEquals("", emptyAcadStream.toString());
        assertEquals("O-Level", nonEmptyAcadStream.toString());
    }

    @Test
    public void equals() {
        AcadStream actualAcadStream = new AcadStream("O-Level");
        AcadStream actualAcadStreamCopy = new AcadStream("O-Level");
        AcadStream diffAcadStream = new AcadStream("SJSS");

        // null -> false
        assertNotEquals(null, actualAcadStream);

        // different type -> false
        assertNotEquals(5, actualAcadStream);

        // different value -> false
        assertNotEquals(diffAcadStream, actualAcadStream);

        // same object -> true
        assertEquals(actualAcadStream, actualAcadStream);

        // same value -> true
        assertEquals(actualAcadStreamCopy, actualAcadStream);
    }

    @Test
    public void testHashCode() {
        AcadStream actualAcadStream = new AcadStream("O-Level");
        AcadStream actualAcadStreamCopy = new AcadStream("O-Level");
        AcadStream diffAcadStream = new AcadStream("A-Level");

        // different value -> false
        assertNotEquals(diffAcadStream.hashCode(), actualAcadStream.hashCode());

        // same object -> true
        assertEquals(actualAcadStream.hashCode(), actualAcadStream.hashCode());

        // same value -> true
        assertEquals(actualAcadStream.hashCode(), actualAcadStreamCopy.hashCode());
    }
}
