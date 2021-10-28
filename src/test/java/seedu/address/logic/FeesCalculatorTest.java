package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
        // Last updated today after lesson
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

        // The test i added that didn't work with your current code
        // lesson starts after last update
        feesCalculator = new FeesCalculator(new LastUpdatedDate("2021-10-01T14:59"),
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

    // The tests are just for me to check if my algo was correct, probably need some cleaning up
    public int getNumOfLessons(LocalDateTime startDateEndTime, LocalDate endDate,
            LocalDateTime lastUpdated, LocalDateTime today, Set<Date> cancelledDates) {
        return FeesCalculator.getNumOfLessonsSinceLastUpdate(startDateEndTime, endDate, lastUpdated, today, cancelledDates);
    }

    Set<Date> emptyDates = Set.of();
    LocalDate endMax = LocalDate.MAX;

    @Test
    public void startBeforeUpdate_endAfterToday() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,9,28,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,6,12,00);
        LocalDateTime today = LocalDateTime.of(2021,10,28,12,00);

        LocalDate endDate = LocalDate.of(2021, 10, 30);
        // 12, 19, 26 Oct
        assertEquals(3, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, emptyDates));
    }

    @Test
    public void startAfterUpdate_endAfterToday() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,8,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,1,12,00);
        LocalDateTime today = LocalDateTime.of(2021,10,21,12,00);

        LocalDate endDate = LocalDate.of(2021, 10, 30);
        // 8, 15 Oct
        assertEquals(2, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, emptyDates));
    }

    @Test
    public void startBeforeUpdate_endBeforeToday() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,9,28,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,6,12,00);
        LocalDateTime today = LocalDateTime.of(2021,10,28,12,00);
        LocalDate endDate = LocalDate.of(2021, 10, 24);
        // 12, 19 Oct
        assertEquals(2, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, emptyDates));
    }

    @Test
    public void startAfterUpdate_endBeforeToday() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,8,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,9,27,12,00);
        LocalDateTime today = LocalDateTime.of(2021,10,30,12,00);
        LocalDate endDate = LocalDate.of(2021, 10, 22);
        // 8, 15, 22 Oct
        assertEquals(3, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, emptyDates));
    }

    @Test
    public void startEqualsEndDate() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,13,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,10,13,00);
        LocalDateTime today = LocalDateTime.of(2021,10,28,13,00);
        LocalDate endDate = LocalDate.of(2021, 10, 13);
        // only 13 oct
        assertEquals(1, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, emptyDates));
    }

    @Test
    public void lastUpdateBeforeEndTime_todayAfterEndTime() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,26,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,26,11,00);
        LocalDateTime today = LocalDateTime.of(2021,10,26,13,00);
        assertEquals(1, getNumOfLessons(startDateEndTime, endMax, lastUpdated, today, emptyDates));
    }

    @Test
    public void lastUpdateAfterEndTime_todayAfterEndTime() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,26,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,26,13,00);
        LocalDateTime today = LocalDateTime.of(2021,10,26,14,00);
        assertEquals(0, getNumOfLessons(startDateEndTime, endMax, lastUpdated, today, emptyDates));
    }

    @Test
    public void lastUpdateBeforeEndTime_todayBeforeEndTime() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,26,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,26,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,26,11,00);
        assertEquals(0, getNumOfLessons(startDateEndTime, endMax, lastUpdated, today, emptyDates));
    }

    @Test
    public void lastUpdateAfterEndTime_todayAfterEndTime_multiple() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,13,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,13,13,00);
        LocalDateTime today = LocalDateTime.of(2021,10,27,13,00);
        // 20, 27
        assertEquals(2, getNumOfLessons(startDateEndTime, endMax, lastUpdated, today, emptyDates));
    }

    @Test
    public void cancelledLesson_betweenStartEnd() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,5,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,4,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,28,11,00);
        LocalDate endDate = LocalDate.of(2021, 10, 26);
        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"), new Date("19 oct 2021"));
        // 5, 26
        assertEquals(2, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, cancelledDates));
    }

    @Test
    public void cancelledLesson_Start() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,5,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,4,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,11,11,00);

        LocalDate endDate = LocalDate.of(2021, 10, 26);
        Set<Date> cancelledDates = Set.of(new Date("5 oct 2021"));
        // only start date passed and is cancelled
        assertEquals(0, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, cancelledDates));
    }

    @Test
    public void cancelledLesson_End() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,19,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,20,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,27,13,00);

        LocalDate endDate = LocalDate.of(2021, 10, 26);
        Set<Date> cancelledDates = Set.of(new Date("26 oct 2021"));
        // only end date passed and is cancelled
        assertEquals(0, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, cancelledDates));
    }

    @Test
    public void cancelledLesson_StartEqualsEndCancelled() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,12,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,10,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,27,13,00);

        LocalDate endDate = LocalDate.of(2021, 10, 12);
        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"));

        // start = end and is cancellled
        assertEquals(0, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, cancelledDates));
    }

    @Test
    public void cancelledLessonAll_startBeforeUpdate_endAfterToday() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,5,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,11,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,11,11,00);

        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"), new Date("19 oct 2021"), new Date("26 oct 2021"));
        // lessons that have passed after update all cancelled
        assertEquals(0, getNumOfLessons(startDateEndTime, endMax, lastUpdated, today, cancelledDates));
    }

    @Test
    public void cancelledLessonAll_startAfterUpdate_endBeforeToday() {
        LocalDateTime startDateEndTime = LocalDateTime.of(2021,10,12,12,00);
        LocalDateTime lastUpdated = LocalDateTime.of(2021,10,1,10,00);
        LocalDateTime today = LocalDateTime.of(2021,10,28,11,00);
        LocalDate endDate = LocalDate.of(2021, 10, 20);

        Set<Date> cancelledDates = Set.of(new Date("12 oct 2021"), new Date("19 oct 2021"));
        // lessons that have passed after update all cancelled
        assertEquals(0, getNumOfLessons(startDateEndTime, endDate, lastUpdated, today, cancelledDates));
    }
}
