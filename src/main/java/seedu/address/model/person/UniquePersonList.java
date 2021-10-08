package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import com.calendarfx.model.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CalendarUtil;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.exceptions.ClashingLessonException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements, no overlapping lessons, and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}.
 * A person's lesson is considered unique by comparing using {@code Person#hasClashingLessons(Lesson)}.
 * As such, adding and updating of persons uses Person#isSamePerson(Person) for equality to ensure that the person
 * being added or updated is unique in terms of identity and lessons in the UniquePersonList.
 * However, the removal of a person uses Person#equals(Object) to ensure that the person with exactly the same fields
 * will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 * @see Person#hasClashingLessons(Lesson)
 */
public class UniquePersonList implements Iterable<Person> {

    private final Calendar calendar = new Calendar();
    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains a person with a clashing lesson.
     *
     * @param toCheck The lesson to check.
     * @return True if there is a clash in lesson timing, false otherwise.
     */
    public boolean hasClashes(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(person -> person.hasClashingLessons(toCheck));
    }

    /**
     * Returns true if a person that has clashing lessons with {@code person} exists in the address book.
     */
    public boolean hasClashes(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(person -> person.hasClashingLessons(toCheck));
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not clash with any in the list.
     */
    public void addLesson(Person target, Person editedPerson, Lesson toAdd) {
        requireNonNull(toAdd);
        if (hasClashes(toAdd)) {
            throw new ClashingLessonException();
        }
        setPerson(target, editedPerson);
        calendar.addEntry(toAdd.asCalendarEntry());
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
        for (Lesson lesson: toRemove.getLessons()) {
            calendar.removeEntry(lesson.asCalendarEntry());
        }
    }

    /**
     * Removes the equivalent Lesson from the Calendar.
     * The lesson must exist in the calendar.
     */
    public void removeLesson(Person target, Person editedPerson, Lesson toRemove) {
        requireAllNonNull(target, editedPerson, toRemove);

        setPerson(target, editedPerson);
        calendar.removeEntry(toRemove.asCalendarEntry());
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        setLessons(replacement);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        internalList.setAll(persons);
        setLessons(persons);
    }

    public void setLessons(UniquePersonList replacement) {
        requireNonNull(replacement);
        calendar.clear();
        for (Person person: replacement.internalList) {
            for (Lesson lesson: person.getLessons()) {
                calendar.addEntry(lesson.asCalendarEntry());
            }
        }
    }

    public void setLessons(List<Person> persons) {
        requireAllNonNull(persons);
        calendar.clear();
        for (Person person : persons) {
            for (Lesson lesson : person.getLessons()) {
                calendar.addEntry(lesson.asCalendarEntry());
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
