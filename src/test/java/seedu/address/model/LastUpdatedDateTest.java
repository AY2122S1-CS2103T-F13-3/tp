package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class LastUpdatedDateTest {
    @Test
    public void lastUpdatedDate_constructorNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastUpdatedDate(null));
    }

    @Test
    public void isValidLastUpdatedDate() {
        // null date
        assertThrows(NullPointerException.class, () -> LastUpdatedDate.isValidLastUpdatedDateTime(null));

        // invalid date
        assertFalse(LastUpdatedDate.isValidLastUpdatedDateTime("")); // empty string
        assertFalse(LastUpdatedDate.isValidLastUpdatedDateTime(" ")); // spaced only
        assertFalse(LastUpdatedDate.isValidLastUpdatedDateTime("2021-02-31T00:00")); // no such date
        assertFalse(LastUpdatedDate.isValidLastUpdatedDateTime("2021-13-23T00:00")); // invalid month

        //valid date
        assertTrue(LastUpdatedDate.isValidLastUpdatedDateTime("2020-02-29T00:00")); // leap year
        assertTrue(LastUpdatedDate.isValidLastUpdatedDateTime("2021-10-23T00:00"));;
    }
}
