package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MESSAGE_CLASHING_LESSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.LastUpdatedDate;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final JsonAdaptedLastUpdated lastUpdated;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("lastUpdated") JsonAdaptedLastUpdated lastUpdated) {
        this.persons.addAll(persons);
        this.lastUpdated = lastUpdated;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        lastUpdated = new JsonAdaptedLastUpdated(source.getLastUpdatedDate().value);
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            if (addressBook.hasClashingLesson(person.getLessons())) {
                throw new IllegalValueException(MESSAGE_CLASHING_LESSON);
            }
            addressBook.addPerson(person);
        }

        if (lastUpdated == null) {
            throw new IllegalValueException(JsonAdaptedLastUpdated.MISSING_FIELD_MESSAGE_FORMAT);
        }
        LastUpdatedDate lastUpdatedDate = lastUpdated.toModelType();
        addressBook.setLastUpdatedDate(lastUpdatedDate);

        return addressBook;
    }
}
