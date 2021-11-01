package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ACAD_LEVEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ACAD_LEVEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ACAD_STREAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ACAD_STREAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FIND_COND_DESC_ALL;
import static seedu.address.logic.commands.CommandTestUtil.FIND_COND_DESC_ANY;
import static seedu.address.logic.commands.CommandTestUtil.FIND_COND_DESC_NONE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_STREAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_STREAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FORGETFUL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ZOOM;
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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindCondition;
import seedu.address.model.person.PersonMatchesKeywordsPredicate;
import seedu.address.testutil.PersonMatchesKeywordsPredicateBuilder;

public class FindCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no fields specified
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // has condition but no fields to find
        assertParseFailure(parser, " cond/all", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " 1 some random string" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " 1 i/ string" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidKeyword_failure() {
        // invalid empty name keyword
        assertParseFailure(parser, " " + PREFIX_NAME + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty phone keyword
        assertParseFailure(parser, " " + PREFIX_PHONE + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty email keyword
        assertParseFailure(parser, " " + PREFIX_EMAIL + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty parent phone keyword
        assertParseFailure(parser, " " + PREFIX_PARENT_PHONE + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty parent email keyword
        assertParseFailure(parser, " " + PREFIX_PARENT_EMAIL + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty address keyword
        assertParseFailure(parser, " " + PREFIX_ADDRESS + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty school keyword
        assertParseFailure(parser, " " + PREFIX_SCHOOL + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty academic stream keyword
        assertParseFailure(parser, " " + PREFIX_ACAD_STREAM + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty academic level keyword
        assertParseFailure(parser, " " + PREFIX_ACAD_LEVEL + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid empty tag keyword
        assertParseFailure(parser, " " + PREFIX_TAG + " ", FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);
        // invalid multiple words tag keyword
        assertParseFailure(parser, " " + PREFIX_TAG + "math paid", FindCommand.MESSAGE_TAG_KEYWORD_CONSTRAINTS);

        // invalid tag keyword followed by valid tag
        assertParseFailure(parser, " " + PREFIX_TAG + " " + PREFIX_TAG + "math ",
                FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);

        // valid tag keyword followed by invalid condition
        assertParseFailure(parser, " " + PREFIX_TAG + "math " + PREFIX_FIND_CONDITION + "invalid",
                FindCommand.MESSAGE_CONDITION_CONSTRAINTS);

        // invalid condition followed by invalid tag keyword
        // invalid keyword takes precedence
        assertParseFailure(parser, " " + PREFIX_FIND_CONDITION + "invalid " + PREFIX_TAG + " ",
                FindCommand.MESSAGE_KEYWORD_CONSTRAINTS);

        // invalid condition followed with no fields
        // Missing fields takes precedence
        assertParseFailure(parser, " " + PREFIX_FIND_CONDITION + "invalid ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB + TAG_DESC_HUSBAND + PARENT_EMAIL_DESC_AMY + PARENT_PHONE_DESC_AMY
                + EMAIL_DESC_AMY + SCHOOL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND
                + ACAD_LEVEL_DESC_AMY + ACAD_STREAM_DESC_AMY + FIND_COND_DESC_ALL;

        PersonMatchesKeywordsPredicate
                predicate = new PersonMatchesKeywordsPredicateBuilder().withCondition(FindCondition.ALL)
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withSchool(VALID_SCHOOL_AMY)
                .withAcadStream(VALID_ACAD_STREAM_AMY)
                .withAcadLevel(VALID_ACAD_LEVEL_AMY)
                .withParentPhone(VALID_PARENT_PHONE_AMY).withParentEmail(VALID_PARENT_EMAIL_AMY)
                .withTags(VALID_TAG_ZOOM, VALID_TAG_FORGETFUL).build();

        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB + NAME_DESC_AMY + TAG_DESC_FRIEND;

        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicateBuilder()
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_FORGETFUL).build();

        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_condition_success() {
        // Match all
        String userInput = FIND_COND_DESC_ALL + NAME_DESC_AMY + TAG_DESC_FRIEND;
        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicateBuilder()
                .withCondition(FindCondition.ALL).withName(VALID_NAME_AMY).withTags(VALID_TAG_FORGETFUL).build();
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Match any
        userInput = FIND_COND_DESC_ANY + NAME_DESC_AMY + TAG_DESC_FRIEND;
        predicate = new PersonMatchesKeywordsPredicateBuilder().withCondition(FindCondition.ANY)
                .withName(VALID_NAME_AMY).withTags(VALID_TAG_FORGETFUL).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Match none
        userInput = FIND_COND_DESC_NONE + NAME_DESC_AMY + TAG_DESC_FRIEND;
        predicate = new PersonMatchesKeywordsPredicateBuilder().withCondition(FindCondition.NONE)
                .withName(VALID_NAME_AMY).withTags(VALID_TAG_FORGETFUL).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new PersonMatchesKeywordsPredicateBuilder()
                .withName("Alice Bob").build());
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicateBuilder()
                .withName(VALID_NAME_AMY).build();
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_AMY, expectedCommand);

        // phone
        predicate = new PersonMatchesKeywordsPredicateBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, PHONE_DESC_AMY, expectedCommand);

        // email
        predicate = new PersonMatchesKeywordsPredicateBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, EMAIL_DESC_AMY, expectedCommand);

        // parent phone
        predicate = new PersonMatchesKeywordsPredicateBuilder().withParentPhone(VALID_PARENT_PHONE_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, PARENT_PHONE_DESC_AMY, expectedCommand);

        // parent email
        predicate = new PersonMatchesKeywordsPredicateBuilder().withParentEmail(VALID_PARENT_EMAIL_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, PARENT_EMAIL_DESC_AMY, expectedCommand);

        // address
        predicate = new PersonMatchesKeywordsPredicateBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, ADDRESS_DESC_AMY, expectedCommand);

        // school
        predicate = new PersonMatchesKeywordsPredicateBuilder().withSchool(VALID_SCHOOL_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, SCHOOL_DESC_AMY, expectedCommand);

        // academic stream
        predicate = new PersonMatchesKeywordsPredicateBuilder().withAcadStream(VALID_ACAD_STREAM_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, ACAD_STREAM_DESC_AMY, expectedCommand);

        // academic level
        predicate = new PersonMatchesKeywordsPredicateBuilder().withAcadLevel(VALID_ACAD_LEVEL_AMY).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, ACAD_LEVEL_DESC_AMY, expectedCommand);

        // tags
        predicate = new PersonMatchesKeywordsPredicateBuilder().withTags(VALID_TAG_FORGETFUL).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, TAG_DESC_FRIEND, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = PHONE_DESC_AMY + SCHOOL_DESC_AMY + ADDRESS_DESC_AMY + ACAD_STREAM_DESC_AMY + EMAIL_DESC_AMY
                + ACAD_LEVEL_DESC_BOB + FIND_COND_DESC_NONE + PARENT_EMAIL_DESC_BOB + PARENT_PHONE_DESC_BOB
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + SCHOOL_DESC_AMY + PARENT_EMAIL_DESC_AMY + PARENT_PHONE_DESC_AMY
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ACAD_LEVEL_DESC_AMY + EMAIL_DESC_BOB
                + ACAD_STREAM_DESC_BOB + FIND_COND_DESC_ANY + TAG_DESC_HUSBAND;

        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicateBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withParentEmail(VALID_PARENT_EMAIL_AMY).withParentPhone(VALID_PARENT_PHONE_AMY)
                .withAcadLevel(VALID_ACAD_LEVEL_AMY).withAcadStream(VALID_ACAD_STREAM_BOB)
                .withSchool(VALID_SCHOOL_BOB).withTags(VALID_TAG_FORGETFUL, VALID_TAG_FORGETFUL, VALID_TAG_ZOOM)
                .withCondition(FindCondition.ANY).build();
        FindCommand expectedCommand = new FindCommand(predicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_PHONE_DESC + PHONE_DESC_BOB;
        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicateBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB + PHONE_DESC_BOB;
        predicate = new PersonMatchesKeywordsPredicateBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
