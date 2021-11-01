package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.lesson.Date;

/**
 * A utility class containing a list of {@code Date} objects to be used in tests.
 */
public class TypicalDates {
    public static final Date DATE_CURRENT_WEEK = DateUtil.build(LocalDate.now());
    public static final Date DATE_ONE_WEEK_AGO = DateUtil.build(LocalDate.now().minusWeeks(1));
    public static final Date DATE_ONE_WEEK_LATER = DateUtil.build(LocalDate.now().plusWeeks(1));
    public static final Date DATE_TWO_WEEKS_LATER = DateUtil.build(LocalDate.now().plusWeeks(2));
}
