package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }


    @Test
    public void constructor_invalidDate2_throwsIllegalArgumentException() {
        String invalidDate = "32 Dec 1999";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void getDayOfTheWeek() {
        // FRIDAY
        assertEquals(new Date("8 Oct 2021").getDayOfWeek(), DayOfWeek.FRIDAY);

        // MONDAY
        assertEquals(new Date("12 jUL 2021").getDayOfWeek(), DayOfWeek.MONDAY);

        // Different days
        assertFalse(new Date("16 Feb 2022").getDayOfWeek().equals(DayOfWeek.FRIDAY));
    }

    @Test
    public void isOver() {
        // Date has passed
        assertTrue(new Date("12 Jan 2000").isOver());
        assertTrue(new Date("12 Feb 1950").isOver());
        assertTrue(new Date("12 Jan 1324").isOver());

        // Future Date
        assertFalse(new Date("13 Mar 2100").isOver());
        assertFalse(new Date("13 Mar 2156").isOver());
        assertFalse(new Date("13 Mar 2345").isOver());
    }

    @Test
    public void compareTo() {
        Date date1 = new Date("24 Apr 2002");
        Date date2 = new Date("13 Jul 2001");

        // Same date
        Date copyOfDate1 = new Date("24 Apr 2002");
        assertEquals(0, date1.compareTo(copyOfDate1));

        // different date
        // other date is later
        assertEquals(1, date1.compareTo(date2));

        // other date is earlier
        assertEquals(-1, date2.compareTo(date1));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("31 Feb 2021")); // no such date
        assertFalse(Date.isValidDate("12 Top 1234")); // contains invalid month

        // valid date
        assertTrue(Date.isValidDate("23 Feb 2021"));
        assertTrue(Date.isValidDate("12 Jan 2022"));
        assertTrue(Date.isValidDate("29 Feb 2020")); // leap year
    }

    @Test
    public void updateDate() {
        // At least 1 week has passed
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        Date dateOneWeekAgo = new Date(oneWeekAgo.format(Date.FORMATTER));
        assertEquals(LocalDate.now(), dateOneWeekAgo.updateDate().getLocalDate());

        /*
        Less than a week has passed but the date is over.
         */
        long daysBefore = 2;
        long daysInAWeek = 7;
        LocalDate lessThanOneWeekAgo = LocalDate.now().minusDays(daysBefore);
        Date dateLessThanOneWeekAgo = new Date(lessThanOneWeekAgo.format(Date.FORMATTER));
        assertEquals(LocalDate.now().plusDays(daysInAWeek - daysBefore),
            dateLessThanOneWeekAgo.updateDate().getLocalDate());

        // Current date (date is not over yet)
        Date today = new Date(LocalDate.now().format(Date.FORMATTER));
        assertEquals(LocalDate.now(), today.updateDate().getLocalDate());
    }
}

