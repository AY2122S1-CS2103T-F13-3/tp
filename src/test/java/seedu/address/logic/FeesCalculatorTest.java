package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.LastUpdatedDate;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

class FeesCalculatorTest {
    private final LastUpdatedDate lastUpdatedDate = new LastUpdatedDate("2021-10-23T00:00");
    private FeesCalculator feesCalculator;

    @Test
    public void updateLessonOutstandingFeesField_recurringLessons() {
        Lesson expected;
        // Last Updated within same week, lesson has passed since
        // Last updated: Monday 1200, Current: Tuesday 1200, Lesson: 1400-1500
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-25T12:00"),
                LocalDateTime.parse("2021-10-27T12:00"));
        Lesson lessonOnMondayAfterLastUpdate = new LessonBuilder().withDate("25 OCT 2021").buildRecurring();
        expected = new LessonBuilder().withDate("25 OCT 2021").withOutstandingFees("150").buildRecurring();
        assertEquals(expected, feesCalculator.updateLessonOutstandingFeesField(lessonOnMondayAfterLastUpdate));


        // Last updated within the same week, lesson has not passed since last updated
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-26T12:00"),
                LocalDateTime.parse("2021-10-27T12:00"));
        Lesson sameWeekNotPassedLesson = new LessonBuilder().withDate("9 SEP 2021").buildRecurring();
        assertEquals(sameWeekNotPassedLesson, feesCalculator.updateLessonOutstandingFeesField(sameWeekNotPassedLesson));

        // Last updated today before lesson
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-27T12:00"),
                LocalDateTime.parse("2021-10-27T15:02"));
        Lesson sameDayToBeUpdated = new LessonBuilder().withDate("27 OCT 2021").buildRecurring();
        expected = new LessonBuilder().withDate("27 OCT 2021").withOutstandingFees("150").buildRecurring();
        assertEquals(expected, feesCalculator.updateLessonOutstandingFeesField(sameDayToBeUpdated));

        // Last updated today after lesson then shouldn't update fees since lesson has already passed last updated
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-27T15:01"),
                LocalDateTime.parse("2021-10-27T20:00"));
        Lesson sameDayNotToBeUpdated = new LessonBuilder().withDate("27 OCT 2021").buildRecurring();
        assertEquals(sameDayNotToBeUpdated, feesCalculator.updateLessonOutstandingFeesField(sameDayNotToBeUpdated));

        // lesson ends right after last updated, should update
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-23T14:59"),
                LocalDateTime.parse("2021-10-25T17:30"));
        Lesson rightAfterLastUpdate = new LessonBuilder().withDate("23 OCT 2021").buildRecurring();
        expected = new LessonBuilder().withDate("23 OCT 2021").withOutstandingFees("150").buildRecurring();
        assertEquals(expected, feesCalculator.updateLessonOutstandingFeesField(rightAfterLastUpdate));

        // lesson ends right before last updated, should not update
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-23T15:01"),
                LocalDateTime.parse("2021-10-25T17:30"));
        Lesson rightBeforeLastUpdate = new LessonBuilder().withDate("23 OCT 2021").buildRecurring();
        assertEquals(rightBeforeLastUpdate, feesCalculator.updateLessonOutstandingFeesField(rightBeforeLastUpdate));

        // lesson starts after last updated
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-09-01T14:59"),
            LocalDateTime.parse("2021-10-27T17:30"));
        rightBeforeLastUpdate = new LessonBuilder().withDate("12 OCT 2021").buildRecurring();
        expected = new LessonBuilder().withDate("12 OCT 2021").withOutstandingFees("250").buildRecurring();
        // should add lessons 12, 19, 26 oct. Should not add 5 oct
        assertEquals(expected, feesCalculator.updateLessonOutstandingFeesField(rightBeforeLastUpdate));

        // With cancelled date between last updated and current date
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-18T12:00"),
                LocalDateTime.parse("2021-10-25T12:00"));
        Lesson withCancelledDateBetween = new LessonBuilder()
                .withDate("16 OCT 2021")
                .withCancelledDatesSet(new Date("23 OCT 2021"))
                .buildRecurring();
        assertEquals(withCancelledDateBetween,
                feesCalculator.updateLessonOutstandingFeesField(withCancelledDateBetween));

        // With end date between last updated and current date
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-12T12:00"),
                LocalDateTime.parse("2021-10-28T12:00"));

        Lesson withEndDateBetween = new LessonBuilder()
                .withDate("13 OCT 2021")
                .withEndDate("26 OCT 2021")
                .buildRecurring();

        expected = new LessonBuilder()
                .withDate("13 OCT 2021")
                .withEndDate("26 OCT 2021")
                .withOutstandingFees("200")
                .buildRecurring();

        assertEquals(expected,
            feesCalculator.updateLessonOutstandingFeesField(withEndDateBetween));

        // With end date as last updated, no lesson between- should not change
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-17T12:00"),
            LocalDateTime.parse("2021-10-28T12:00"));

        Lesson withEndDateAsLastUpdated = new LessonBuilder()
            .withDate("13 OCT 2021")
            .withEndDate("17 OCT 2021")
            .buildRecurring();

        assertEquals(withEndDateAsLastUpdated,
            feesCalculator.updateLessonOutstandingFeesField(withEndDateAsLastUpdated));

        // With end date as last updated, lesson between- should update
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-13T12:00"),
            LocalDateTime.parse("2021-10-28T12:00"));

        Lesson withEndDateAsLastUpdated2 = new LessonBuilder()
                .withDate("13 OCT 2021")
                .withEndDate("17 OCT 2021")
                .buildRecurring();

        expected = new LessonBuilder()
                .withDate("13 OCT 2021")
                .withEndDate("17 OCT 2021")
                .withOutstandingFees("150")
                .buildRecurring();

        assertEquals(expected,
            feesCalculator.updateLessonOutstandingFeesField(withEndDateAsLastUpdated2));

        // With end date as current date, lesson after current - should not update with 2nd lesson
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-13T12:00"),
            LocalDateTime.parse("2021-10-28T12:00"));

        Lesson withEndDateAsCurrentDate = new LessonBuilder()
            .withDate("21 OCT 2021")
            .withEndDate("28 OCT 2021")
            .buildRecurring();

        expected = new LessonBuilder()
                .withDate("21 OCT 2021")
                .withEndDate("28 OCT 2021")
                .withOutstandingFees("150")
                .buildRecurring();

        assertEquals(expected,
            feesCalculator.updateLessonOutstandingFeesField(withEndDateAsCurrentDate));
    }

    @Test
    public void updateLessonOutstandingFeesField_makeUpLesson() {
        // MakeUp Lesson in the past do not update
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-23T12:00"), LocalDateTime.now());
        Lesson pastMakeUpLesson = new LessonBuilder().withDate("12 OCT 2021").build();
        assertEquals(pastMakeUpLesson, feesCalculator.updateLessonOutstandingFeesField(pastMakeUpLesson));

        // MakeUp Lesson between last update and current date, should update
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-25T12:20"),
                LocalDateTime.parse("2021-10-27T22:10"));
        Lesson lessonBetweenLastUpdateAndToday = new LessonBuilder().withDate("26 OCT 2021").build();
        Lesson expectedLessonBetweenUpdateAndToday = new LessonBuilder().withDate("26 OCT 2021")
                .withOutstandingFees("150").build();
        assertEquals(expectedLessonBetweenUpdateAndToday,
                feesCalculator.updateLessonOutstandingFeesField(lessonBetweenLastUpdateAndToday));
    }

    @Test
    public void startBeforeUpdate_endAfterToday() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-06T12:00"),
            LocalDateTime.parse("2021-10-28T12:00"));

        Lesson lesson = new LessonBuilder()
                .withDate("28 SEP 2021")
                .withEndDate("30 OCT 2021")
                .withTimeRange("1100-1200")
                .buildRecurring();

        // 12, 19, 26 Oct
        assertEquals(3, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void startAfterUpdate_endAfterToday() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-01T12:00"),
            LocalDateTime.parse("2021-10-21T12:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("8 OCT 2021")
            .withEndDate("30 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        // 8, 15 Oct
        assertEquals(2, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void startBeforeUpdate_endBeforeToday() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-06T12:00"),
            LocalDateTime.parse("2021-10-28T12:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("28 SEP 2021")
            .withEndDate("24 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        // 12, 19 Oct
        assertEquals(2, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void startAfterUpdate_endBeforeToday() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-09-27T12:00"),
            LocalDateTime.parse("2021-10-30T12:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("8 OCT 2021")
            .withEndDate("22 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        // 8, 15, 22 Oct
        assertEquals(3, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void startEqualsEndDate() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-10T13:00"),
            LocalDateTime.parse("2021-10-28T13:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("13 OCT 2021")
            .withEndDate("13 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        // 13 Oct
        assertEquals(1, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void lastUpdateBeforeEndTime_todayAfterEndTime() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-26T11:00"),
            LocalDateTime.parse("2021-10-26T13:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("26 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        // 26 Oct
        assertEquals(1, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void lastUpdateAfterEndTime_todayAfterEndTime() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-26T13:00"),
            LocalDateTime.parse("2021-10-26T14:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("26 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void lastUpdateBeforeEndTime_todayBeforeEndTime() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-26T10:00"),
            LocalDateTime.parse("2021-10-26T11:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("26 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void lastUpdateAfterEndTime_todayAfterEndTime_multiple() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-13T13:00"),
            LocalDateTime.parse("2021-10-27T13:00"));

        Lesson lesson = new LessonBuilder()
            .withDate("13 OCT 2021")
            .withTimeRange("1100-1200")
            .buildRecurring();

        // 20, 27 Oct
        assertEquals(2, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), new HashSet<>()));
    }

    @Test
    public void cancelledLesson_betweenStartEnd() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-04T10:00"),
            LocalDateTime.parse("2021-10-28T11:00"));

        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"), new Date("19 oct 2021"));

        Lesson lesson = new LessonBuilder()
            .withDate("5 OCT 2021")
            .withEndDate("26 OCT 2021")
            .withTimeRange("1100-1200")
            .withCancelledDatesSet("12 OCT 2021", "19 OCT 2021")
            .buildRecurring();

        // 5, 26 Oct
        assertEquals(2, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), cancelledDates));
    }

    @Test
    public void cancelledLesson_start() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-04T10:00"),
            LocalDateTime.parse("2021-10-11T11:00"));

        Set<Date> cancelledDates = Set.of(new Date("5 oct 2021"));

        Lesson lesson = new LessonBuilder()
            .withDate("5 OCT 2021")
            .withEndDate("26 OCT 2021")
            .withTimeRange("1100-1200")
            .withCancelledDatesSet("5 OCT 2021")
            .buildRecurring();

        // only start date passed and is cancelled
        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), cancelledDates));
    }

    @Test
    public void cancelledLesson_end() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-20T10:00"),
            LocalDateTime.parse("2021-10-27T13:00"));

        Set<Date> cancelledDates = Set.of(new Date("26 oct 2021"));

        Lesson lesson = new LessonBuilder()
            .withDate("19 OCT 2021")
            .withEndDate("26 OCT 2021")
            .withTimeRange("1100-1200")
            .withCancelledDatesSet("26 OCT 2021")
            .buildRecurring();

        // only end date passed and is cancelled
        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), cancelledDates));
    }

    @Test
    public void cancelledLesson_startEqualsEndCancelled() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-10T10:00"),
            LocalDateTime.parse("2021-10-27T13:00"));

        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"));

        Lesson lesson = new LessonBuilder()
            .withDate("12 OCT 2021")
            .withEndDate("12 OCT 2021")
            .withTimeRange("1100-1200")
            .withCancelledDatesSet("12 OCT 2021")
            .buildRecurring();

        // start = end and is cancelled
        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), cancelledDates));
    }


    @Test
    public void cancelledLessonAll_startBeforeUpdate_endAfterToday() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-11T10:00"),
            LocalDateTime.parse("2021-10-11T11:00"));

        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"), new Date("19 oct 2021"), new Date("26 oct 2021"));

        Lesson lesson = new LessonBuilder()
            .withDate("5 OCT 2021")
            .withTimeRange("1100-1200")
            .withCancelledDatesSet("12 OCT 2021", "19 oct 2021", "26 oct 2021")
            .buildRecurring();

        // lessons that have passed after update all cancelled
        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), cancelledDates));
    }

    @Test
    public void cancelledLessonAll_startAfterUpdate_endBeforeToday() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-01T10:00"),
            LocalDateTime.parse("2021-10-28T11:00"));

        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"), new Date("19 oct 2021"));

        Lesson lesson = new LessonBuilder()
            .withDate("12 OCT 2021")
            .withEndDate("20 OCT 2021")
            .withTimeRange("1100-1200")
            .withCancelledDatesSet("12 OCT 2021", "19 oct 2021")
            .buildRecurring();

        // lessons that have passed after update all cancelled
        assertEquals(0, feesCalculator.getNumOfLessonsSinceLastUpdate(
            lesson.getDayOfWeek(), lesson.getStartDate().getLocalDate(), lesson.getEndDate().getLocalDate(),
            lesson.getTimeRange().getEnd(), cancelledDates));
    }

    @Test
    public void lastUpdatedAfterEndDate_noUpdate() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-28T17:00"),
            LocalDateTime.parse("2021-10-28T19:00"));

        Lesson withEndDateBeforeLastUpdated = new LessonBuilder()
            .withDate("21 OCT 2021")
            .withEndDate("28 OCT 2021")
            .buildRecurring();

        assertEquals(withEndDateBeforeLastUpdated,
            feesCalculator.updateLessonOutstandingFeesField(withEndDateBeforeLastUpdated));
    }

    @Test
    public void lastUpdatedBeforeEndTimeEndDate_noUpdate() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-28T11:00"),
            LocalDateTime.parse("2021-10-28T19:00"));

        Lesson withEndDateSameAsEndTimeBeforeLastUpdated = new LessonBuilder()
            .withDate("21 OCT 2021")
            .withEndDate("28 OCT 2021")
            .buildRecurring();

        Lesson expected = new LessonBuilder()
            .withDate("21 OCT 2021")
            .withEndDate("28 OCT 2021")
            .withOutstandingFees("150")
            .buildRecurring();

        assertEquals(expected,
            feesCalculator.updateLessonOutstandingFeesField(withEndDateSameAsEndTimeBeforeLastUpdated));
    }

    @Test
    public void startAndEndSameAndIsCancelled_noUpdate() {
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-20T14:30"),
            LocalDateTime.parse("2021-10-21T14:45"));

        Lesson withSameStartAndEndAndCancelled = new LessonBuilder()
            .withDate("21 OCT 2021")
            .withEndDate("21 OCT 2021")
            .withCancelledDatesSet("21 OCT 2021")
            .buildRecurring();

        assertEquals(withSameStartAndEndAndCancelled,
            feesCalculator.updateLessonOutstandingFeesField(withSameStartAndEndAndCancelled));
    }

    /**
     * Considers singular cancelled date only.
     */
    @Test
    public void cancelledDatesTests() {
        // starts before last updated and end after current
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-21T14:30"),
            LocalDateTime.parse("2021-10-21T14:45"));

        Lesson startsBeforeLastUpdatedEndsAfterCurrent = new LessonBuilder()
            .withDate("7 OCT 2021")
            .withEndDate("28 OCT 2021")
            .withCancelledDatesSet("21 OCT 2021")
            .buildRecurring();

        // no update
        assertEquals(startsBeforeLastUpdatedEndsAfterCurrent,
            feesCalculator.updateLessonOutstandingFeesField(startsBeforeLastUpdatedEndsAfterCurrent));

        // starts before last updated and end before current
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-21T14:30"),
            LocalDateTime.parse("2021-10-21T15:45"));

        Lesson startsBeforeLastUpdatedEndsBeforeCurrent = new LessonBuilder()
            .withDate("7 OCT 2021")
            .withEndDate("28 OCT 2021")
            .withCancelledDatesSet("21 OCT 2021")
            .buildRecurring();

        // no update since cancelled
        assertEquals(startsBeforeLastUpdatedEndsBeforeCurrent,
            feesCalculator.updateLessonOutstandingFeesField(startsBeforeLastUpdatedEndsBeforeCurrent));

        // starts after last updated and ends after current
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-21T13:30"),
            LocalDateTime.parse("2021-10-21T14:45"));

        Lesson startsAfterLastUpdatedEndsAfterCurrent = new LessonBuilder()
            .withDate("7 OCT 2021")
            .withEndDate("28 OCT 2021")
            .withCancelledDatesSet("21 OCT 2021")
            .buildRecurring();

        // no update
        assertEquals(startsAfterLastUpdatedEndsAfterCurrent,
            feesCalculator.updateLessonOutstandingFeesField(startsAfterLastUpdatedEndsAfterCurrent));

        // updated on current day and is cancelled
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-21T15:30"),
            LocalDateTime.parse("2021-10-21T16:45"));

        Lesson todayIsCancelled = new LessonBuilder()
            .withDate("21 OCT 2021")
            .withCancelledDatesSet("21 OCT 2021")
            .buildRecurring();

        // no update since cancelled
        assertEquals(todayIsCancelled,
            feesCalculator.updateLessonOutstandingFeesField(todayIsCancelled));
    }

}
