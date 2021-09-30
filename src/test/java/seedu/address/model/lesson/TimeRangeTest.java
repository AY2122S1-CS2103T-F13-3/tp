package seedu.address.model.lesson;

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

        // invalid time range
        assertFalse(TimeRange.isValidTimeRange("1200-1000")); // end time earlier than start time
        assertFalse(TimeRange.isValidTimeRange("1099-1200")); // not a time value
        assertFalse(TimeRange.isValidTimeRange("0000-1200")); // before 8am
        assertFalse(TimeRange.isValidTimeRange("2000-2359")); // after 10pm

        // valid time
        assertTrue(TimeRange.isValidTimeRange("1300-1400"));
    }

    @Test
    public void isClashing_timeRange() {
        TimeRange timeRange = new TimeRange("0900-1200");
        TimeRange clashingTimeRange = new TimeRange("0900-1000");
        TimeRange nonClashingTimeRange = new TimeRange("1300-1500");

        // time ranges not clashing
        assertFalse(timeRange.isClashing(nonClashingTimeRange));

        // time ranges clashing
        assertTrue(timeRange.isClashing(clashingTimeRange));
    }
}
