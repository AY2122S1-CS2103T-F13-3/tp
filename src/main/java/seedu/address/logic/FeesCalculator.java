package seedu.address.logic;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LastUpdatedDate;
import seedu.address.model.Model;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.Money;
import seedu.address.model.lesson.OutstandingFees;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonUtil;

/**
 * Responsible for the automated updates and calculation of each lesson's fees.
 * Many lessons to 1 FeeCalculator.
 */
public class FeesCalculator implements Calculator {
    public static final String MESSAGE_PAY_TOO_MUCH = "Payment amount exceeds current "
            + "outstanding fees. Invalid transaction.";

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final float numberOfMinutesInAnHour = 60.00F;
    private static final float numberOfDaysInAWeek = 7;
    private final LocalDateTime currentDateTime;
    private final LastUpdatedDate lastUpdated;

    /**
     * Constructs a {@code FeesCalculator}
     *
     * @param lastUpdatedDate Last Updated Date. Equivalent to last date and time user launched TAB.
     * @param currentDateTime Current Date and Time.
     */
    public FeesCalculator(LastUpdatedDate lastUpdatedDate, LocalDateTime currentDateTime) {
        lastUpdated = lastUpdatedDate;
        this.currentDateTime = currentDateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    @Override
    public Model updateAllLessonOutstandingFees(Model model) {
        List<Person> personList = model.getFilteredPersonList();

        for (Person targetPerson : personList) {
            model.setPerson(targetPerson, createEditedPerson(targetPerson));
        }

        model.setLastUpdatedDate();


        return model;
    }

    private Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        List<Lesson> lessonList = new ArrayList<>(personToEdit.getLessons());

        for (int i = 0; i < lessonList.size(); i++) {
            lessonList.set(i, updateLessonOutstandingFeesField(lessonList.get(i)));
        }

        return PersonUtil.createdEditedPerson(personToEdit, new TreeSet<>(lessonList));
    }

    /**
     * Automatically updates the specific lesson's outstanding fees.
     *
     * @param lesson The specific lesson to be updated.
     * @return Updated lesson with the correct outstanding fees.
     */
    public Lesson updateLessonOutstandingFeesField(Lesson lesson) {

        // make a copy of untouched fields
        Date copiedDate = new Date(lesson.getStartDate().value);
        TimeRange copiedTimeRange = new TimeRange(lesson.getTimeRange().value);
        Subject copiedSubject = new Subject(lesson.getSubject().subject);
        LessonRates copiedLessonRates = new LessonRates(lesson.getLessonRates().value);
        Set<Homework> copiedHomework = lesson.getHomework() == null
                ? null
                : new HashSet<>(lesson.getHomework());
        Set<Date> copiedCancelledDates = lesson.getCancelledDates();

        OutstandingFees currentOutstanding = lesson.getOutstandingFees();

        if (lesson.isRecurring()) {
            OutstandingFees updatedOutstandingFees =
                    getUpdatedOutstandingFeesRecurring(currentOutstanding, lesson.getEndDateTime(), copiedTimeRange,
                            copiedLessonRates, copiedCancelledDates);
            return new RecurringLesson(copiedDate, copiedTimeRange, copiedSubject,
                    copiedHomework, copiedLessonRates, updatedOutstandingFees, copiedCancelledDates);
        } else {
            OutstandingFees updatedOutstandingFees = getUpdatedOutstandingFeesMakeup(currentOutstanding,
                    copiedDate, copiedTimeRange, copiedLessonRates);
            return new MakeUpLesson(copiedDate, copiedTimeRange, copiedSubject,
                    copiedHomework, copiedLessonRates, updatedOutstandingFees, copiedCancelledDates);
        }
    }

    public OutstandingFees getUpdatedOutstandingFeesMakeup(OutstandingFees original, Date date, TimeRange timeRange,
                                                           LessonRates lessonRates) {
        float costPerLesson = getCostPerLesson(timeRange, lessonRates);
        float updatedFees = original.getMonetaryValueInFloat();

        LocalDateTime makeUpLessonEnd = LocalDateTime.of(date.getLocalDate(), timeRange.getEnd());

        boolean isBetween = makeUpLessonEnd.isAfter(lastUpdated.dateTime) && makeUpLessonEnd.isBefore(currentDateTime);

        if (isBetween) {
            updatedFees += costPerLesson;
        }

        return new OutstandingFees(DECIMAL_FORMAT.format(updatedFees));
    }

    /**
     * Updates the Outstanding Fees field to most recent value and modify the lastAdded date.
     *
     * @param original Outstanding Fees of current amount.
     * @param updateDay Day of the lesson.
     * @param timeRange Duration per lesson.
     * @param lessonRates Cost per hour for the lesson.
     * @return Updated Outstanding Fees object.
     */
    public OutstandingFees getUpdatedOutstandingFeesRecurring(OutstandingFees original, LocalDateTime startDateEndTime,
            TimeRange timeRange, LessonRates lessonRates, Set<Date> cancelledDates) {
        // updated fee values
        LocalDate endDate = LocalDate.MAX;
        int numberOfLessons =
                getNumOfLessonsSinceLastUpdate(startDateEndTime, endDate, lastUpdated.dateTime, currentDateTime,
                        cancelledDates);
        assert numberOfLessons >= 0;
        float costPerLesson = getCostPerLesson(timeRange, lessonRates);
        float updatedFees = costPerLesson * (float) numberOfLessons + original.getMonetaryValueInFloat();

        return new OutstandingFees(DECIMAL_FORMAT.format(updatedFees));
    }

    private float getCostPerLesson(TimeRange timeRange, LessonRates lessonRates) {
        Duration duration = timeRange.getDuration();
        float durationInHour = duration.toMinutes() / numberOfMinutesInAnHour;
        return durationInHour * lessonRates.getMonetaryValueInFloat();
    }

    // i made it static just to test, and the parameters are not ideal, just for testing to see if it's correct
    public static int getNumOfLessonsSinceLastUpdate(LocalDateTime startDateEndTime, LocalDate endDate,
            LocalDateTime lastUpdated, LocalDateTime today, Set<Date> cancelledDates) {

        LocalTime endTime = LocalTime.from(startDateEndTime);
        LocalDateTime endDateEndTime = LocalDateTime.of(endDate, endTime);

        DayOfWeek dayOfLesson = startDateEndTime.getDayOfWeek();

        // the next or same dayOfLesson after last updated, with end time
        LocalDateTime lessonAfterUpdate = lastUpdated.with(TemporalAdjusters.nextOrSame(dayOfLesson)).with(endTime);
        // last updated is on the day of lesson and after end time
        if (lessonAfterUpdate.isBefore(lastUpdated)) {
            // adjust lesson after update to one week later
            lessonAfterUpdate = lessonAfterUpdate.plusWeeks(1);
        }
        LocalDateTime firstLesson = Collections.max(List.of(lessonAfterUpdate, startDateEndTime));

        // the prev or same dayOfLesson before today
        LocalDateTime lessonBeforeToday = today.with(TemporalAdjusters.previousOrSame(dayOfLesson)).with(endTime);
        // today is on the day of lesson and before end time
        if (lessonBeforeToday.isAfter(today)) {
            // adjust lesson before today to one week ago
            lessonBeforeToday = lessonBeforeToday.minusWeeks(1);
        }
        LocalDateTime lastLesson = Collections.min(List.of(lessonBeforeToday, endDateEndTime));

        // if last lesson is before first lesson
        if (lastLesson.isBefore(firstLesson)) {
            return 0;
        }
        // count number of lessons between first and last lesson inclusive
        int lessonCount = (int) ChronoUnit.WEEKS.between(firstLesson, lastLesson) + 1;

        // remove cancelled dates
        for (Date cancelledDate : cancelledDates) {
            if (cancelledDate.isDateBetween(firstLesson.toLocalDate(), lastLesson.toLocalDate())) {
                lessonCount -= 1;
            }
        }
        return lessonCount;
    }

    /**
     * Returns the outstanding fees after payment.
     *F
     * @param payment Amount paid.
     * @return Updated OutstandingFees.
     */
    public static String pay(OutstandingFees toPay, Money payment) throws IllegalValueException {
        float newOutstandingFees = toPay.getMonetaryValueInFloat() - payment.getMonetaryValueInFloat();

        if (newOutstandingFees < 0) {
            throw new IllegalValueException(MESSAGE_PAY_TOO_MUCH);
        }
        String parseValueToString = Float.toString(newOutstandingFees);
        return parseValueToString;
    }
}
