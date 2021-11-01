package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.AcadLevel;
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

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setParentPhone(person.getParentPhone());
        descriptor.setParentEmail(person.getParentEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setSchool(person.getSchool());
        descriptor.setAcadStream(person.getAcadStream());
        descriptor.setAcadLevel(person.getAcadLevel());
        descriptor.setFee(person.getFee());
        descriptor.setRemark(person.getRemark());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }
    /**
     *
     * Sets the Parent {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentPhone(String parentPhone) {
        descriptor.setParentPhone(new Phone(parentPhone));
        return this;
    }

    /**
     * Sets the Parent {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentEmail(String parentEmail) {
        descriptor.setParentEmail(new Email(parentEmail));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code School} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSchool(String school) {
        descriptor.setSchool(new School(school));
        return this;
    }

    /**
     * Sets the {@code AcadStream} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAcadStream(String acadStream) {
        descriptor.setAcadStream(new AcadStream(acadStream));
        return this;
    }

    /**
     * Sets the {@code AcadLevel} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAcadLevel(String acadLevel) {
        descriptor.setAcadLevel(new AcadLevel(acadLevel));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFee(String fee) {
        descriptor.setFee(new Fee(fee));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
