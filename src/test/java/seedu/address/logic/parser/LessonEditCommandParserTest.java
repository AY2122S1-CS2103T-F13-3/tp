package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.HOMEWORK_DESC_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.HOMEWORK_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOMEWORK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_POETRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_RANGE;
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
        // invalid subject
        assertParseFailure(parser, "1 1" + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        // invalid time range
        assertParseFailure(parser, "1 1" + INVALID_TIME_RANGE_DESC, TimeRange.MESSAGE_CONSTRAINTS);

        // invalid homework
        assertParseFailure(parser, "1 1" + INVALID_HOMEWORK_DESC, Homework.MESSAGE_CONSTRAINTS);

        // invalid time range followed by valid subject
        assertParseFailure(parser, "1 1" + INVALID_TIME_RANGE_DESC + SUBJECT_DESC,
                TimeRange.MESSAGE_CONSTRAINTS);

        /*
        // while parsing {@code PREFIX_HOMEWORK} alone will reset the Homework of the {@code Lesson} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1 1" + HOMEWORK_DESC_TEXTBOOK + HOMEWORK_DESC_POETRY + HOMEWORK_EMPTY,
                Homework.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 1" + HOMEWORK_DESC_TEXTBOOK + HOMEWORK_EMPTY + HOMEWORK_DESC_POETRY,
                Homework.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 1" + HOMEWORK_EMPTY + HOMEWORK_DESC_POETRY + HOMEWORK_DESC_TEXTBOOK,
                Homework.MESSAGE_CONSTRAINTS);

         */
        // multiple invalid values, but only the first invalid value checked is captured
        // order goes in time range, subject, homework
        assertParseFailure(parser, "1 1" + INVALID_HOMEWORK_DESC + INVALID_TIME_RANGE_DESC + VALID_SUBJECT,
            TimeRange.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        Index lessonTargetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased()
                + TIME_RANGE_DESC + SUBJECT_DESC + HOMEWORK_DESC_TEXTBOOK;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
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

        // time range
        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + TIME_RANGE_DESC;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withTimeRange(VALID_TIME_RANGE).build();
        LessonEditCommand expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
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

        // recurring flag
        userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased() + " " + PREFIX_RECURRING;
        descriptor = new EditLessonDescriptorBuilder().withRecurrence().build();
        expectedCommand = new LessonEditCommand(targetIndex, lessonTargetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Index lessonTargetIndex = INDEX_THIRD_LESSON;

        String userInput = targetIndex.getOneBased() + " " + lessonTargetIndex.getOneBased()
                + TIME_RANGE_DESC + TIME_RANGE_DESC + SUBJECT_DESC
                + HOMEWORK_DESC_TEXTBOOK + HOMEWORK_DESC_TEXTBOOK;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
            .withTimeRange(VALID_TIME_RANGE)
            .withSubject(VALID_SUBJECT)
            .withHomeworkSet(VALID_HOMEWORK_TEXTBOOK).build();

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