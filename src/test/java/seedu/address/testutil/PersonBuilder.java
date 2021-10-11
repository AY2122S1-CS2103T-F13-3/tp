package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonWithoutOwner;
import seedu.address.model.person.AcadStream;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355655";
    public static final String DEFAULT_EMAIL = "amy@example.com";
    public static final String DEFAULT_PARENT_PHONE = "";
    public static final String DEFAULT_PARENT_EMAIL = "";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SCHOOL = "";
    public static final String DEFAULT_ACAD_STREAM = "";
    public static final String DEFAULT_FEE = "";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Phone parentPhone;
    private Email parentEmail;
    private Address address;
    private School school;
    private AcadStream acadStream;
    private Fee fee;
    private Remark remark;
    private Set<Tag> tags;
    private Set<LessonWithoutOwner> lessons;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        parentPhone = new Phone(DEFAULT_PARENT_PHONE);
        parentEmail = new Email(DEFAULT_PARENT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        school = new School(DEFAULT_SCHOOL);
        acadStream = new AcadStream(DEFAULT_ACAD_STREAM);
        fee = new Fee(DEFAULT_FEE);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        lessons = new TreeSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        parentPhone = personToCopy.getParentPhone();
        parentEmail = personToCopy.getParentEmail();
        address = personToCopy.getAddress();
        school = personToCopy.getSchool();
        acadStream = personToCopy.getAcadStream();
        fee = personToCopy.getFee();
        remark = personToCopy.getRemark();
        tags = new HashSet<>(personToCopy.getTags());
        lessons = new TreeSet<>(
                personToCopy.getLessons().stream().map(Lesson::getLessonWithoutOwner).collect(Collectors.toSet()));
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Set<Lesson>} of the {@code Person} that we are building with multiple sample lessons.
     */
    public PersonBuilder withLessons() {
        this.lessons = SampleDataUtil.getSampleLessonsWithoutOwner();
        return this;
    }
    
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Set<Lesson>} of the {@code Person} that we are building with one sample lesson.
     */
    public PersonBuilder withSampleLesson() {
        Set<LessonWithoutOwner> lessonSetWithOneLesson = new TreeSet<>();
        lessonSetWithOneLesson.add(SampleDataUtil.getSampleLessonWithoutOwner());
        this.lessons = lessonSetWithOneLesson;
        return this;
    }
    
    public PersonBuilder withParentPhone(String parentPhone) {
        this.parentPhone = new Phone(parentPhone);
        return this;
    }

    /**
     * Sets the default Parent {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentPhone() {
        this.parentPhone = new Phone(DEFAULT_PARENT_PHONE);
        return this;
    }

    /**
     * Sets the Parent {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentEmail(String parentEmail) {
        this.parentEmail = new Email(parentEmail);
        return this;
    }

    /**
     * Sets the default Parent {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentEmail() {
        this.parentEmail = new Email(DEFAULT_PARENT_EMAIL);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code School} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchool(String school) {
        this.school = new School(school);
        return this;
    }

    /**
     * Sets the default {@code School} of the {@code Person} that we are building as blank.
     */
    public PersonBuilder withSchool() {
        this.school = new School(DEFAULT_SCHOOL);
        return this;
    }

    /**
     * Sets the {@code AcadStream} of the {@code Person} that we are building.
     */
    public PersonBuilder withAcadStream(String acadStream) {
        this.acadStream = new AcadStream(acadStream);
        return this;
    }

    /**
     * Sets the default {@code AcadStream} of the {@code Person} that we are building as blank.
     */
    public PersonBuilder withAcadStream() {
        this.acadStream = new AcadStream(DEFAULT_ACAD_STREAM);
        return this;
    }

    /**
     * Sets the {@code Fee} of the {@code Person} that we are building.
     */
    public PersonBuilder withFee(String fee) {
        this.fee = new Fee(fee);
        return this;
    }

    /**
     * Sets the default {@code Fee} of the {@code Person} that we are building.
     */
    public PersonBuilder withFee() {
        this.fee = new Fee(DEFAULT_FEE);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building as blank.
     */
    public PersonBuilder withRemark() {
        this.remark = new Remark(DEFAULT_REMARK);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds a person with the specified information.
     *
     * @return {@code Person} container the information given.
     */
    public Person build() {
        return new Person(lessons, name, phone, email, parentPhone, parentEmail,
            address, school, acadStream, fee, remark, tags);
    }
}
