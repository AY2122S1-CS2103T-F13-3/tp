package seedu.address.model;


import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person that has clashing lesson with {@code person} exists in the address book.
     */
    boolean hasClashingLesson(Lesson lesson);

    /**
     * Returns true if a person that has clashing lesson with {@code person} exists in the address book.
     */
    boolean hasClashingLesson(Lesson lesson, Lesson lessonToIgnore);

    /**
     * Returns the set of clashing lessons for {@code lesson}in the address book.
     */
    Set<String> getClashingLessonsString(Lesson lesson);

    /**
     * Returns the set of clashing lessons for {@code lesson}in the address book.
     */
    Set<String> getClashingLessonsString(Lesson lesson, Lesson lessonToIgnore);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    void addPersonAtIndex(Person person, Index targetIndex);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the Calendar.
     */
    Calendar getCalendar();

    /**
     * Returns a list of upcoming lessons within the next two days.
     *
     * @return List of upcoming lessons within the next two days.
     */
    ObservableList<Entry<Lesson>> getUpcomingLessons();

    /**
     * Updates the list of upcoming lessons.
     */
    void updateUpcomingLessons();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Returns an immutable Last Updated Date object. */
    LastUpdatedDate getLastUpdatedDate();

    /**
     * Sets the last updated date to today.
     */
    void setLastUpdatedDate();

    /** Returns an unmodifiable view of the observable tag list */
    ObservableList<Tag> getObservableTagList();

    /** Returns the tag counter map. */
    ObservableMap<Tag, Integer> getTagCounter();
}
