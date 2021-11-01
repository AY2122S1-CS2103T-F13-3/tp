package seedu.address.model.lesson;

import java.util.Set;

/**
 * Represents a Recurring Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RecurringLesson extends Lesson {
    /**
     * Every field must be present and not null.
     *
     * @param date Date of lesson.
     * @param timeRange Time range of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     * @param rates Cost per hour for the lesson.
     * @param cancelledDates Cancelled dates of the lesson.
     */
    public RecurringLesson(Date date, TimeRange timeRange, Subject subject, Set<Homework> homework, LessonRates rates,
            Set<Date> cancelledDates) {
        super(date, timeRange, subject, homework, rates, cancelledDates);
    }

    /**
     * Returns a lesson with the same details but updated cancelled dates.
     *
     * @param updatedCancelledDates A set of cancelled dates of the lesson.
     * @return Lesson with updated cancelled dates.
     */
    @Override
    public Lesson updateCancelledDates(Set<Date> updatedCancelledDates) {
        return new RecurringLesson(getStartDate(), getTimeRange(), getSubject(), getHomework(), getLessonRates(),
                updatedCancelledDates);
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
     * Get the upcoming date of the lesson to display to user.
     *
     * @return The upcoming date on the same day of week if start date
     * has passed or start date if it has yet to pass.
     */
    @Override
    public Date getDisplayDate() {
        return getStartDate().updateDate(getCancelledDates());
    }

    /**
     * Returns true if this {@code RecurringLesson} clashes with the given {@code Lesson}.
     *
     * @param otherLesson The other lesson to be compared with.
     * @return True if and only if lessons clash.
     */
    @Override
    public boolean isClashing(Lesson otherLesson) {
        if (otherLesson.isRecurring()) {
            return getDayOfWeek().equals(otherLesson.getDayOfWeek()) // same day
                    && getTimeRange().isClashing(otherLesson.getTimeRange());
        } else {
            return !otherLesson.isCancelled() // other makeup lesson is not cancelled
                    && hasLessonOnDate(otherLesson.getStartDate())
                    && getTimeRange().isClashing(otherLesson.getTimeRange());
        }
    }

    /**
     * Checks if this lesson is cancelled and does not occur on any date.
     *
     * @return false.
     */
    @Override
    public boolean isCancelled() {
        // recurring lesson does not terminate, hence it is never fully cancelled.
        return false;
    }

    /**
     * Checks if this lesson occurs on a given date.
     *
     * @param date The lesson date to check.
     * @return True if this lesson occurs on the date.
     */
    @Override
    public boolean hasLessonOnDate(Date date) {
        return date.isOnRecurringDate(getStartDate()) // date lies on a recurring lesson date
                && !getCancelledDates().contains(date); // date is not a cancelled date
    }

}
