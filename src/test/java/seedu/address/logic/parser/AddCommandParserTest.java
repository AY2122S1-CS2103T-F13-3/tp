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
import static seedu.address.logic.commands.CommandTestUtil.FEE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FEE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ACAD_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FORGETFUL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ZOOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.AcadLevel;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FORGETFUL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + FEE_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + FEE_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + FEE_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + FEE_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + FEE_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple school - last school accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_AMY + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB
                + FEE_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple acad streams - last acad stream accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_AMY + ACAD_STREAM_DESC_BOB
                + ACAD_LEVEL_DESC_BOB + FEE_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple acad levels - last acad level accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB
                + ACAD_LEVEL_DESC_AMY + ACAD_LEVEL_DESC_BOB + FEE_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple fee - last fee accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB
                + FEE_DESC_AMY + FEE_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB + FEE_DESC_BOB
                + REMARK_DESC_AMY + REMARK_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FORGETFUL, VALID_TAG_ZOOM)
                .build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + PARENT_PHONE_DESC_BOB + PARENT_EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB + FEE_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags and no remarks
        Person expectedPerson = new PersonBuilder(AMY).withEmail("")
                .withParentPhone().withParentEmail().withSchool().withAcadStream().withAcadLevel()
                .withFee().withRemark().withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        // no school and no acad stream
        Person expectedPerson2 = new PersonBuilder(AMY).withSchool().withAcadStream().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + PARENT_PHONE_DESC_AMY + PARENT_EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + ACAD_LEVEL_DESC_AMY + FEE_DESC_AMY
                        + REMARK_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson2));

        // no acad level
        Person expectedPerson3 = new PersonBuilder(AMY).withAcadLevel().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + PARENT_PHONE_DESC_AMY + PARENT_EMAIL_DESC_AMY
                        + SCHOOL_DESC_AMY + ACAD_STREAM_DESC_AMY
                        + FEE_DESC_AMY + REMARK_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson3));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing all contact prefixes
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + ACAD_LEVEL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid acad level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + INVALID_ACAD_LEVEL_DESC + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                AcadLevel.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FORGETFUL, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ACAD_STREAM_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
