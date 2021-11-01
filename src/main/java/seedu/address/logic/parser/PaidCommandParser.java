package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Money;

/**
 * Parses input arguments and creates a new PaidCommand object.
 */
public class PaidCommandParser implements Parser<PaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PaidCommand
     * and returns a PaidCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public PaidCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PAID_AMOUNT);

        if (!argumentMultimap.getValue(PREFIX_PAID_AMOUNT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
        }

        Index[] indices;

        try {
            indices = ParserUtil.parseIndices(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE), pe);
        }

        assert indices.length == 2;

        Money payment = ParserUtil.parseMoney(argumentMultimap.getValue(PREFIX_PAID_AMOUNT).get());

        Index studentIndex = indices[0];
        Index lessonIndex = indices[1];

        return new PaidCommand(studentIndex, lessonIndex, payment);
    }
}
