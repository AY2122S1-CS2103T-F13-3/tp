package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_NEXT_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_RATES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NON_CLASHING_TIME_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OUTSTANDING_FEES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_RANGE;
import static seedu.address.model.lesson.Date.MAX_DATE;
import static seedu.address.testutil.TypicalDates.DATE_CURRENT_WEEK;
import static seedu.address.testutil.TypicalDates.DATE_ONE_WEEK_AGO;
import static seedu.address.testutil.TypicalDates.DATE_ONE_WEEK_LATER;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

class RecurringLessonTest {


    @Test
    public void getDisplayDate_dateNotOver_sameDate() {
        Lesson lesson = new LessonBuilder().withDate(DATE_CURRENT_WEEK).buildRecurring();
        assertEquals(DATE_CURRENT_WEEK, lesson.getDisplayDate());
        assertEquals(LocalDate.now(), lesson.getDisplayLocalDate());
    }

    @Test
    public void getDisplayDate_dateOver_showNextDate() {
        Lesson lesson = new LessonBuilder().withDate(DATE_ONE_WEEK_AGO).buildRecurring();
        assertEquals(DATE_CURRENT_WEEK, lesson.getDisplayDate());
        assertEquals(LocalDate.now(), lesson.getDisplayLocalDate());
    }

    @Test
    public void getDisplayDate_dateOver_showNextUncancelledDate() {
        Lesson lesson = new LessonBuilder().withDate(DATE_ONE_WEEK_AGO)
                .withCancelledDatesSet(DATE_CURRENT_WEEK).buildRecurring();
        assertEquals(DATE_ONE_WEEK_LATER, lesson.getDisplayDate());
        assertEquals(LocalDate.now().plusWeeks(1), lesson.getDisplayLocalDate());
    }

    @Test
    public void getDisplayStartLocalDateTime_dateNotOver_sameDateTime() {
        Lesson lesson = new LessonBuilder().withDate(DATE_CURRENT_WEEK)
                .withTimeRange(VALID_TIME_RANGE).buildRecurring();
        TimeRange timeRange = new TimeRange(VALID_TIME_RANGE);
        assertEquals(timeRange.getStart().atDate(DATE_CURRENT_WEEK.getLocalDate()),
                lesson.getDisplayStartLocalDateTime());
    }

    @Test
    public void getDisplayStartLocalDateTime_dateOver_showNextDateTime() {
        Lesson lesson = new LessonBuilder().withDate(DATE_ONE_WEEK_AGO)
                .withTimeRange(VALID_TIME_RANGE).buildRecurring();
        TimeRange timeRange = new TimeRange(VALID_TIME_RANGE);
        assertEquals(timeRange.getStart().atDate(DATE_CURRENT_WEEK.getLocalDate()),
                lesson.getDisplayStartLocalDateTime());
    }

    @Test
    public void getDisplayStartLocalDateTime_dateOver_showNextUncancelledDateTime() {
        Lesson lesson = new LessonBuilder().withDate(DATE_ONE_WEEK_AGO).withTimeRange(VALID_TIME_RANGE)
                .withCancelledDatesSet(DATE_CURRENT_WEEK).buildRecurring();
        TimeRange timeRange = new TimeRange(VALID_TIME_RANGE);
        assertEquals(timeRange.getStart().atDate(DATE_ONE_WEEK_LATER.getLocalDate()),
                lesson.getDisplayStartLocalDateTime());
    }

    @Test
    public void getDisplayEndLocalDateTime_dateNotOver_sameDateTime() {
        Lesson lesson = new LessonBuilder().withDate(DATE_CURRENT_WEEK)
                .withTimeRange(VALID_TIME_RANGE).buildRecurring();
        TimeRange timeRange = new TimeRange(VALID_TIME_RANGE);
        assertEquals(timeRange.getEnd().atDate(DATE_CURRENT_WEEK.getLocalDate()),
                lesson.getDisplayEndLocalDateTime());
    }

    @Test
    public void getDisplayEndLocalDateTime_dateOver_showNextDateTime() {
        Lesson lesson = new LessonBuilder().withDate(DATE_ONE_WEEK_AGO)
                .withTimeRange(VALID_TIME_RANGE).buildRecurring();
        TimeRange timeRange = new TimeRange(VALID_TIME_RANGE);
        assertEquals(timeRange.getEnd().atDate(DATE_CURRENT_WEEK.getLocalDate()),
                lesson.getDisplayEndLocalDateTime());
    }

    @Test
    public void getDisplayEndLocalDateTime_dateOver_showNextUncancelledDateTime() {
        Lesson lesson = new LessonBuilder().withDate(DATE_ONE_WEEK_AGO).withTimeRange(VALID_TIME_RANGE)
                .withCancelledDatesSet(DATE_CURRENT_WEEK).buildRecurring();
        TimeRange timeRange = new TimeRange(VALID_TIME_RANGE);
        assertEquals(timeRange.getEnd().atDate(DATE_ONE_WEEK_LATER.getLocalDate()),
                lesson.getDisplayEndLocalDateTime());
    }


    @Test
    public void isClashing_withMakeupLesson_returnsTrue() {
        // clashes with makeup lesson with clashing day and time
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).buildRecurring();
        Lesson makeupLesson = new LessonBuilder().withDate(VALID_DATE_NEXT_MON).build();
        assertTrue(recurringLesson.isClashing(makeupLesson));
    }

    @Test
    public void isClashing_withMakeupLesson_returnsFalse() {
        // does not clash with makeup lesson on same day and non-clashing time
        Lesson recurringLesson = new LessonBuilder().withTimeRange(VALID_TIME_RANGE).buildRecurring();
        Lesson makeupLesson = new LessonBuilder().withTimeRange(VALID_NON_CLASHING_TIME_RANGE).build();
        assertFalse(recurringLesson.isClashing(makeupLesson));
    }

    @Test
    public void getNextDate_dateNotOver_sameDate() {
        Lesson lesson = new RecurringLesson(new Date(LocalDate.now().format(Date.FORMATTER)),
            MAX_DATE,
            new TimeRange(VALID_TIME_RANGE),
            new Subject(VALID_SUBJECT), new HashSet<>(),
            new LessonRates(VALID_LESSON_RATES), new OutstandingFees(VALID_OUTSTANDING_FEES),
            new HashSet<>());
        assertEquals(LocalDate.now(), lesson.getDisplayDate().getLocalDate());

        Date cancelled = new Date(LocalDate.now().format(Date.FORMATTER));
        Set<Date> dates = new HashSet<>();
        dates.add(cancelled);
        Lesson lesson1 = new MakeUpLesson(new Date(LocalDate.now().format(Date.FORMATTER)),
            new TimeRange(VALID_TIME_RANGE),
            new Subject(VALID_SUBJECT), new HashSet<>(),
            new LessonRates(VALID_LESSON_RATES), new OutstandingFees(VALID_OUTSTANDING_FEES), dates);

        Date cancelled2 = new Date(LocalDate.now().format(Date.FORMATTER));
        Set<Date> dates2 = new HashSet<>();
        dates2.add(cancelled2);
        Lesson lesson2 = new MakeUpLesson(new Date(LocalDate.now().format(Date.FORMATTER)),
            new TimeRange(VALID_TIME_RANGE),
            new Subject(VALID_SUBJECT), new HashSet<>(),
            new LessonRates(VALID_LESSON_RATES), new OutstandingFees(VALID_OUTSTANDING_FEES), dates2);

        TreeSet<Lesson> lessonSet = new TreeSet<>();
        lessonSet.add(lesson1);
        lessonSet.add(lesson2);
        SortedSet<Lesson> unmod = Collections.unmodifiableSortedSet(lessonSet);
        assertTrue(unmod.size() == 2);
    }

    @Test
    public void isClashing() {
        Date oneWeekLaterDate = new Date("21 Jan 2021");
        Lesson lesson = new RecurringLesson(new Date("14 Jan 2021"),
            MAX_DATE,
            new TimeRange(VALID_TIME_RANGE),
            new Subject(VALID_SUBJECT), new HashSet<>(),
            new LessonRates(VALID_LESSON_RATES), new OutstandingFees(VALID_OUTSTANDING_FEES),
            new HashSet<>());
        Lesson clashingLesson = new RecurringLesson(oneWeekLaterDate,
            MAX_DATE,
            new TimeRange(VALID_TIME_RANGE),
            new Subject(VALID_SUBJECT), new HashSet<>(),
            new LessonRates(VALID_LESSON_RATES), new OutstandingFees(VALID_OUTSTANDING_FEES),
            new HashSet<>());

        assertTrue(lesson.isClashing(clashingLesson));
    }

    public void isClashing_withCancelledMakeupLesson_returnsFalse() {
        // does not clash with cancelled makeup lesson
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).buildRecurring();
        Lesson cancelledMakeupLesson = new LessonBuilder().withDate(VALID_DATE_NEXT_MON)
                .withCancelledDatesSet(VALID_DATE_NEXT_MON).build();

        assertFalse(recurringLesson.isClashing(cancelledMakeupLesson));
    }

    @Test
    public void isClashing_withMakeupLessonOnCancelledDate_returnsFalse() {
        // does not clash makeup lesson on cancelled dates
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON)
                .withCancelledDatesSet(VALID_DATE_NEXT_MON).buildRecurring();
        Lesson cancelledMakeupLesson = new LessonBuilder().withDate(VALID_DATE_NEXT_MON).build();

        assertFalse(recurringLesson.isClashing(cancelledMakeupLesson));
    }

    @Test
    public void isClashing_withRecurringLesson() {
        // clashes with recurring lesson with clashing day and time
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).buildRecurring();
        Lesson recurringLessonClashing = new LessonBuilder().withDate(VALID_DATE_NEXT_MON).buildRecurring();

        assertTrue(recurringLesson.isClashing(recurringLessonClashing));
    }

    @Test
    public void isClashing_withRecurringLesson_returnsFalse() {
        // does not clash with recurring lesson on same day different time
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).withTimeRange(VALID_TIME_RANGE)
                .buildRecurring();
        Lesson recurringLessonNonClashing = new LessonBuilder().withDate(VALID_DATE_NEXT_MON)
                .withTimeRange(VALID_NON_CLASHING_TIME_RANGE).buildRecurring();

        assertFalse(recurringLesson.isClashing(recurringLessonNonClashing));
    }

    @Test
    public void hasLessonOnDate_returnsTrue() {
        // date lies on a recurring lesson date and not cancelled
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).withTimeRange(VALID_TIME_RANGE)
                .buildRecurring();
        Date date = new Date(VALID_DATE_NEXT_MON);
        assertTrue(recurringLesson.hasLessonOnDate(date));
    }

    @Test
    public void hasLessonOnDate_returnsFalse() {
        // date does not lie on a recurring lesson date
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).withTimeRange(VALID_TIME_RANGE)
                .buildRecurring();
        Date dateTue = new Date(VALID_DATE_TUE);
        assertFalse(recurringLesson.hasLessonOnDate(dateTue));

        // date lies on a recurring lesson date and is cancelled
        recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).withTimeRange(VALID_TIME_RANGE)
                .withCancelledDatesSet(VALID_DATE_NEXT_MON).buildRecurring();
        Date dateMon = new Date(VALID_DATE_NEXT_MON);
        assertFalse(recurringLesson.hasLessonOnDate(dateMon));
    }
}
