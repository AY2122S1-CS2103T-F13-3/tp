package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FUTURE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.HOMEWORK_DESC_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.HOMEWORK_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOMEWORK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_RATES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OUTSTANDING_FEES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_RATES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.OUTSTANDING_FEES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PAST_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_RATES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OUTSTANDING_FEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalLessons.PAST_MAKEUP_LESSON;
import static seedu.address.testutil.TypicalLessons.PAST_RECURRING_LESSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LessonAddCommand;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.OutstandingFees;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.testutil.LessonBuilder;

public class LessonAddCommandParserTest {

    private static final int FIRST_PERSON = INDEX_FIRST_PERSON.getOneBased();
    private static final String RECURRENCE_FLAG = " " + PREFIX_RECURRING;
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonAddCommand.MESSAGE_USAGE);
    private LessonAddCommandParser parser = new LessonAddCommandParser();

    @Test
    public void parse_allFieldsPresentMakeUpLesson_success() {
        Lesson expectedLesson = new LessonBuilder(PAST_MAKEUP_LESSON)
                .withHomeworkSet(VALID_HOMEWORK_POETRY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FIRST_PERSON
                + PAST_DATE_DESC + TIME_RANGE_DESC + LESSON_RATES_DESC
                + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple date - last date accepted
        assertParseSuccess(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC
                + PAST_DATE_DESC + TIME_RANGE_DESC + LESSON_RATES_DESC
                + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple subject - last subject accepted
        String testSubject = " " + PREFIX_SUBJECT + "Testing";
        assertParseSuccess(parser, " " + FIRST_PERSON + PAST_DATE_DESC
            + TIME_RANGE_DESC + testSubject + SUBJECT_DESC + LESSON_RATES_DESC + OUTSTANDING_FEES_DESC
            + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple time ranges - last time range accepted
        String testTimeRange = " " + PREFIX_TIME + "1234-1400";
        assertParseSuccess(parser, " " + FIRST_PERSON + PAST_DATE_DESC
                + testTimeRange + TIME_RANGE_DESC + LESSON_RATES_DESC
                + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        //multiple lesson rates - last lesson rate accepted
        String testLessonRates = " " + PREFIX_RATES + 60.00;
        assertParseSuccess(parser, " " + FIRST_PERSON + PAST_DATE_DESC
                + TIME_RANGE_DESC + testLessonRates + LESSON_RATES_DESC
                + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        //multiple outstanding fee - last outstanding fees accepted
        String testOutstandingFees = " " + PREFIX_OUTSTANDING_FEES + 99.99;
        assertParseSuccess(parser, " " + FIRST_PERSON + PAST_DATE_DESC
                + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC
                + testOutstandingFees + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple homework - all accepted
        Lesson expectedLessonMultipleHomework = new LessonBuilder(PAST_MAKEUP_LESSON)
                .withHomeworkSet(VALID_HOMEWORK_POETRY, VALID_HOMEWORK_TEXTBOOK).build();

        assertParseSuccess(parser, " " + FIRST_PERSON + PAST_DATE_DESC
                + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY + HOMEWORK_DESC_TEXTBOOK,
            new LessonAddCommand(INDEX_FIRST_PERSON, expectedLessonMultipleHomework));
    }

    @Test
    public void parse_allFieldsPresentRecurringLesson_success() {
        Lesson expectedLesson = new LessonBuilder(PAST_RECURRING_LESSON)
            .withHomeworkSet(VALID_HOMEWORK_POETRY).buildRecurring();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FIRST_PERSON + LESSON_RATES_DESC
            + RECURRENCE_FLAG + PAST_DATE_DESC + TIME_RANGE_DESC + SUBJECT_DESC + OUTSTANDING_FEES_DESC
            + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple date - last date accepted
        assertParseSuccess(parser, " " + FIRST_PERSON + RECURRENCE_FLAG
            + FUTURE_DATE_DESC + LESSON_RATES_DESC + OUTSTANDING_FEES_DESC
            + PAST_DATE_DESC + TIME_RANGE_DESC + SUBJECT_DESC
            + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple subject - last subject accepted
        String testSubject = " " + PREFIX_SUBJECT + "Testing";
        assertParseSuccess(parser, " " + FIRST_PERSON + RECURRENCE_FLAG + PAST_DATE_DESC
            + TIME_RANGE_DESC + testSubject + SUBJECT_DESC + LESSON_RATES_DESC + OUTSTANDING_FEES_DESC
            + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple time ranges - last time range accepted
        assertParseSuccess(parser, " " + FIRST_PERSON + RECURRENCE_FLAG + PAST_DATE_DESC
            + TIME_RANGE_DESC + TIME_RANGE_DESC + SUBJECT_DESC + LESSON_RATES_DESC + OUTSTANDING_FEES_DESC
            + HOMEWORK_DESC_POETRY, new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        // multiple homework - all accepted
        Lesson expectedLessonMultipleHomework = new LessonBuilder(PAST_RECURRING_LESSON)
            .withHomeworkSet(VALID_HOMEWORK_POETRY, VALID_HOMEWORK_TEXTBOOK).buildRecurring();

        assertParseSuccess(parser, " " + FIRST_PERSON + RECURRENCE_FLAG + PAST_DATE_DESC
                + TIME_RANGE_DESC + SUBJECT_DESC + LESSON_RATES_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY + HOMEWORK_DESC_TEXTBOOK,
            new LessonAddCommand(INDEX_FIRST_PERSON, expectedLessonMultipleHomework));

        // multiple lesson rates - last rate accepted
        String testLessonRates = " " + PREFIX_RATES + 60.00;
        assertParseSuccess(parser, " " + FIRST_PERSON + RECURRENCE_FLAG + PAST_DATE_DESC
                + TIME_RANGE_DESC + SUBJECT_DESC + testLessonRates + LESSON_RATES_DESC
                + OUTSTANDING_FEES_DESC + HOMEWORK_DESC_POETRY,
                new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));

        //multiple outstanding fee - last outstanding fees accepted
        String testOutstandingFees = " " + PREFIX_OUTSTANDING_FEES + 99.99;
        assertParseSuccess(parser, " " + FIRST_PERSON + RECURRENCE_FLAG + PAST_DATE_DESC
                + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC + testOutstandingFees
                + OUTSTANDING_FEES_DESC + HOMEWORK_DESC_POETRY,
                new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // No homework
        Lesson expectedLesson = new LessonBuilder(PAST_MAKEUP_LESSON)
                .withHomeworkSet().withOutstandingFees("0.00").build();

        assertParseSuccess(parser, " " + FIRST_PERSON + PAST_DATE_DESC
                + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC,
                new LessonAddCommand(INDEX_FIRST_PERSON, expectedLesson));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LessonAddCommand.MESSAGE_USAGE);

        // missing date
        assertParseFailure(parser, " " + FIRST_PERSON + TIME_RANGE_DESC + SUBJECT_DESC
                + LESSON_RATES_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing time range
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + SUBJECT_DESC
                + LESSON_RATES_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing subject
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + LESSON_RATES_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing lesson rates
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + SUBJECT_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing all fields
        assertParseFailure(parser, " " + FIRST_PERSON, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, " " + FIRST_PERSON + VALID_DATE_FUTURE + TIME_RANGE_DESC
                + SUBJECT_DESC + LESSON_RATES_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing time range prefix
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + VALID_TIME_RANGE
                + SUBJECT_DESC + LESSON_RATES_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + VALID_SUBJECT + LESSON_RATES_DESC + HOMEWORK_DESC_POETRY, expectedMessage);

        // missing lesson rates prefix
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + SUBJECT_DESC + VALID_LESSON_RATES + HOMEWORK_DESC_POETRY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " " + FIRST_PERSON + VALID_DATE_FUTURE + VALID_TIME_RANGE
                + VALID_LESSON_RATES + VALID_SUBJECT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index or preamble
        assertParseFailure(parser, " " + 0 + VALID_DATE_PAST
            + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC
            + OUTSTANDING_FEES_DESC + HOMEWORK_DESC_POETRY, MESSAGE_INVALID_FORMAT);

        // invalid date
        assertParseFailure(parser, " " + FIRST_PERSON + INVALID_DATE_DESC
                + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC
                + OUTSTANDING_FEES_DESC + HOMEWORK_DESC_POETRY, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, " " + FIRST_PERSON + " " + PREFIX_DATE
            + TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC
            + OUTSTANDING_FEES_DESC + HOMEWORK_DESC_POETRY, Date.MESSAGE_CONSTRAINTS);

        // invalid time range
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC
                + INVALID_TIME_RANGE_DESC + LESSON_RATES_DESC + SUBJECT_DESC
                + OUTSTANDING_FEES_DESC + HOMEWORK_DESC_POETRY, TimeRange.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + LESSON_RATES_DESC + INVALID_SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, Subject.MESSAGE_CONSTRAINTS);

        // invalid lesson rates
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + INVALID_LESSON_RATES_DESC + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, LessonRates.MESSAGE_CONSTRAINTS);

        // invalid outstanding fees
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + LESSON_RATES_DESC + SUBJECT_DESC + INVALID_OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, OutstandingFees.MESSAGE_CONSTRAINTS);

        // invalid homework
        assertParseFailure(parser, " " + FIRST_PERSON + FUTURE_DATE_DESC + TIME_RANGE_DESC
                + LESSON_RATES_DESC + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + INVALID_HOMEWORK_DESC, Homework.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + FIRST_PERSON + INVALID_DATE_DESC + TIME_RANGE_DESC
                + LESSON_RATES_DESC + INVALID_SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY, Date.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + FIRST_PERSON
                + FUTURE_DATE_DESC + TIME_RANGE_DESC + SUBJECT_DESC + OUTSTANDING_FEES_DESC
                + HOMEWORK_DESC_POETRY + HOMEWORK_DESC_TEXTBOOK,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonAddCommand.MESSAGE_USAGE));
    }
}
