package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Homework;

/**
 * Jackson-friendly version of {@link Homework}.
 */
class JsonAdaptedHomework {

    private final String description;

    /**
     * Constructs a {@code JsonAdaptedHomework} with the given {@code description}.
     */
    @JsonCreator
    public JsonAdaptedHomework(String description) {
        this.description = description;
    }

    /**
     * Converts a given {@code Homework} into this class for Jackson use.
     */
    public JsonAdaptedHomework(Homework source) {
        description = source.description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    /**
     * Converts this Jackson-friendly adapted homework object into the model's {@code Homework} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted homework.
     */
    public Homework toModelType() throws IllegalValueException {
        String strippedDescription = description.strip();
        if (!Homework.isValidDescription(strippedDescription)) {
            throw new IllegalValueException(Homework.MESSAGE_CONSTRAINTS);
        }
        return new Homework(strippedDescription);
    }

}
