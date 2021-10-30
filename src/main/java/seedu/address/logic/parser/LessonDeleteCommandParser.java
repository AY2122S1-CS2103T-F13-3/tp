package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_CHECK_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LessonDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LessonDeleteCommandParser implements Parser<LessonDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LessonDeleteCommand
     * and returns a LessonDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonDeleteCommand parse(String args) throws ParseException {
        try {
            Index[] studentLessonIndices = ParserUtil.parseIndices(args);
            Index index = studentLessonIndices[0];
            Index lessonIndex = studentLessonIndices[1];
            return new LessonDeleteCommand(index, lessonIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            String.format(MESSAGE_CHECK_INPUT, pe.getMessage()) + "\n"
                                    + LessonDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
