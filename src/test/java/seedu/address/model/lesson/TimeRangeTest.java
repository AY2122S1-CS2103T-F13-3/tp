package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeRangeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TimeRange(null));
    }

    @Test
    public void constructor_invalidTimeRange_throwsIllegalArgumentException() {
        String invalidTimeRange = " ";
        assertThrows(IllegalArgumentException.class, () ->
            new TimeRange(invalidTimeRange));
    }

    @Test
    public void isValidTimeRange() {
        // null date
        assertThrows(NullPointerException.class, () -> TimeRange.isValidTimeRange(null));

        // invalid time ranges
        assertFalse(TimeRange.isValidTimeRange("1200-1000")); // end time earlier than start time
        assertFalse(TimeRange.isValidTimeRange("1099-1200")); // not a time value
        assertFalse(TimeRange.isValidTimeRange("0000-1200")); // before 8am
        assertFalse(TimeRange.isValidTimeRange("2000-2359")); // after 10pm
        assertFalse(TimeRange.isValidTimeRange("2000:2359")); // invalid format
        assertFalse(TimeRange.isValidTimeRange("")); // empty string

        // valid time ranges
        assertTrue(TimeRange.isValidTimeRange("1300-1400"));
        assertTrue(TimeRange.isValidTimeRange("0800-1400"));
    }

    @Test
    public void isClashing_timeRange() {
        TimeRange timeRange = new TimeRange("0900-1200");
        TimeRange nonClashingTimeRange = new TimeRange("1300-1500");
        TimeRange startDuringEndAfter = new TimeRange("1030-1300");
        TimeRange startBeforeEndDuring = new TimeRange("0900-1000");
        TimeRange startBeforeEndAfter = new TimeRange("0800-1300");
        TimeRange startDuringEndDuring = new TimeRange("1000-1100");
        TimeRange duplicateTimeRange = new TimeRange("0900-1200");

        // time ranges not clashing
        assertFalse(timeRange.isClashing(nonClashingTimeRange));

        // time ranges clashing
        assertTrue(timeRange.isClashing(startDuringEndAfter));
        assertTrue(timeRange.isClashing(startBeforeEndDuring));
        assertTrue(timeRange.isClashing(startBeforeEndAfter));
        assertTrue(timeRange.isClashing(startDuringEndDuring));
        assertTrue(timeRange.isClashing(duplicateTimeRange));
    }

    @Test
    public void compareTo() {
        TimeRange timeRange = new TimeRange("0900-1200");
        TimeRange laterNonClashingTimeRange = new TimeRange("1300-1500");
        TimeRange startBeforeEndDuring = new TimeRange("0900-1000");
        TimeRange startDuringEndAfter = new TimeRange("1030-1300");

        // clashing time ranges
        assertEquals(0, timeRange.compareTo(startBeforeEndDuring));
        assertEquals(0, timeRange.compareTo(startDuringEndAfter));

        // later time range
        assertEquals(-1, timeRange.compareTo(laterNonClashingTimeRange));

        // earlier time range
        assertEquals(1, laterNonClashingTimeRange.compareTo(timeRange));
    }
}
