package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertThrows(IllegalArgumentException.class, () -> new Fee(invalidFee));
    }

    @Test
    public void isValidFee() {
        // null fee
        assertThrows(NullPointerException.class, () -> Fee.isValidFee(null));

        // invalid fees
        assertFalse(Fee.isValidFee(" ")); // spaces only
        assertFalse(Fee.isValidFee(".12")); // no dollars before decimal
        assertFalse(Fee.isValidFee("50.")); // no cents after decimal
        assertFalse(Fee.isValidFee("43..21")); // more than one decimal
        assertFalse(Fee.isValidFee("3.999")); // more than two dp
        assertFalse(Fee.isValidFee("a")); // non-numeric in dollars
        assertFalse(Fee.isValidFee("a.a")); // non-numeric in dollars and cents
        assertFalse(Fee.isValidFee("50cents")); // non-numeric with digits
        assertFalse(Fee.isValidFee("99,999")); // commas within digits
        assertFalse(Fee.isValidFee("10+10")); // non-numeric, non-period within digits
        assertFalse(Fee.isValidFee("$50.05")); // starting with dollar sign
        assertFalse(Fee.isValidFee("99 999")); // spaces within digits

        // valid fees
        assertTrue(Fee.isValidFee("")); // empty string
        assertTrue(Fee.isValidFee("0")); // zero dollars
        assertTrue(Fee.isValidFee("0.0")); // zero dollars and zero cents
        assertTrue(Fee.isValidFee("123")); // only dollars
        assertTrue(Fee.isValidFee("0.50")); // zero dollars and 50 cents
        assertTrue(Fee.isValidFee("765.4")); // dollars and cents to 1 decimal places
        assertTrue(Fee.isValidFee("99999.99")); // dollars and cents to 2 decimal places
        assertTrue(Fee.isValidFee("00123.45")); // leading zeroes
        assertTrue(Fee.isValidFee("124293842033123.99")); // large fee
    }
}
