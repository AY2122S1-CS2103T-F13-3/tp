package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LessonAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;

/**
 * Parses input arguments and creates a new LessonAddCommand object.
 */
public class LessonAddCommandParser implements Parser<LessonAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LessonAddCommand
     * and returns a LessonAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECURRING, PREFIX_DATE, PREFIX_START_TIME,
                        PREFIX_END_TIME, PREFIX_SUBJECT, PREFIX_HOMEWORK);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_SUBJECT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonAddCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonAddCommand.MESSAGE_USAGE), pe);
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        /* Parse time and check if range is valid.
        Throws ParseException if either time is invalid
        or if range is invalid.
         */
        String startTime = argMultimap.getValue(PREFIX_START_TIME).get();
        String endTime = argMultimap.getValue(PREFIX_END_TIME).get();
        TimeRange timeRange = ParserUtil.parseTimeRange(startTime, endTime);

        Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        Set<Homework> homework = parseHomeworkForLessonAdd(argMultimap.getAllValues(PREFIX_HOMEWORK))
                .orElse(new HashSet<>());

        // Is a recurring lesson
        if (argMultimap.getValue(PREFIX_RECURRING).isPresent()) {
            RecurringLesson lesson = new RecurringLesson(date, timeRange, subject, homework);
            return new LessonAddCommand(index, lesson);
        }

        MakeUpLesson lesson = new MakeUpLesson(date, timeRange, subject, homework);
        return new LessonAddCommand(index, lesson);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> homework} into a {@code Set<Homework>} if {@code homework} is non-empty.
     * If {@code homework} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Homework>} containing zero homework.
     */
    private Optional<Set<Homework>> parseHomeworkForLessonAdd(Collection<String> homework) throws ParseException {
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
