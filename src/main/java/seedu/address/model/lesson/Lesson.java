package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
    private static final String RECURRING = "Recurring Lesson";
    private static final String MAKEUP = "MakeUp Lesson";

    // Time fields
    private final Date date;
    private final Time startTime;
    private final Time endTime;

    // Data fields
    private final Subject subject;
    private final Set<Homework> homework = new HashSet<>();

    /**
     * Every field must be present and not null.
     *
     * @param date Date of lesson.
     * @param startTime Start time of the lesson.
     * @param endTime End time of the lesson.
     * @param subject Subject of the lesson.
     * @param homework Homework for the lesson.
     */
    public Lesson(Date date, Time startTime, Time endTime, Subject subject, Set<Homework> homework) {
        requireAllNonNull(date, startTime, endTime, homework);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.homework.addAll(homework);
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Subject getSubject() {
        return subject;
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
     * Update the lesson date to the same day on the following week.
     *
     * @return newDate The date of the same day on the following week.
     */
    public Date updateDateWithWeek() {
        Date newDate = new Date(getDate().getLocalDate().plusDays(7).format(Date.FORMATTER));
        return newDate;
    }

    /**
     * Edit the date of the particular type of lesson.
     *
     * @param newDateString The date to be updated with.
     * @return {@code Lesson} with the updated date.
     */
    public abstract Lesson updateDate(String newDateString);

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
        return otherLesson.getDate().equals(getDate())
                && otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getEndTime())
                && otherLesson.getSubject().equals(getSubject())
                && otherLesson.getHomework().equals(getHomework())
                && otherLesson.isRecurring() == isRecurring();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, startTime, endTime, subject, homework);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String typeOfLesson = isRecurring() ? RECURRING : MAKEUP;
        builder.append(typeOfLesson)
            .append("\n")
            .append(getDate())
            .append("\n Start: ")
            .append(getStartTime())
            .append("    End: ")
            .append(getEndTime())
            .append("\n Subject: ")
            .append(getSubject());

        Set<Homework> homework = getHomework();
        if (!homework.isEmpty()) {
            builder.append("\n Homework: ");
            homework.forEach(builder::append);
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
        int compareDate = getDate().compareTo(other.getDate());
        int compareTime = getStartTime().compareTo(other.getStartTime());
        // Compare time if date is equal
        return compareDate == 0 ? compareTime : compareDate;
    }

}

