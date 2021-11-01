package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class MoneyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Money(null));
    }

    @Test
    public void constructor_invalidLessonRates_throwsIllegalArgumentException() {
        String invalidAmount = " ";
        assertThrows(IllegalArgumentException.class, () -> new Money(invalidAmount));
    }

    @Test
    public void isValidMonetaryField() {
        // null fee
        assertThrows(NullPointerException.class, () -> Money.isValidMonetaryField(null));

        // invalid fees
        assertFalse(Money.isValidMonetaryField("")); // empty string
        assertFalse(Money.isValidMonetaryField(" ")); // spaces only
        assertFalse(Money.isValidMonetaryField(".12")); // no dollars before decimal
        assertFalse(Money.isValidMonetaryField("50.")); // no cents after decimal
        assertFalse(Money.isValidMonetaryField("43..21")); // more than one decimal
        assertFalse(Money.isValidMonetaryField("3.999")); // more than two dp
        assertFalse(Money.isValidMonetaryField("a")); // non-numeric in dollars
        assertFalse(Money.isValidMonetaryField("a.a")); // non-numeric in dollars and cents
        assertFalse(Money.isValidMonetaryField("50cents")); // non-numeric with digits
        assertFalse(Money.isValidMonetaryField("99,999")); // commas within digits
        assertFalse(Money.isValidMonetaryField("10+10")); // non-numeric, non-period within digits
        assertFalse(Money.isValidMonetaryField("$50.05")); // starting with dollar sign
        assertFalse(Money.isValidMonetaryField("99 999")); // spaces within digits
        assertFalse(Money.isValidMonetaryField("-100.00")); // negative value

        // valid fees
        assertTrue(Money.isValidMonetaryField("0")); // zero dollars
        assertTrue(Money.isValidMonetaryField("0.0")); // zero dollars and zero cents
        assertTrue(Money.isValidMonetaryField("123")); // only dollars
        assertTrue(Money.isValidMonetaryField("0.50")); // zero dollars and 50 cents
        assertTrue(Money.isValidMonetaryField("765.4")); // dollars and cents to 1 decimal places
        assertTrue(Money.isValidMonetaryField("99999.99")); // dollars and cents to 2 decimal places
        assertTrue(Money.isValidMonetaryField("00123.45")); // leading zeroes
        assertTrue(Money.isValidMonetaryField("124293842033123.99")); // large fee
    }
}
