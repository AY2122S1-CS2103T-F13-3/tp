package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OUTSTANDING_FEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.LessonAddCommand;
import seedu.address.logic.commands.LessonDeleteCommand;
import seedu.address.logic.commands.LessonEditCommand;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class for Lesson.
 */
public class LessonUtil {
    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getLessonAddCommand(int index, Lesson lesson) {
        return LessonAddCommand.COMMAND_WORD + " " + index + " "
            + getLessonDetails(lesson);
    }

    /**
     * Returns a delete command string for deleting the {@code lesson}.
     */
    public static String getLessonDeleteCommand(int index, int indexToDelete) {
        return LessonDeleteCommand.COMMAND_WORD + " " + index + " " + indexToDelete;
    }

    /**
     * Returns an edit command string for editing the {@code lesson}.
     */
    public static String getLessonEditCommand(int index, int indexToEdit, Lesson lesson) {
        return LessonEditCommand.COMMAND_WORD + " " + index + " " + indexToEdit + " "
            + getLessonDetails(lesson);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getLessonDetails(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        if (lesson.isRecurring()) {
            sb.append(PREFIX_RECURRING + " ");
        }

        sb.append(PREFIX_DATE + lesson.getStartDate().toString() + " ");
        sb.append(PREFIX_TIME + lesson.getTimeRange().toString() + " ");
        sb.append(PREFIX_SUBJECT + lesson.getSubject().toString() + " ");
        sb.append(PREFIX_RATES + lesson.getLessonRates().value + " ");
        sb.append(PREFIX_OUTSTANDING_FEES + lesson.getOutstandingFees().value + " ");

        lesson.getHomework().stream().forEach(
            s -> sb.append(PREFIX_HOMEWORK + s.description + " ")
        );
        return sb.toString();
    }

}
