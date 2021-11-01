package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.index.Index;
import seedu.address.model.lesson.CalendarEntryList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final CalendarEntryList entries;
    private final UniqueTagList tags;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        entries = new CalendarEntryList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        entries.resetLessons(persons);
        tags.addTagFromPersonList(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if {@code lesson} clashes with an existing lesson in the address book.
     */
    public boolean hasClashingLesson(Lesson lesson) {
        requireNonNull(lesson);
        return entries.hasClashes(lesson);
    }

    /**
     * Returns true if a person that has clashing lesson with {@code person} exists in the address book.
     */
    public boolean hasClashingLesson(Lesson lesson, Lesson lessonToIgnore) {
        requireNonNull(lesson);
        return entries.hasClashes(lesson, lessonToIgnore);
    }

    /**
     * Returns true if any of the specified lessons clashes with existing lesson in the address book.
     */
    public boolean hasClashingLesson(Iterable<Lesson> lessons) {
        requireAllNonNull(lessons);
        return entries.hasClashes(lessons);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     *
     * @param p Person to be added to the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        entries.addLessons(p);
        tags.addTagFromPerson(p);
    }

    /**
     * Adds a person to a specific index in address book.
     * Called when undoing a Delete Command.
     *
     * @param index Index in the list for which the person has to be added to.
     * @param p Person to be added.
     */
    public void addPerson(Index index, Person p) {
        persons.add(p, index);
        entries.addLessons(p);
        tags.addTagFromPerson(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
        entries.setLessons(target, editedPerson);
        tags.editTagFromPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        entries.removeLessons(key);
        tags.removeTagFromPerson(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons\n"
                + persons;
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the tag list.
     *
     * @return An unmodifiable view of the tag list.
     */
    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableTagList();
    }

    @Override
    public ObservableMap<Tag, Integer> getTagCounter() {
        return tags.asUnmodifiableMap();
    }

    public Calendar getCalendar() {
        // TODO: Make defensive
        return entries.getCalendar();
    }

    public ObservableList<Entry<Lesson>> getUpcomingLessons() {
        return entries.getUpcomingLessons();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof AddressBook)) {
            return false;
        }

        // state check
        AddressBook other = (AddressBook) obj;
        return persons.equals(other.persons)
                && entries.equals(other.entries)
                && tags.equals(((AddressBook) other).tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, tags);
    }
}
