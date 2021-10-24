package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LessonEditCommand;
import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Homework;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class LessonEditCommandParser implements Parser<LessonEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LessonEditCommand
     * and returns a LessonEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_RECURRING, PREFIX_DATE, PREFIX_TIME,
                PREFIX_SUBJECT, PREFIX_HOMEWORK, PREFIX_RATES);

        // don't allow changes to type of lesson
        if (argMultimap.getValue(PREFIX_RECURRING).isPresent()) {
            throw new ParseException(LessonEditCommand.MESSAGE_ATTEMPT_TO_EDIT_TYPE);
        }

        Index[] indices;

        try {
            indices = ParserUtil.parseIndices(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LessonEditCommand.MESSAGE_USAGE), pe);
        }

        assert indices.length == 2;

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editLessonDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editLessonDescriptor.setTimeRange(ParserUtil.parseTimeRange(argMultimap.getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editLessonDescriptor.setSubject(ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get()));
        }
        parseHomeworkForLessonEdit(argMultimap.getAllValues(PREFIX_HOMEWORK))
            .ifPresent(editLessonDescriptor::setHomeworkSet);

        if (argMultimap.getValue(PREFIX_RATES).isPresent()) {
            editLessonDescriptor.setRate(ParserUtil.parseLessonRates(argMultimap.getValue(PREFIX_RATES).get()));
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(LessonEditCommand.MESSAGE_NOT_EDITED);
        }

        /*
        First index identifies the student; second identifies the lesson of the student to be edited.
         */
        return new LessonEditCommand(indices[0], indices[1], editLessonDescriptor);
    }

    /**
     * Parses {@code Collection<String> homework} into a {@code Set<Homework>} if {@code homework} is non-empty.
     * If {@code homework} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Homework>} containing zero homework.
     */
    private Optional<Set<Homework>> parseHomeworkForLessonEdit(Collection<String> homework) throws ParseException {
        assert homework != null;

        if (homework.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> homeworkSet = homework.size() == 1 && homework.contains("")
            ? Collections.emptySet()
            : homework;
        return Optional.of(ParserUtil.parseHomeworkList(homeworkSet));
    }
}
