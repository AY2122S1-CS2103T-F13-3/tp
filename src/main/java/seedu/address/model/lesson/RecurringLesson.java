package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Represents a Recurring Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RecurringLesson extends Lesson {
    /**
     * Every field must be present and not null.
     *
     * @param date           Date of lesson.
     * @param endDate        End date of the recurrence.
     * @param timeRange      Time range of the lesson.
     * @param subject        Subject of the lesson.
     * @param homework       Homework for the lesson.
     * @param rates          Cost per lesson for the lesson.
     * @param fees           Outstanding fees for the lesson.
     * @param cancelledDates Cancelled dates of the lesson.
     */
    public RecurringLesson(Date date, Date endDate, TimeRange timeRange, Subject subject, Set<Homework> homework,
                           LessonRates rates, OutstandingFees fees, Set<Date> cancelledDates) {
        super(date, endDate, timeRange, subject, homework, rates, fees, cancelledDates);
    }

    /**
     * Returns a lesson with the same details but updated cancelled dates.
     *
     * @param updatedCancelledDates A set of cancelled dates of the lesson.
     * @return Lesson with updated cancelled dates.
     */
    @Override
    public Lesson updateCancelledDates(Set<Date> updatedCancelledDates) {
        return new RecurringLesson(getStartDate(), getEndDate(), getTimeRange(),
                getSubject(), getHomework(), getLessonRates(), getOutstandingFees(), updatedCancelledDates);
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
        Date updatedDate = getStartDate().updateDate(getCancelledDates());
        return getEndDate().isBefore(updatedDate) // end date earlier than updated date
                ? Collections.max(Arrays.asList(getEndDate().getPreviousDate(getDayOfWeek()),
                getStartDate())) // earliest date to display is start date
                : updatedDate;
    }

    private boolean checkOverlapping(Lesson other) {
        requireNonNull(other);

        return !getStartDate().getLocalDate().isAfter(other.getEndDate().getLocalDate()) // <=
                && !other.getStartDate().getLocalDate().isAfter(getEndDate().getLocalDate()); // <=
    }

    /**
     * Returns true if the date intervals intersect with each other, not considering
     * cancelled dates as intersection.
     *
     * @param other Other lesson to check for intersection.
     * @return True if they intersect
     */
    private boolean checkIntersection(Lesson other) {
        // Non-terminating recurrence
        if (getEndDate().equals(other.getEndDate()) && getEndDate().equals(Date.MAX_DATE)) {
            return getDayOfWeek().equals(other.getDayOfWeek());
        }

        Set<Date> cancelledDates = getCancelledDates();
        Set<Date> otherCancelledDates = other.getCancelledDates();

        // get the intersection
        // Code reuse from https://stackoverflow.com/questions/60785426/
        LocalDate laterStart = Collections.max(Arrays.asList(getLocalDate(), other.getLocalDate()));
        LocalDate earlierEnd = Collections.min(Arrays.asList(getEndDate().getLocalDate(),
                other.getEndDate().getLocalDate()));
        // 3 points, 2 interval
        // adjust end date to same day of week
        LocalDate earlierEndDate = earlierEnd.with(TemporalAdjusters.previousOrSame(getDayOfWeek()));
        long numberOfOverlappingDates = ChronoUnit.WEEKS.between(laterStart, earlierEndDate) + 1;

        // Get the number of cancelled dates from this lesson within this intersection
        // minus the duplicates with the other lesson
        Set<Date> cancelledDatesWithinIntersection = cancelledDates.stream()
                .filter(date -> !date.getLocalDate().isBefore(laterStart)
                        && !date.getLocalDate().isAfter(earlierEnd))
                .filter(date -> !otherCancelledDates.contains(date))
                .collect(Collectors.toSet());

        // get the number of cancelled dates fro mthe other lesson within this intersection
        Set<Date> otherCancelledDatesWithinIntersection = otherCancelledDates.stream()
                .filter(date -> date.getLocalDate().compareTo(laterStart) >= 0
                        && date.getLocalDate().compareTo(earlierEnd) <= 0)
                .collect(Collectors.toSet());

        long numberOfUniqueCancelledDates = cancelledDatesWithinIntersection.size()
                + otherCancelledDatesWithinIntersection.size();

        return numberOfUniqueCancelledDates < numberOfOverlappingDates;
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
            return !otherLesson.isCancelled() && !isCancelled()
                    && checkOverlapping(otherLesson) // check if date range overlaps
                    && getDayOfWeek().equals(otherLesson.getDayOfWeek()) // same day
                    && getTimeRange().isClashing(otherLesson.getTimeRange())
                    && checkIntersection(otherLesson); //check if cancelled dates number the size of intersection
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
        if (getEndDate().equals(Date.MAX_DATE)) {
            // never fully cancelled
            return false;
        }

        // number of weeks between start and end plus the start
        long numLessons = ChronoUnit.WEEKS.between(getStartDate().getLocalDate(),
                getEndDate().getLocalDate()) + 1;

        return numLessons == getCancelledDates().size();
    }

    /**
     * Checks if this lesson occurs on a given date.
     *
     * @param date The lesson date to check.
     * @return True if this lesson occurs on the date.
     */
    @Override
    public boolean hasLessonOnDate(Date date) {
        return date.isOnRecurringDate(getStartDate(), getEndDate()) // other date lies on a recurring lesson date
                && !getCancelledDates().contains(date); // other date is not a cancelled date
    }

}
