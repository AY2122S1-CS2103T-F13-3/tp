package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

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
     * @param endDate End date of the recurrence.
     * @param timeRange Time range of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     * @param rates Cost per lesson for the lesson.
     */
    public RecurringLesson(Date date, Date endDate, TimeRange timeRange, Subject subject, Set<Homework> homework, LessonRates rates) {
        super(date, endDate, timeRange, subject, homework, rates);
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
        Date updatedDate = getStartDate().updateDate();
        return getEndDate().compareTo(updatedDate) < 0 // end date earlier than updated date
                ? getEndDate().getPreviousDate(updatedDate.getDayOfWeek())
                : updatedDate;
    }

    private boolean checkOverlapping(Lesson other) {
        requireNonNull(other);

        return !getStartDate().getLocalDate().isAfter(other.getEndDate().getLocalDate())
            && !other.getStartDate().getLocalDate().isAfter(getEndDate().getLocalDate());
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
            return checkOverlapping(otherLesson) // check if date range overlaps
                    && getDayOfWeek().equals(otherLesson.getDayOfWeek()) // same day
                    && getTimeRange().isClashing(otherLesson.getTimeRange());
        } else {
            return getLocalDate().compareTo(otherLesson.getLocalDate()) <= 0 // same date or before
                    && getDayOfWeek().equals(otherLesson.getDayOfWeek()) // same day
                    && getTimeRange().isClashing(otherLesson.getTimeRange());
        }
    }

}
