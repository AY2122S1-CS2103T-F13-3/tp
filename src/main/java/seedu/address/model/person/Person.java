package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Phone parentPhone;
    private final Email parentEmail;
    private final Address address;
    private final School school;
    private final AcadStream acadStream;
    private final AcadLevel acadLevel;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Lesson> lessons = new TreeSet<>();
    private final Fee outstandingFees;

    /**
     * Every field must be present and not null.
     *
     * @param name The name of this person.
     * @param phone The phone number of this person.
     * @param email The email of this person
     * @param parentPhone The parent's phone number of this person.
     * @param parentEmail The parent's email of this person.
     * @param address The address of this person.
     * @param school The school of this person.
     * @param acadStream The academic stream of this person.
     * @param acadLevel The academic level of this person.
     * @param remark Any remarks on this person.
     * @param tags Tags that categorise this person.
     * @param lessons The Set of Lessons objects that this person will become owner of.
     */
    public Person(Name name, Phone phone, Email email, Phone parentPhone, Email parentEmail,
                  Address address, School school, AcadStream acadStream, AcadLevel acadLevel,
                  Remark remark, Set<Tag> tags, Set<Lesson> lessons) {
        requireAllNonNull(name, phone, email, parentPhone, parentEmail, address,
                school, acadStream, acadLevel, remark, tags, lessons);
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
        this.tags.addAll(tags);
        this.lessons.addAll(lessons);
        outstandingFees = new Fee(lessons);
    }
    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getParentPhone() {
        return parentPhone;
    }

    public Email getParentEmail() {
        return parentEmail;
    }

    public Address getAddress() {
        return address;
    }

    public School getSchool() {
        return school;
    }

    public AcadStream getAcadStream() {
        return acadStream;
    }

    public AcadLevel getAcadLevel() {
        return acadLevel;
    }

    //requires a get Fee method

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Adds this person's tags to the UniqueTagList.
     *
     * @param tagList UniqueTagList to add tags to.
     */
    public void addTagsToTagList(UniqueTagList tagList) {
        requireNonNull(tagList);
        tags.forEach(tagList::addTag);
    }

    /**
     * Removes this person's tags to the UniqueTagList.
     *
     * @param tagList UniqueTagList to remove tags from.
     */
    public void removeTagsFromTagList(UniqueTagList tagList) {
        requireNonNull(tagList);
        tags.forEach(tagList::removeTag);
    }

    /**
     * Returns an immutable lesson set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Lesson> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }

    public Fee getOutstandingFees() {
        return outstandingFees;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if this person has at least one contact field not empty.
     */
    public boolean hasContactField() {
        return !(phone.value.isEmpty() && email.value.isEmpty()
                && parentPhone.value.isEmpty() && parentEmail.value.isEmpty());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getParentPhone().equals(getParentPhone())
                && otherPerson.getParentEmail().equals(getParentEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getSchool().equals(getSchool())
                && otherPerson.getAcadStream().equals(getAcadStream())
                && otherPerson.getAcadLevel().equals(getAcadLevel())
                && otherPerson.getRemark().equals(getRemark())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getLessons().equals(getLessons())
                && otherPerson.getOutstandingFees().equals(getOutstandingFees());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, parentPhone, parentEmail, address,
                school, acadStream, acadLevel, remark, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Address: ")
                .append(getAddress())
                .append("; Outstanding Fees: ")
                .append(getOutstandingFees());

        if (!getPhone().isEmpty()) {
            builder.append("; Phone: ").append(getPhone());
        }

        if (!getEmail().isEmpty()) {
            builder.append("; Email: ").append(getEmail());
        }

        if (!getParentPhone().isEmpty()) {
            builder.append("; Parent Phone: ").append(getParentPhone());
        }

        if (!getParentEmail().isEmpty()) {
            builder.append("; Parent Email: ").append(getParentEmail());
        }

        if (!getSchool().isEmpty()) {
            builder.append("; School: ").append(getSchool());
        }

        if (!getAcadStream().isEmpty()) {
            builder.append("; Academic Stream: ").append(getAcadStream());
        }

        if (!getAcadLevel().isEmpty()) {
            builder.append("; Academic Level: ").append(getAcadLevel());
        }

        if (!getRemark().isEmpty()) {
            builder.append("; Remark: ").append(getRemark());
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
