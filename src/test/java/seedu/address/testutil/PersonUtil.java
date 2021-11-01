package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_STREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonMatchesKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     *
     * @param person The person to add.
     * @return The add command for the person
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     *
     * @param person The person to get command string.
     * @return The command string for the person.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_PARENT_PHONE + person.getParentPhone().value + " ");
        sb.append(PREFIX_PARENT_EMAIL + person.getParentEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_SCHOOL + person.getSchool().value + " ");
        sb.append(PREFIX_ACAD_STREAM + person.getAcadStream().value + " ");
        sb.append(PREFIX_REMARK + person.getRemark().value + " ");
        person.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.getTagName() + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     *
     * @param descriptor The descriptor to get command string.
     * @return The command string for the descriptor.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getParentPhone().ifPresent(phone -> sb.append(PREFIX_PARENT_PHONE).append(phone.value).append(" "));
        descriptor.getParentEmail().ifPresent(email -> sb.append(PREFIX_PARENT_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getSchool().ifPresent(school -> sb.append(PREFIX_SCHOOL).append(school.value).append(" "));
        descriptor.getAcadStream().ifPresent(acadStream ->
                sb.append(PREFIX_ACAD_STREAM).append(acadStream.value).append(" "));
        descriptor.getAcadLevel().ifPresent(acadLevel ->
                sb.append(PREFIX_ACAD_LEVEL).append(acadLevel.value).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_REMARK).append(remark.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getTagName()).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code PersonMatchesKeywordsPredicate}'s details.
     *
     * @param predicate The predicate to get command string.
     * @return The command string for the predicate.
     */
    public static String getPersonMatchesKeywordsPredicateDetails(PersonMatchesKeywordsPredicate predicate) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_FIND_CONDITION).append(predicate.getCondition()).append(" ");
        predicate.getNameKeywords().ifPresent(keywords ->
                sb.append(PREFIX_NAME).append(String.join(" ", keywords)).append(" "));
        predicate.getPhoneKeywords().ifPresent(keywords ->
                sb.append(PREFIX_PHONE).append(String.join(" ", keywords)).append(" "));
        predicate.getEmailKeywords().ifPresent(keywords ->
                sb.append(PREFIX_EMAIL).append(String.join(" ", keywords)).append(" "));
        predicate.getParentPhoneKeywords().ifPresent(keywords ->
                sb.append(PREFIX_PARENT_PHONE).append(String.join(" ", keywords)).append(" "));
        predicate.getParentEmailKeywords().ifPresent(keywords ->
                sb.append(PREFIX_PARENT_EMAIL).append(String.join(" ", keywords)).append(" "));
        predicate.getAddressKeywords().ifPresent(keywords ->
                sb.append(PREFIX_ADDRESS).append(String.join(" ", keywords)).append(" "));
        predicate.getSchoolKeywords().ifPresent(keywords ->
                sb.append(PREFIX_SCHOOL).append(String.join(" ", keywords)).append(" "));
        predicate.getAcadStreamKeywords().ifPresent(keywords ->
                sb.append(PREFIX_ACAD_STREAM).append(String.join(" ", keywords)).append(" "));
        predicate.getAcadLevelKeywords().ifPresent(keywords ->
                sb.append(PREFIX_ACAD_LEVEL).append(String.join(" ", keywords)).append(" "));
        if (predicate.getTagKeywords().isPresent()) {
            List<String> tags = predicate.getTagKeywords().get();
            if (!tags.isEmpty()) {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s).append(" "));
            }
        }
        return sb.toString();
    }
}
