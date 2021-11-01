package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_STREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CommandUtil;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Edit Student";

    public static final String COMMAND_WORD = "edit";

    public static final String COMMAND_PARAMETERS = "INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_PARENT_PHONE + "PARENT_PHONE] "
            + "[" + PREFIX_PARENT_EMAIL + "PARENT_EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SCHOOL + "SCHOOL] "
            + "[" + PREFIX_ACAD_STREAM + "ACAD_STREAM] "
            + "[" + PREFIX_ACAD_LEVEL + "ACAD_LEVEL] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "You must provide at least one field to edit!";
    public static final String MESSAGE_CONTACT_REQUIRED = "This student must have at least one contact field!";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in TAB!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private Person personBeforeEdit;
    private Person personAfterEdit;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        personBeforeEdit = CommandUtil.getPerson(lastShownList, index);
        personAfterEdit = createEditedPerson(personBeforeEdit, editPersonDescriptor);

        if (!personBeforeEdit.isSamePerson(personAfterEdit) && model.hasPerson(personAfterEdit)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }
        if (!personAfterEdit.hasContactField()) {
            throw new CommandException(MESSAGE_CONTACT_REQUIRED);
        }

        model.setPerson(personBeforeEdit, personAfterEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, personAfterEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Phone updatedParentPhone = editPersonDescriptor.getParentPhone().orElse(personToEdit.getParentPhone());
        Email updatedParentEmail = editPersonDescriptor.getParentEmail().orElse(personToEdit.getParentEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        School updatedSchool = editPersonDescriptor.getSchool().orElse(personToEdit.getSchool());
        AcadStream updatedAcadStream = editPersonDescriptor.getAcadStream().orElse(personToEdit.getAcadStream());
        AcadLevel updatedAcadLevel = editPersonDescriptor.getAcadLevel().orElse(personToEdit.getAcadLevel());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        // This command does not allow the editing of lessons.
        Set<Lesson> updatedLessons = editPersonDescriptor.getLessons().orElse(personToEdit.getLessons());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedParentPhone, updatedParentEmail,
                updatedAddress, updatedSchool, updatedAcadStream, updatedAcadLevel, updatedRemark,
                updatedTags, updatedLessons);
    }

    @Override
    public void undo() {
        requireNonNull(model);

        model.setPerson(personAfterEdit, personBeforeEdit);
    }

    @Override
    protected void redo() {
        requireNonNull(model);

        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError(MESSAGE_REDO_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Phone parentPhone;
        private Email email;
        private Email parentEmail;
        private Address address;
        private School school;
        private AcadStream acadStream;
        private AcadLevel acadLevel;
        private Remark remark;
        private Set<Tag> tags;
        private Set<Lesson> lessons;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setParentPhone(toCopy.parentPhone);
            setEmail(toCopy.email);
            setParentEmail(toCopy.parentEmail);
            setAddress(toCopy.address);
            setSchool(toCopy.school);
            setAcadStream(toCopy.acadStream);
            setAcadLevel(toCopy.acadLevel);
            setRemark(toCopy.remark);
            setTags(toCopy.tags);
            setLessons(toCopy.lessons);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, parentPhone, parentEmail, address,
                    school, acadStream, acadLevel, remark, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setParentPhone(Phone parentPhone) {
            this.parentPhone = parentPhone;
        }

        public Optional<Phone> getParentPhone() {
            return Optional.ofNullable(parentPhone);
        }

        public void setParentEmail(Email parentEmail) {
            this.parentEmail = parentEmail;
        }

        public Optional<Email> getParentEmail() {
            return Optional.ofNullable(parentEmail);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public Optional<School> getSchool() {
            return Optional.ofNullable(school);
        }

        public void setAcadStream(AcadStream acadStream) {
            this.acadStream = acadStream;
        }

        public Optional<AcadStream> getAcadStream() {
            return Optional.ofNullable(acadStream);
        }

        public void setAcadLevel(AcadLevel acadLevel) {
            this.acadLevel = acadLevel;
        }

        public Optional<AcadLevel> getAcadLevel() {
            return Optional.ofNullable(acadLevel);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable lesson set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code lessons} is null.
         */
        public Optional<Set<Lesson>> getLessons() {
            return (lessons != null) ? Optional.of(Collections.unmodifiableSet(lessons)) : Optional.empty();
        }

        public void setLessons(Set<Lesson> lessons) {
            this.lessons = (lessons != null) ? new TreeSet<>(lessons) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getParentPhone().equals(e.getParentPhone())
                    && getParentEmail().equals(e.getParentEmail())
                    && getAddress().equals(e.getAddress())
                    && getSchool().equals(e.getSchool())
                    && getAcadStream().equals(e.getAcadStream())
                    && getAcadLevel().equals(e.getAcadLevel())
                    && getRemark().equals(e.getRemark())
                    && getTags().equals(e.getTags())
                    && getLessons().equals(e.getLessons());
        }
    }
}
