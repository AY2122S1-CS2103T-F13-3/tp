package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDates.DATE_CURRENT_WEEK;
import static seedu.address.testutil.TypicalDates.DATE_ONE_WEEK_AGO;
import static seedu.address.testutil.TypicalDates.DATE_ONE_WEEK_LATER;
import static seedu.address.testutil.TypicalDates.DATE_TWO_WEEKS_LATER;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DateUtil;

public class DateTest {
    private static final Set<Date> EMPTY_DATES = new HashSet<>();

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
    public void updateDate_withoutSkipDates() {
        // At least 1 week has passed
        assertEquals(DATE_CURRENT_WEEK, DATE_ONE_WEEK_AGO.updateDate(EMPTY_DATES));

        // Less than a week has passed but the date is over.
        long daysBefore = 2;
        long daysInAWeek = 7;
        Date dateLessThanOneWeekAgo = DateUtil.build(LocalDate.now().minusDays(daysBefore));
        Date dateNext = DateUtil.build(LocalDate.now().plusDays(daysInAWeek - daysBefore));
        assertEquals(dateNext, dateLessThanOneWeekAgo.updateDate(EMPTY_DATES));

        // Current date (date is not over yet)
        assertEquals(DATE_CURRENT_WEEK, DATE_CURRENT_WEEK.updateDate(EMPTY_DATES));

        // Future date
        assertEquals(DATE_ONE_WEEK_LATER, DATE_ONE_WEEK_LATER.updateDate(EMPTY_DATES));
    }

    @Test
    public void updateDate_withSkipDates() {
        // Current date skip
        Set<Date> cancelledDates = Set.of(DATE_CURRENT_WEEK);
        assertEquals(DATE_ONE_WEEK_LATER, DATE_ONE_WEEK_AGO.updateDate(cancelledDates));

        // Skip consecutive dates
        cancelledDates = Set.of(DATE_CURRENT_WEEK, DATE_ONE_WEEK_LATER);
        assertEquals(DATE_TWO_WEEKS_LATER, DATE_ONE_WEEK_AGO.updateDate(cancelledDates));
    }
}

