package seedu.address.model.person;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FeeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Fee(null));
    }

    @Test
    public void constructor_invalidFee_throwsIllegalArgumentException() {
        String invalidFee = " ";
    }
}
