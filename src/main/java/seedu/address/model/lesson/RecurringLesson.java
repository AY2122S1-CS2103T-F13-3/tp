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
     * @param date           Date of lesson.
     * @param timeRange      Time range of the lesson.
     * @param subject        Subject of the lesson.
     * @param homework       Homework for the lesson.
     * @param rates          Cost per lesson for the lesson.
     * @param cancelledDates Cancelled dates of the lesson.
     */
    public RecurringLesson(Date date, TimeRange timeRange, Subject subject, Set<Homework> homework, LessonRates rates,
                           Set<Date> cancelledDates) {
        super(date, timeRange, subject, homework, rates, cancelledDates);
    }

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

    @Override
    public boolean isCancelled() {
        // recurring lesson does not terminate, hence it is never fully cancelled.
        return false;
    }

    @Override
    public boolean hasLessonOnDate(Date otherDate) {
        return otherDate.isOnRecurringDate(getStartDate()) // other date lies on a recurring lesson date
                && !getCancelledDates().contains(otherDate); // other date is not a cancelled date
    }

}
