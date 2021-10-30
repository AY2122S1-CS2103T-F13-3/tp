package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Lesson implements Comparable<Lesson> {

    // Types of lesson
    private static final String RECURRING = "Recurring";
    private static final String MAKEUP = "Makeup";

    // Time fields
    private final Date startDate;
    private final Date endDate;
    private final TimeRange timeRange;

    // Data fields
    private final Subject subject;
    private final Set<Homework> homework = new HashSet<>();
    private final Set<Date> cancelledDates = new HashSet<>();

    // Fees calculation related fields
    private final LessonRates lessonRates;
    private final OutstandingFees outstandingFees;

    /**
     * Every field must be present and not null.
     *
     * @param date Date of lesson.
     * @param timeRange Time range of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     * @param rates Cost per hour for the lesson.
     * @param fees Outstanding fees that student has not paid for this lesson.
     * @param cancelledDates Cancelled dates of the lesson.
     */
    public Lesson(Date date, Date endDate, TimeRange timeRange, Subject subject, Set<Homework> homework,
                  LessonRates rates, OutstandingFees fees, Set<Date> cancelledDates) {
        requireAllNonNull(date, endDate, timeRange, subject, homework, rates, fees, cancelledDates);
        this.startDate = date;
        this.endDate = endDate;
        this.timeRange = timeRange;
        this.subject = subject;
        this.homework.addAll(homework);
        this.lessonRates = rates;
        this.outstandingFees = fees;
        if (!isRecurring()) {
            // non-recurring lesson should have maximum one cancelled date
            assert cancelledDates.size() <= 1;
        }
        this.cancelledDates.addAll(cancelledDates);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public LocalDate getLocalDate() {
        return startDate.getLocalDate();
    }

    public DayOfWeek getDayOfWeek() {
        return startDate.getDayOfWeek();
    }

    public boolean hasStarted() {
        return startDate.isOver();
    }

    public Subject getSubject() {
        return subject;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public LocalDateTime getStartDateTime() {
        return timeRange.getStart().atDate(startDate.getLocalDate());
    }

    public LocalDateTime getEndDateTime() {
        return timeRange.getEnd().atDate(startDate.getLocalDate());
    }

    public String getTypeOfLesson() {
        return isRecurring() ? RECURRING : MAKEUP;
    }

    public LessonRates getLessonRates() {
        return lessonRates;
    }

    public OutstandingFees getOutstandingFees() {
        return outstandingFees;
    }

    /**
     * Returns an immutable homework set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Homework> getHomework() {
        return Collections.unmodifiableSet(homework);
    }

    /**
     * Returns an immutable cancelledDates set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Date> getCancelledDates() {
        return Collections.unmodifiableSet(cancelledDates);
    }

    /**
     * Returns a lesson with the same details but updated cancelled dates.
     *
     * @param updatedCancelledDates A set of cancelled dates of the lesson.
     * @return Lesson with updated cancelled dates.
     */
    public abstract Lesson updateCancelledDates(Set<Date> updatedCancelledDates);

    /**
     * Checks if the Lesson object is recurring.
     *
     * @return True if it is a recurring lesson, false otherwise.
     */
    public abstract boolean isRecurring();

    /**
     * Gets the date of the lesson to display to the user.
     */
    public abstract Date getDisplayDate();

    /**
     * Gets the local date of the lesson to display to the user.
     *
     * @return {@code LocalDate} to be displayed.
     */
    public LocalDate getDisplayLocalDate() {
        return getDisplayDate().getLocalDate();
    }

    /**
     * Gets the end {@code LocalDateTime} to be displayed.
     *
     * @return End {@code LocalDateTime} to be displayed.
     */
    public LocalDateTime getDisplayEndLocalDateTime() {
        return timeRange.getEnd().atDate(getDisplayLocalDate());
    }

    /**
     * Returns true both lessons clash.
     *
     * @param otherLesson The other lesson to be compared with.
     * @return True if and only if lessons clash.
     */
    public abstract boolean isClashing(Lesson otherLesson);

    /**
     * Checks if this lesson occurs on a given date.
     *
     * @param date The lesson date to check.
     * @return True if this lesson occurs on the date.
     */
    public abstract boolean hasLessonOnDate(Date date);

    /**
     * Checks if this lesson is cancelled and does not occur on any date.
     *
     * @return True if lesson is cancelled.
     */
    public abstract boolean isCancelled();

    /**
     * Checks if both lessons have the same data fields.
     * This defines a stronger notion of equality between two lessons.
     *
     * @param other The other object to compare.
     * @return True if all fields are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;

        return otherLesson.getStartDate().equals(getStartDate())
                && otherLesson.getEndDate().equals(getEndDate())
                && otherLesson.getTimeRange().equals(getTimeRange())
                && otherLesson.getSubject().equals(getSubject())
                && otherLesson.getHomework().equals(getHomework())
                && otherLesson.getLessonRates().equals(getLessonRates())
                && otherLesson.getOutstandingFees().equals(getOutstandingFees())
                && otherLesson.getCancelledDates().equals(getCancelledDates())
                && otherLesson.isRecurring() == isRecurring();
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, timeRange, subject, homework,
                lessonRates, outstandingFees, cancelledDates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String typeOfLesson = isRecurring() ? RECURRING : MAKEUP;

        builder.append(typeOfLesson)
                .append(" ")
                .append("Start Date: ")
                .append(getStartDate());

        if (!getEndDate().equals(Date.MAX_DATE)) {
            builder.append("; End Date: ")
                   .append(getEndDate());
        }


        builder.append("; Date: ")
                .append(getDisplayDate())
                .append("; Time: ")
                .append(getTimeRange())
                .append("; Subject: ")
                .append(getSubject())
                .append("; Outstanding Fees: ")
                .append(getOutstandingFees())
                .append("; Lesson Rates: ")
                .append(getLessonRates());

        Set<Homework> homework = getHomework();
        if (!homework.isEmpty()) {
            builder.append("; Homework: ");
            homework.forEach(x -> builder.append(x + "; "));
        } else {
            builder.append("; ");
        }
        if (isCancelled()) {
            builder.append("(Cancelled)");
            return builder.toString();
        }
        String dates = getCancelledDates().stream().sorted()
                .map(Date::toString).collect(Collectors.joining(","));

        if (!dates.isEmpty()) {
            builder.append("Cancelled Dates: ")
                    .append(dates);
        }
        return builder.toString();
    }

    /**
     * Compares this Lesson object with the other Lesson object.
     *
     * @param other The Lesson object to compare with.
     * @return 1, if this is later than other;0 if equal; -1 if this is earlier.
     */
    @Override
    public int compareTo(Lesson other) {
        // enforces order as cancelled lessons could be equal
        if (isCancelled() || other.isCancelled()) {
            return -1;
        }
        /*
        Date represents the date of this lesson that is relevant to the comparison.
        i.e. The upcoming date for recurring lessons; or the start date for makeup lessons
        (since makeup lesson is a one-time event).
         */
        int compareDate = getDisplayDate().compareTo(other.getDisplayDate());
        int compareTime = getTimeRange().compareTo(other.getTimeRange());
        // Compare time if date is equal
        return compareDate == 0 ? compareTime : compareDate;
    }

}
