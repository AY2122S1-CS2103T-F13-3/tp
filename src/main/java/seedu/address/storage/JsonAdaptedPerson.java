package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.AcadLevel;
import seedu.address.model.person.AcadStream;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MESSAGE_CLASHING_LESSON = "Student contains clashing lesson(s)!";
    public static final String MESSAGE_MISSING_CONTACT = "Student does not have any contact information!";
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String parentPhone;
    private final String parentEmail;
    private final String address;
    private final String school;
    private final String acadStream;
    private final String acadLevel;
    private final String remark;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("parent phone") String parentPhone,
                             @JsonProperty("parent email") String parentEmail, @JsonProperty("address") String address,
                             @JsonProperty("school") String school, @JsonProperty("acadStream") String acadStream,
                             @JsonProperty("acadLevel") String acadLevel, @JsonProperty("remark") String remark,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.address = address;
        this.school = school;
        this.acadStream = acadStream;
        this.acadLevel = acadLevel;
        this.remark = remark;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        parentPhone = source.getParentPhone().value;
        parentEmail = source.getParentEmail().value;
        address = source.getAddress().value;
        school = source.getSchool().value;
        acadStream = source.getAcadStream().value;
        acadLevel = source.getAcadLevel().value;
        remark = source.getRemark().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        lessons.addAll(source.getLessons().stream()
                .map(JsonAdaptedLesson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        checkNullFields();
        checkContactFields();

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Lesson> personLessons = new ArrayList<>();
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (personLessons.stream().anyMatch(personLesson -> personLesson.isClashing(lesson))) {
                throw new IllegalValueException(MESSAGE_CLASHING_LESSON);
            }
            personLessons.add(lesson);
        }

        String strippedName = name.strip();
        if (!Name.isValidName(strippedName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(strippedName);

        String strippedPhone = phone.strip();
        if (!Phone.isValidPhone(strippedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(strippedPhone);

        String strippedEmail = email.strip();
        if (!Email.isValidEmail(strippedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(strippedEmail);

        String strippedParentPhone = parentPhone.strip();
        if (!Phone.isValidPhone(strippedParentPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelParentPhone = new Phone(strippedParentPhone);

        String strippedParentEmail = parentEmail.strip();
        if (!Email.isValidEmail(strippedParentEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelParentEmail = new Email(strippedParentEmail);

        String strippedAddress = address.strip();
        if (!Address.isValidAddress(strippedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(strippedAddress);

        String strippedSchool = school.strip();
        final School modelSchool = new School(strippedSchool);

        String strippedAcadStream = acadStream.strip();
        final AcadStream modelAcadStream = new AcadStream(strippedAcadStream);

        String strippedAcadLevel = acadLevel.strip();
        if (!AcadLevel.isValidAcadLevel(strippedAcadLevel)) {
            throw new IllegalValueException(AcadLevel.MESSAGE_CONSTRAINTS);
        }
        final AcadLevel modelAcadLevel = new AcadLevel(strippedAcadLevel);

        String strippedRemark = remark.strip();
        final Remark modelRemark = new Remark(strippedRemark);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Set<Lesson> modelLessons = new TreeSet<>(personLessons);

        return new Person(modelName, modelPhone, modelEmail, modelParentPhone, modelParentEmail,
                modelAddress, modelSchool, modelAcadStream, modelAcadLevel, modelRemark, modelTags,
                modelLessons);
    }

    private void checkContactFields() throws IllegalValueException {
        if (phone.isBlank() && parentPhone.isBlank() && email.isBlank() && parentEmail.isBlank()) {
            throw new IllegalValueException(MESSAGE_MISSING_CONTACT);
        }
    }

    private void checkNullFields() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (parentPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (parentEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (school == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
        }
        if (acadStream == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AcadStream.class.getSimpleName()));
        }
        if (acadLevel == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AcadLevel.class.getSimpleName()));
        }
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
    }
}
