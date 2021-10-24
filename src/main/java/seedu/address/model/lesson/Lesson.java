package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private final TimeRange timeRange;

    // Data fields
    private final Subject subject;
    private final Set<Homework> homework = new HashSet<>();

    // Lesson Rates
    private final LessonRates lessonRates;

    /**
     * Every field must be present and not null.
     *
     * @param date Date of lesson.
     * @param timeRange Time range of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     * @param rates Cost per hour for the lesson.
     */
    public Lesson(Date date, TimeRange timeRange, Subject subject, Set<Homework> homework, LessonRates rates) {
        requireAllNonNull(date, timeRange, subject, homework);
        this.startDate = date;
        this.timeRange = timeRange;
        this.subject = subject;
        this.homework.addAll(homework);
        this.lessonRates = rates;
    }

    public Date getStartDate() {
        return startDate;
    }

    public LocalDate getLocalDate() {
        return startDate.getLocalDate();
    }

    public DayOfWeek getDayOfWeek() {
        return startDate.getDayOfWeek();
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

    /**
     * Returns an immutable homework set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Homework> getHomework() {
        return Collections.unmodifiableSet(homework);
    }

    /**
     * Check if the Lesson object is recurring.
     *
     * @return True if it is a recurring lesson, false otherwise.
     */
    public abstract boolean isRecurring();

    /**
     * Get the date of the lesson to display to the user.
     */
    public abstract Date getDisplayDate();

    /**
     * Returns true both lessons clash.
     *
     * @param otherLesson The other lesson to be compared with.
     * @return True if and only if lessons clash.
     */
    public abstract boolean isClashing(Lesson otherLesson);

    /**
     * Check if both lessons have the same data fields.
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
            && otherLesson.getTimeRange().equals(getTimeRange())
            && otherLesson.getSubject().equals(getSubject())
            && otherLesson.getHomework().equals(getHomework())
            && otherLesson.getLessonRates().equals(getLessonRates())
            && otherLesson.isRecurring() == isRecurring();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startDate, timeRange, subject, homework);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String typeOfLesson = isRecurring() ? RECURRING : MAKEUP;
        builder.append(typeOfLesson)
            .append(" ")
            .append(getDisplayDate())
            .append("; Time: ")
            .append(getTimeRange())
            .append("; Subject: ")
            .append(getSubject())
            .append("; Lesson Rates: ")
            .append(getLessonRates());

        Set<Homework> homework = getHomework();
        if (!homework.isEmpty()) {
            builder.append("; Homework: ");
            homework.forEach(x -> builder.append(x + "; "));
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
