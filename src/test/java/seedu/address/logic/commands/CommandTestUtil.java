package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_STREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANCEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OUTSTANDING_FEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNCANCEL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MAKEUP_LESSON;
import static seedu.address.testutil.TypicalLessons.RECURRING_LESSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String EMPTY_FIELD = "";

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_PARENT_PHONE_AMY = "33333333";
    public static final String VALID_PARENT_PHONE_BOB = "44444444";
    public static final String VALID_PARENT_EMAIL_AMY = "amyparent@example.com";
    public static final String VALID_PARENT_EMAIL_BOB = "bobparent@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_SCHOOL_AMY = "Amys School";
    public static final String VALID_SCHOOL_BOB = "Bob's School";
    public static final String VALID_ACAD_STREAM_AMY = "Amy stream";
    public static final String VALID_ACAD_STREAM_BOB = "Bob stream";
    public static final String VALID_ACAD_LEVEL_AMY = "S1";
    public static final String VALID_ACAD_LEVEL_BOB = "P6";
    public static final String VALID_REMARK_AMY = "Amy loves sushi!";
    public static final String VALID_REMARK_BOB = "Bob loves sashimi!";
    public static final String VALID_TAG_FORGETFUL = "forgetful";
    public static final String VALID_TAG_UNPAID = "unpaid";
    public static final String VALID_TAG_ZOOM = "zoom";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String PARENT_PHONE_DESC_AMY = " " + PREFIX_PARENT_PHONE + VALID_PARENT_PHONE_AMY;
    public static final String PARENT_PHONE_DESC_BOB = " " + PREFIX_PARENT_PHONE + VALID_PARENT_PHONE_BOB;
    public static final String PARENT_EMAIL_DESC_AMY = " " + PREFIX_PARENT_EMAIL + VALID_PARENT_EMAIL_AMY;
    public static final String PARENT_EMAIL_DESC_BOB = " " + PREFIX_PARENT_EMAIL + VALID_PARENT_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String SCHOOL_DESC_AMY = " " + PREFIX_SCHOOL + VALID_SCHOOL_AMY;
    public static final String SCHOOL_DESC_BOB = " " + PREFIX_SCHOOL + VALID_SCHOOL_BOB;
    public static final String ACAD_STREAM_DESC_AMY = " " + PREFIX_ACAD_STREAM + VALID_ACAD_STREAM_AMY;
    public static final String ACAD_STREAM_DESC_BOB = " " + PREFIX_ACAD_STREAM + VALID_ACAD_STREAM_BOB;
    public static final String ACAD_LEVEL_DESC_AMY = " " + PREFIX_ACAD_LEVEL + VALID_ACAD_LEVEL_AMY;
    public static final String ACAD_LEVEL_DESC_BOB = " " + PREFIX_ACAD_LEVEL + VALID_ACAD_LEVEL_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String TAG_DESC_FORGETFUL = " " + PREFIX_TAG + VALID_TAG_FORGETFUL;
    public static final String TAG_DESC_ZOOM = " " + PREFIX_TAG + VALID_TAG_ZOOM;
    public static final String FIND_COND_DESC_ALL = " " + PREFIX_FIND_CONDITION + FindCommand.FindCondition.ALL;
    public static final String FIND_COND_DESC_ANY = " " + PREFIX_FIND_CONDITION + FindCommand.FindCondition.ANY;
    public static final String FIND_COND_DESC_NONE = " " + PREFIX_FIND_CONDITION + FindCommand.FindCondition.NONE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "8765432a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_PARENT_PHONE_DESC =
            " " + PREFIX_PARENT_PHONE + "8765 4321"; // ' ' spaces not allowed in phones
    public static final String INVALID_PARENT_EMAIL_DESC =
            " " + PREFIX_PARENT_EMAIL + "bobparent.yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_ACAD_LEVEL_DESC = " " + PREFIX_ACAD_LEVEL
            + "abcdefghijklmnopq"; // max 15 characters allowed for acad level
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_DATE_MON = "4 OCT 2021";
    public static final String VALID_DATE_TUE = "5 OCT 2021";
    public static final String VALID_DATE_NEXT_MON = "11 OCT 2021";
    public static final String VALID_DATE_PREV_MON = "27 SEP 2021";
    public static final String VALID_DATE_PAST = "15 May 1988";
    public static final String VALID_DATE_FUTURE = "7 Apr 2103";
    public static final String VALID_TIME_RANGE = "1234-1345";
    public static final String VALID_CLASHING_TIME_RANGE = "1300-1355";
    public static final String VALID_NON_CLASHING_TIME_RANGE = "1600-1855";
    public static final String VALID_LESSON_RATES = "50.50";
    public static final String VALID_SUBJECT = "Language Arts";
    public static final String VALID_OUTSTANDING_FEES = "100.00";
    public static final String VALID_OUTSTANDING_FEES_AFTER_PAYMENT = "80.00";
    public static final String VALID_HOMEWORK_TEXTBOOK = "Textbook pg 21-26";
    public static final String VALID_HOMEWORK_POETRY = "Poetry draft";
    public static final String VALID_PAYMENT = "20.00";

    public static final String PAST_DATE_DESC = " " + PREFIX_DATE + VALID_DATE_PAST;
    public static final String FUTURE_DATE_DESC = " " + PREFIX_DATE + VALID_DATE_FUTURE;
    public static final String TIME_RANGE_DESC = " " + PREFIX_TIME + VALID_TIME_RANGE;
    public static final String CLASHING_TIME_RANGE_DESC = " " + PREFIX_TIME + VALID_CLASHING_TIME_RANGE;
    public static final String NON_CLASHING_TIME_RANGE_DESC = " " + PREFIX_TIME + VALID_NON_CLASHING_TIME_RANGE;
    public static final String CANCEL_DATE_DESC_MON = " " + PREFIX_CANCEL + VALID_DATE_MON;
    public static final String CANCEL_DATE_DESC_NEXT_MON = " " + PREFIX_CANCEL + VALID_DATE_NEXT_MON;
    public static final String UNCANCEL_DATE_DESC_MON = " " + PREFIX_UNCANCEL + VALID_DATE_MON;
    public static final String UNCANCEL_DATE_DESC_NEXT_MON = " " + PREFIX_UNCANCEL + VALID_DATE_NEXT_MON;
    public static final String LESSON_RATES_DESC = " " + PREFIX_RATES + VALID_LESSON_RATES;
    public static final String OUTSTANDING_FEES_DESC = " " + PREFIX_OUTSTANDING_FEES + VALID_OUTSTANDING_FEES;
    public static final String SUBJECT_DESC = " " + PREFIX_SUBJECT + VALID_SUBJECT;
    public static final String HOMEWORK_DESC_TEXTBOOK = " " + PREFIX_HOMEWORK + VALID_HOMEWORK_TEXTBOOK;
    public static final String HOMEWORK_DESC_POETRY = " " + PREFIX_HOMEWORK + VALID_HOMEWORK_POETRY;
    public static final String PAYMENT_DESC = " " + PREFIX_PAID_AMOUNT + VALID_PAYMENT;

    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "32 Dec 2001"; // No such date
    public static final String INVALID_CANCEL_DATE_DESC = " " + PREFIX_CANCEL + "32 Dec 2001"; // No such date
    public static final String INVALID_UNCANCEL_DATE_DESC = " " + PREFIX_UNCANCEL + "32 Dec 2001"; // No such date
    public static final String INVALID_TIME_RANGE_DESC = " " + PREFIX_TIME + "0100-0300"; // Not between 0000h - 0800h
    public static final String INVALID_LESSON_RATES_DESC =
            " " + PREFIX_RATES + "$50.50"; //'$' not allowed in lesson rates
    public static final String INVALID_OUTSTANDING_FEES_DESC =
            " " + PREFIX_OUTSTANDING_FEES + "$100.00"; //'$' not allowed in outstanding fees
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "-An+"; // Not alphanumeric
    public static final String INVALID_HOMEWORK_DESC =
            " " + PREFIX_HOMEWORK + "Homework cannot be more than fifty characters long,"
            + " including whitespaces";
    public static final String INVALID_PAYMENT_DESC = " " + PREFIX_PAID_AMOUNT + "$20.00";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final LessonEditCommand.EditLessonDescriptor DESC_RECURRING;
    public static final LessonEditCommand.EditLessonDescriptor DESC_MAKEUP;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withParentPhone(VALID_PARENT_PHONE_AMY).withParentEmail(VALID_PARENT_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withSchool(VALID_SCHOOL_AMY)
                .withAcadStream(VALID_ACAD_STREAM_AMY)
                .withAcadLevel(VALID_ACAD_LEVEL_AMY)
                .withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_FORGETFUL).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withParentPhone(VALID_PARENT_PHONE_BOB).withParentEmail(VALID_PARENT_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withSchool(VALID_SCHOOL_BOB)
                .withAcadStream(VALID_ACAD_STREAM_BOB)
                .withAcadLevel(VALID_ACAD_LEVEL_BOB)
                .withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_ZOOM, VALID_TAG_FORGETFUL).build();

        DESC_RECURRING = new EditLessonDescriptorBuilder(RECURRING_LESSON).build();

        DESC_MAKEUP = new EditLessonDescriptorBuilder(MAKEUP_LESSON).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute());
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
