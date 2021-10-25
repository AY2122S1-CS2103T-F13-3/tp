package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OUTSTANDING_FEES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAYMENT;
import static seedu.address.testutil.Assert.assertThrows;


import org.junit.jupiter.api.Test;

class OutstandingFeesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new OutstandingFees(null));
    }

    @Test
    public void constructor_invalidFee_throwsIllegalArgumentException() {
        String invalidOutstandingFees = " ";

        assertThrows(IllegalArgumentException.class, () -> new OutstandingFees(invalidOutstandingFees));
        assertThrows(IllegalArgumentException.class, () ->
                new OutstandingFees(invalidOutstandingFees));
    }

    @Test
    public void pay() {
        Money payment = new Money(VALID_PAYMENT);
        float afterPayment = Float.parseFloat(VALID_OUTSTANDING_FEES) - Float.parseFloat(VALID_PAYMENT);
        String afterPaymentInString = Float.toString(afterPayment);

        OutstandingFees actual = new OutstandingFees(VALID_OUTSTANDING_FEES);
        OutstandingFees expected = new OutstandingFees(afterPaymentInString);

        assertEquals(expected, actual.pay(payment));
    }
}
