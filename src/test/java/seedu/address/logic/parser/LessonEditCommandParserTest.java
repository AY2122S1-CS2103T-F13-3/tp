package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CANCEL_DATE_DESC_MON;
import static seedu.address.logic.commands.CommandTestUtil.CANCEL_DATE_DESC_NEXT_MON;
import static seedu.address.logic.commands.CommandTestUtil.FUTURE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.HOMEWORK_DESC_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.HOMEWORK_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CANCEL_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOMEWORK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNCANCEL_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_RATES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.OUTSTANDING_FEES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PAST_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.UNCANCEL_DATE_DESC_MON;
import static seedu.address.logic.commands.CommandTestUtil.UNCANCEL_DATE_DESC_NEXT_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_NEXT_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_RATES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OUTSTANDING_FEES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LessonEditCommand;
import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.testutil.EditLessonDescriptorBuilder;

class LessonEditCommandParserTest {

    private static final String HOMEWORK_EMPTY = " " + PREFIX_HOMEWORK;
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE);
    private final LessonEditCommandParser parser = new LessonEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TIME_RANGE_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 1", LessonEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 1 " + PREFIX_DATE, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative student index
        assertParseFailure(parser, "-5 1" + SUBJECT_DESC, MESSAGE_INVALID_FORMAT);

        // zero student index
        assertParseFailure(parser, "0 1" + SUBJECT_DESC, MESSAGE_INVALID_FORMAT);

        // negative lesson index
        assertParseFailure(parser, "1 -7" + SUBJECT_DESC, MESSAGE_INVALID_FORMAT);

        // zero lesson index
        assertParseFailure(parser, "1 0" + SUBJECT_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, "1 1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // invalid cancelled date
        assertParseFailure(parser, "1 1" + INVALID_CANCEL_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // invalid uncancelled date
        assertParseFailure(parser, "1 1" + INVALID_UNCANCEL_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, "1 1" + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        // invalid time range
        assertParseFailure(parser, "1 1" + INVALID_TIME_RANGE_DESC, TimeRange.MESSAGE_CONSTRAINTS);

        // invalid homework
        assertParseFailure(parser, "1 1" + INVALID_HOMEWORK_DESC, Homework.MESSAGE_CONSTRAINTS);

        // invalid time range followed by valid subject
        assertParseFailure(parser, "1 1" + INVALID_TIME_RANGE_DESC + SUBJECT_DESC,
                TimeRange.MESSAGE_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value checked is captured
        // order goes in time range, subject, homework
        assertParseFailure(parser, "1 1" + INVALID_HOMEWORK_DESC + INVALID_TIME_RANGE_DESC + VALID_SUBJECT,
            TimeRange.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        Index lessonTargetIndex = INDEX_FIRST_LESSON;
        String endDate = " " + PREFIX_RECURRING + VALID_DATE_FUTURE;
        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased()
            + endDate + HOMEWORK_DESC_TEXTBOOK + SUBJECT_DESC + TIME_RANGE_DESC + FUTURE_DATE_DESC;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withRecurrence()
                .withEndDate(VALID_DATE_FUTURE)
                .withDate(VALID_DATE_FUTURE)
                .withTimeRange(VALID_TIME_RANGE)
                .withSubject(VALID_SUBJECT)
                .withHomeworkSet(VALID_HOMEWORK_TEXTBOOK).build();

        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Index lessonTargetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased()
            + HOMEWORK_DESC_TEXTBOOK + SUBJECT_DESC;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
            .withHomeworkSet(VALID_HOMEWORK_TEXTBOOK)
            .withSubject(VALID_SUBJECT).build();

        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        Index lessonTargetIndex = INDEX_SECOND_LESSON;

        // date
        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + PAST_DATE_DESC;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withDate(VALID_DATE_PAST).build();
        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        //assertParseSuccess(parser, userInput, expectedCommand);

        // time range
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + TIME_RANGE_DESC;
        descriptor = new EditLessonDescriptorBuilder().withTimeRange(VALID_TIME_RANGE).build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // subject
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + SUBJECT_DESC;
        descriptor = new EditLessonDescriptorBuilder().withSubject(VALID_SUBJECT).build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // homework list
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + HOMEWORK_DESC_POETRY;
        descriptor = new EditLessonDescriptorBuilder().withHomeworkSet(VALID_HOMEWORK_POETRY).build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // lesson rates
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + LESSON_RATES_DESC;
        descriptor = new EditLessonDescriptorBuilder().withLessonRates(VALID_LESSON_RATES).build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // outstanding fees
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + OUTSTANDING_FEES_DESC;
        descriptor = new EditLessonDescriptorBuilder().withOutstandingFees(VALID_OUTSTANDING_FEES).build();

        // cancel dates list
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + CANCEL_DATE_DESC_MON;
        descriptor = new EditLessonDescriptorBuilder().withCancelDates(VALID_DATE_MON).build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // uncancel dates list
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + UNCANCEL_DATE_DESC_MON;
        descriptor = new EditLessonDescriptorBuilder().withUncancelDates(VALID_DATE_MON).build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Index lessonTargetIndex = INDEX_THIRD_LESSON;

        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased()
                + FUTURE_DATE_DESC + PAST_DATE_DESC + TIME_RANGE_DESC + TIME_RANGE_DESC + SUBJECT_DESC
                + HOMEWORK_DESC_TEXTBOOK + HOMEWORK_DESC_TEXTBOOK;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withDate(VALID_DATE_PAST)
                .withTimeRange(VALID_TIME_RANGE)
                .withSubject(VALID_SUBJECT)
                .withHomeworkSet(VALID_HOMEWORK_TEXTBOOK).build();

        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleCancelUncancelFields_acceptsAll() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Index lessonTargetIndex = INDEX_THIRD_LESSON;

        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased()
                + CANCEL_DATE_DESC_MON + CANCEL_DATE_DESC_NEXT_MON
                + UNCANCEL_DATE_DESC_NEXT_MON + UNCANCEL_DATE_DESC_MON;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withCancelDates(VALID_DATE_MON, VALID_DATE_NEXT_MON)
                .withUncancelDates(VALID_DATE_NEXT_MON, VALID_DATE_MON).build();

        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetHomeworkSet_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        Index lessonTargetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + HOMEWORK_EMPTY;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
            .withHomeworkSet().build();
        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
