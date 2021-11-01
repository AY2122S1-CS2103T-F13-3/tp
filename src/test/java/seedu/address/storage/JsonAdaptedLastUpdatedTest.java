package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.LastUpdatedDateUtil.INVALID_LAST_ADDED_DATE_TIME;
import static seedu.address.testutil.LastUpdatedDateUtil.VALID_LAST_UPDATED_DATE_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LastUpdatedDate;

class JsonAdaptedLastUpdatedTest {
    public static final String MISSING_MESSAGE_FORMAT = "Last Updated Date is missing!";

    private static final LastUpdatedDate LAST_UPDATED_DATE = new LastUpdatedDate(VALID_LAST_UPDATED_DATE_TIME);

    @Test
    public void toModelType_validLastUpdatedDate_returnUpdatedDate() throws IllegalValueException {
        JsonAdaptedLastUpdated lastUpdated = new JsonAdaptedLastUpdated(LAST_UPDATED_DATE);
        assertEquals(LAST_UPDATED_DATE, lastUpdated.toModelType());
    }

    @Test
    public void toModelType_invalidLastUpdatedDate_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedLastUpdated lastUpdated = new JsonAdaptedLastUpdated(INVALID_LAST_ADDED_DATE_TIME);
        String expectedMessage = LastUpdatedDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lastUpdated::toModelType);
    }

    @Test
    public void toModelType_nullLastUpdatedDate_throwsIllegalValueException() {
        JsonAdaptedLastUpdated lastUpdated = new JsonAdaptedLastUpdated((String) null);
        String expectedMessage = MISSING_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lastUpdated::toModelType);
    }
}
