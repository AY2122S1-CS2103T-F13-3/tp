package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns the date when the system was updated.
     */
    LastUpdatedDate getLastUpdatedDate();

    /**
     * Returns an unmodifiable view of the tag list.
     *
     * @return An unmodifiable view of the tag list.
     */
    ObservableList<Tag> getTagList();

    /** Returns an unmodifiable view of the tag counter map. */
    ObservableMap<Tag, Integer> getTagCounter();
}
