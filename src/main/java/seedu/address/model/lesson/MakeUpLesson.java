package seedu.address.model.lesson;

import java.util.Set;

/**
 * Represents a Make Up Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MakeUpLesson extends Lesson {
    /**
     * Every field must be present and not null.
     *
     * @param date Date of lesson.
     * @param timeRange Time range of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     * @param rates Cost per lesson for the lesson.
     */
    public MakeUpLesson(Date date, TimeRange timeRange, Subject subject, Set<Homework> homework, LessonRates rates) {
        super(date, timeRange, subject, homework, rates);
    }

    /**
     * Check if the Lesson object is recurring.
     *
     * @return False
     */
    public boolean isRecurring() {
        return false;
    }

    /**
     * Get the date of the makeup lesson to display.
     * Date will be start date since a makeup lesson only has one date.
     *
     * @return startDate Start date of the makeup lesson.
     */
    @Override
    public Date getDisplayDate() {
        return getStartDate();
    }

    /**
     * Returns true if this {@code MakeUpLesson} clashes with the given {@code Lesson}.
     *
     * @param otherLesson The other lesson to be compared with.
     * @return True if and only if lessons clash.
     */
    @Override
    public boolean isClashing(Lesson otherLesson) {
        if (otherLesson.isRecurring()) {
            return getLocalDate().compareTo(otherLesson.getLocalDate()) >= 0 // same date or after
                    && getDayOfWeek().equals(otherLesson.getDayOfWeek()) // same day
                    && getTimeRange().isClashing(otherLesson.getTimeRange());
        } else {
            return getLocalDate().equals(otherLesson.getLocalDate())
                    && getTimeRange().isClashing(otherLesson.getTimeRange());
        }
    }

}

