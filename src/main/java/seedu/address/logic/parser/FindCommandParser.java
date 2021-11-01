package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_STREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindCondition;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonMatchesKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FIND_CONDITION, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL, PREFIX_ADDRESS,
                PREFIX_SCHOOL, PREFIX_ACAD_STREAM, PREFIX_ACAD_LEVEL, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicate.setNameKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            predicate.setPhoneKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            predicate.setEmailKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_PARENT_PHONE).isPresent()) {
            predicate.setParentPhoneKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_PARENT_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_PARENT_EMAIL).isPresent()) {
            predicate.setParentEmailKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_PARENT_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicate.setAddressKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_SCHOOL).isPresent()) {
            predicate.setSchoolKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_SCHOOL).get()));
        }
        if (argMultimap.getValue(PREFIX_ACAD_STREAM).isPresent()) {
            predicate.setAcadStreamKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_ACAD_STREAM).get()));
        }
        if (argMultimap.getValue(PREFIX_ACAD_LEVEL).isPresent()) {
            predicate.setAcadLevelKeywords(ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_ACAD_LEVEL).get()));
        }

        Optional<List<String>> tagsKeywords = parseTagsForFind(argMultimap.getAllValues(PREFIX_TAG));
        tagsKeywords.ifPresent(predicate::setTagKeywords);

        if (!predicate.isAnyFieldSearched()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Find condition is optional and defaults to match all fields
        if (argMultimap.getValue(PREFIX_FIND_CONDITION).isPresent()) {
            FindCondition condition = ParserUtil.parseFindCondition(argMultimap.getValue(PREFIX_FIND_CONDITION).get());
            predicate.setCondition(condition);
        }

        return new FindCommand(predicate);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<List<String>> parseTagsForFind(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseTagKeywords(tags));
    }
}
