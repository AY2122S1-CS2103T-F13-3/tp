package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.ClashingLessonException;
import seedu.address.model.person.exceptions.LessonNotFoundException;

/**
 * A list of calendar entries of lessons that enforces no overlapping time ranges between its lessons,
 * and does not allow nulls.
 * A lesson is considered overlapping by comparing using {@code Lesson#isClashing(Lesson)}.
 * As such, adding and updating of entries uses Lesson#isClashing(Lesson) for checking to ensure that the lesson
 * being added or updated does not clash with other lessons in the CalendarEntryList.
 * However, the removal of a lesson uses Person#equals(Object) to ensure that the person with exactly the same fields
 * will be removed.
 *
 * @author Chesterwongz, with add-ons from Xiaoyunnn.
 *
 * @see Lesson#isClashing(Lesson)
 */
public class CalendarEntryList {
    private static final long TWO_DAY_DIFF = 2;

    private final Calendar calendar = new Calendar();
    private final List<Entry<Lesson>> entryList = new ArrayList<>();
    private final ObservableList<Entry<Lesson>> upcomingLessons = FXCollections.observableArrayList();

    public Calendar getCalendar() {
        return calendar;
    }

    private void add(Entry<Lesson> calendarEntry) {
        calendar.addEntry(calendarEntry);
        entryList.add(calendarEntry);
        if (isUpcoming(calendarEntry.getUserObject().getDisplayLocalDate())) {
            upcomingLessons.add(calendarEntry);
            sortUpcomingLessons();
        }
    }

    private void remove(Entry<Lesson> calendarEntry) {
        calendar.removeEntry(calendarEntry);
        entryList.remove(calendarEntry);
        upcomingLessons.remove(calendarEntry);
        sortUpcomingLessons();
    }

    private void clear() {
        calendar.clear();
        entryList.clear();
        upcomingLessons.clear();
    }

    /**
     * Retrieves the {@code Entry} that contains the {@code Lesson} we want to remove.
     * The Entry must exist within the {@code entryList}.
     *
     * @param toFind The Lesson whose Entry we want to find
     * @return The Entry that has the Lesson.
     * @throws LessonNotFoundException If the Entry does not exist.
     */
    private Entry<Lesson> getEntry(Lesson toFind) {
        return entryList.stream()
                .filter(entry -> entry.getUserObject().equals(toFind))
                .findFirst()
                .orElseThrow(LessonNotFoundException::new);
        // findFirst() assumes that there is only one lesson we need to delete
    }

    /**
     * Returns true if the specified lesson clashes with existing lessons.
     *
     * @param toCheck The lesson to check.
     * @return True if there is a clash in lesson timing, false otherwise.
     */
    public boolean hasClashes(Lesson toCheck) {
        requireNonNull(toCheck);
        return entryList.stream().anyMatch(entry-> entry.getUserObject().isClashing(toCheck));
    }

    /**
     * Returns true if the specified lesson clashes with existing lessons.
     *
     * @param toCheck The lesson to check.
     * @param toIgnore The lesson to ignore.
     * @return True if there is a clash in lesson timing, false otherwise.
     */
    public boolean hasClashes(Lesson toCheck, Lesson toIgnore) {
        requireNonNull(toCheck);
        requireNonNull(toIgnore);
        return entryList.stream().anyMatch(entry-> !entry.getUserObject().equals(toIgnore)
                && entry.getUserObject().isClashing(toCheck));
    }

    /**
     * Returns true if the entryList contains a Lesson that clashes with the specified Lessons.
     *
     * @param toCheck The lesson to check.
     * @return True if there is a clash in lesson timing, false otherwise.
     */
    public boolean hasClashes(Iterable<Lesson> toCheck) {
        requireAllNonNull(toCheck);
        for (Lesson lesson : toCheck) {
            if (hasClashes(lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not clash with any in the list.
     *
     * @param editedPerson the person we added the lesson to.
     * @param toAdd The lesson to add
     */
    private void addLesson(Person editedPerson, Lesson toAdd) {
        requireAllNonNull(editedPerson, toAdd);
        if (hasClashes(toAdd)) {
            throw new ClashingLessonException();
        }
        Entry<Lesson> entryToAdd = convertToEntry(editedPerson, toAdd);
        add(entryToAdd);
    }

    /**
     * Removes the equivalent Lesson from the Calendar.
     * The lesson must exist in the calendar.
     *
     * @param toRemove The lesson to remove.
     */
    private void removeLesson(Lesson toRemove) {
        requireNonNull(toRemove);
        Entry<Lesson> entryToRemove = getEntry(toRemove);
        remove(entryToRemove);
    }

    /**
     * Adds all lessons of the specified person into the calendar.
     *
     * @param person The person whose lesson we want to add.
     */
    public void addLessons(Person person) {
        requireAllNonNull(person);
        calendar.startBatchUpdates();
        for (Lesson lesson : person.getLessons()) {
            addLesson(person, lesson);
        }
        calendar.stopBatchUpdates();
    }

    /**
     * Removes all specified lessons from the calendar.
     *
     * @param person The person whose lessons are to be removed.
     */
    public void removeLessons(Person person) {
        requireAllNonNull(person);
        calendar.startBatchUpdates();
        for (Lesson lesson : person.getLessons()) {
            removeLesson(lesson);
        }
        calendar.stopBatchUpdates();
    }

    /**
     * Sets the {@code target} person's lessons to the {@code editedPerson} lessons.
     *
     * @param target The person whose lessons we want to remove.
     * @param editedPerson THe person whose lessons we want to add.
     */
    public void setLessons(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        removeLessons(target);
        addLessons(editedPerson);
    }

    /**
     * Replaces the entries of the calendar with {@code persons}.
     * {@code persons} must not contain clashing lessons.
     *
     * @param persons The list of persons we will use to build the {@code CalendarEntryList}.
     */
    public void resetLessons(List<Person> persons) {
        requireAllNonNull(persons);
        clear();
        for (Person person : persons) {
            addLessons(person);
        }
    }

    /**
     * Returns an unmodifiable view of upcoming lessons within two days from current time.
     * Lessons are sorted from the earliest date to the latest date.
     *
     * @return Unmodifiable observable list of upcoming lessons within two days.
     */
    public ObservableList<Entry<Lesson>> getUpcomingLessons() {
        sortUpcomingLessons();
        return FXCollections.unmodifiableObservableList(upcomingLessons);
    }

    /**
     * Returns true if the given date and time is within two days of current date time.
     *
     * @param endDate Date and time to be checked.
     * @return True if the given date and time is within two days of current date time.
     */
    private boolean isUpcoming(LocalDate endDate) {
        long timeDiff = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
        return timeDiff >= 0 && timeDiff < TWO_DAY_DIFF;
    }

    /**
     * Sorts the upcoming lessons from nearest to furthest date.
     */
    private void sortUpcomingLessons() {
        List<Entry<Lesson>> sortedList = upcomingLessons.stream()
                .sorted(Comparator.comparing(Entry::getEndAsLocalDateTime))
                .collect(Collectors.toList());
        upcomingLessons.setAll(sortedList);
    }

    /**
     * Converts a {@code Lesson} to a calendar {@code Entry} for CalendarFX.
     * Adapted from CalendarFX API example: https://dlsc.com/wp-content/html/calendarfx/apidocs/index.html
     *
     * @param lesson The lesson to be converted to a calendar entry.
     * @return The calendar entry that also contains this lesson.
     */
    private Entry<Lesson> convertToEntry(Person owner, Lesson lesson) {
        requireNonNull(lesson);

        Entry<Lesson> entry = new Entry<>();
        entry.setUserObject(lesson);
        Interval entryInterval = new Interval(lesson.getStartDateTime(), lesson.getEndDateTime());
        entry.setInterval(entryInterval);
        StringBuilder entryTitle = new StringBuilder(owner.getName().toString());
        entryTitle.append(" (").append(lesson.getSubject().toString()).append(")");
        if (lesson.isRecurring()) {
            entry.setRecurrenceRule("RRULE:FREQ=WEEKLY");
            entryTitle.append("(Recurring)");
        }
        entry.setTitle(entryTitle.toString());
        return entry;
    }

    /**
     * Returns true if the compared object is an equivalent {@code CalendarEntryList}.
     * Note that we don't use ArrayList#equals(Object) as we want to check if lessons are equal, not entries.
     * CalendarFX Entry#equals(Object) method does not check equality of the user object, and only checks Entry.id,
     * which we do not set, so we should not use it.
     *
     * @param obj Object to be compared.
     * @return True if the compared object is an equivalent {@code CalendarEntryList}.
     */
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CalendarEntryList)) {
            return false;
        }

        // state check
        CalendarEntryList other = (CalendarEntryList) obj;

        if (entryList.size() != other.entryList.size() || upcomingLessons.size() != other.upcomingLessons.size()) {
            return false;
        }

        for (int i = 0; i < entryList.size(); i++) {
            boolean equalPair = entryList.get(i).getUserObject().equals(other.entryList.get(i).getUserObject());
            if (!equalPair) {
                return false;
            }
        }

        for (int i = 0; i < upcomingLessons.size(); i++) {
            boolean equalPair = upcomingLessons.get(i).getUserObject()
                    .equals(other.upcomingLessons.get(i).getUserObject());
            if (!equalPair) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return calendar.hashCode();
    }
}
