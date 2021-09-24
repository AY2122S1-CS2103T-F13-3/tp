package seedu.address.model.lesson;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a Recurring Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RecurringLesson extends Lesson {
    private static int recurringLessonsCount = 0;

    /**
     * Every field must be present and not null.
     *
     * @param date Date of lesson.
     * @param startTime Start time of the lesson.
     * @param endTime End time of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     */
    public RecurringLesson(Date date, Time startTime, Time endTime,
                           Subject subject, Set<Homework> homework) {
        super(date, startTime, endTime, subject, homework);
        updateRecurringLessonCount();
    }

    /**
     * Returns the number of lessons attended.
     *
     * @return The number of lessons attended for this recurring lesson.
     */
    public static int getRecurringLessonsCount() {
        return recurringLessonsCount;
    }

    /**
     * Check if the Lesson object is recurring.
     *
     * @return true.
     */
    @Override
    public boolean isRecurring() {
        return true;
    }

    /**
     * Edit the date of the recurring lesson.
     *
     * @param newDateString The date to be updated with.
     * @return {@code RecurringLesson} with the updated date.
     */
    @Override
    public Lesson updateDate(String newDateString) {
        return new RecurringLesson(new Date(newDateString), getStartTime(), getEndTime(),
                getSubject(), getHomework());
    }

    private RecurringLesson updateRecurringLessonCount() {
        // Compare lesson date to current date
        // Increment count if date has passed
        if (getDate().getLocalDate().compareTo(LocalDate.now()) > 0) {
            return this;
        }
        recurringLessonsCount++;
        // Update the date
        Date newDate = super.updateDateWithWeek();
        return new RecurringLesson(newDate, getStartTime(), getEndTime(),
                getSubject(), getHomework());
    }

}
