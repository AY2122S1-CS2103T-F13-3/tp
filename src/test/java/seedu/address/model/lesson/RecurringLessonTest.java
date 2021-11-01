package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_NEXT_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NON_CLASHING_TIME_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_RANGE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DateUtil;
import seedu.address.testutil.LessonBuilder;

class RecurringLessonTest {

    @Test
    public void getDisplayDate_dateNotOver_sameDate() {
        Date dateCurrentWeek = DateUtil.build(LocalDate.now());
        Lesson lesson = new LessonBuilder().withDate(dateCurrentWeek).buildRecurring();
        assertEquals(dateCurrentWeek, lesson.getDisplayDate());
        assertEquals(LocalDate.now(), lesson.getDisplayLocalDate());
    }

    @Test
    public void getDisplayDate_dateOver_showNextDate() {
        Date currentWeek = DateUtil.build(LocalDate.now());
        Date dateOneWeekAgo = DateUtil.build(LocalDate.now().minusWeeks(1));
        Lesson lesson = new LessonBuilder().withDate(dateOneWeekAgo).buildRecurring();
        assertEquals(currentWeek, lesson.getDisplayDate());
        assertEquals(LocalDate.now(), lesson.getDisplayLocalDate());
    }

    @Test
    public void getDisplayDate_dateOver_showNextUncancelledDate() {
        Date dateOneWeekAgo = DateUtil.build(LocalDate.now().minusWeeks(1));
        Date dateCurrentWeek = DateUtil.build(LocalDate.now());
        Date dateOneWeekLater = DateUtil.build(LocalDate.now().plusWeeks(1));
        Lesson lesson = new LessonBuilder().withDate(dateOneWeekAgo)
                .withCancelledDatesSet(dateCurrentWeek).buildRecurring();

        assertEquals(dateOneWeekLater, lesson.getDisplayDate());
        assertEquals(LocalDate.now().plusWeeks(1), lesson.getDisplayLocalDate());
    }

    @Test
    public void isClashing_withMakeupLesson_returnsTrue() {
        // clashes with makeup lesson with clashing day and time
        Lesson recurringLesson = new LessonBuilder().withDate(VALID_DATE_MON).buildRecurring();
        Lesson makeupLesson = new LessonBuilder().withDate(VALID_DATE_NEXT_MON).build();

        assertTrue(recurringLesson.isClashing(makeupLesson));
    }

    @Test
    public void isClashing_withMakeupLessonD_returnsFalse() {
        // does not clash with makeup lesson on same day and non-clashing time
        Lesson recurringLesson = new LessonBuilder().withTimeRange(VALID_TIME_RANGE).buildRecurring();
        Lesson makeupLesson = new LessonBuilder().withTimeRange(VALID_NON_CLASHING_TIME_RANGE).build();

        assertFalse(recurringLesson.isClashing(makeupLesson));
    }

    @Test
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
        assertFalse(recurringLesson.hasLessonOnDate(dateTue));
    }
}
