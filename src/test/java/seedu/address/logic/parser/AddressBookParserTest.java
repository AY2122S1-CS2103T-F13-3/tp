package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DayCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LessonAddCommand;
import seedu.address.logic.commands.LessonDeleteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MonthCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.TodayCommand;
import seedu.address.logic.commands.WeekCommand;
import seedu.address.logic.commands.YearCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonMatchesKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.LessonUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonMatchesKeywordsPredicateBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_ladd() throws Exception {
        Lesson lesson = new LessonBuilder().build();
        LessonAddCommand command = (LessonAddCommand) parser.parseCommand(
                LessonUtil.getLessonAddCommand(
                        INDEX_FIRST_PERSON.getOneBased(),
                        lesson));
        LessonAddCommand other = new LessonAddCommand(INDEX_FIRST_PERSON, lesson);
        assertEquals(other, command);
    }

    @Test
    public void parseCommand_ldelete() throws Exception {
        LessonDeleteCommand command = (LessonDeleteCommand) parser.parseCommand(
            LessonUtil.getLessonDeleteCommand(
                INDEX_FIRST_PERSON.getOneBased(),
                INDEX_FIRST_LESSON.getOneBased()));
        LessonDeleteCommand other = new LessonDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON);
        assertEquals(other, command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        PersonMatchesKeywordsPredicate predicate = new PersonMatchesKeywordsPredicateBuilder()
                .withName("Amy bob").withAddress("street").withAcadLevel("s3").withTags("friend", "paid")
                .withCondition(FindCommand.FindCondition.ANY).build();
        String details = PersonUtil.getPersonMatchesKeywordsPredicateDetails(predicate);
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + details);
        assertEquals(new FindCommand(predicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_calendar() throws Exception {
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD) instanceof CalendarCommand);
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD + " 3") instanceof CalendarCommand);
    }

    @Test
    public void parseCommand_day() throws Exception {
        assertTrue(parser.parseCommand(DayCommand.COMMAND_WORD) instanceof DayCommand);
        assertTrue(parser.parseCommand(DayCommand.COMMAND_WORD + " 3") instanceof DayCommand);
    }

    @Test
    public void parseCommand_week() throws Exception {
        assertTrue(parser.parseCommand(WeekCommand.COMMAND_WORD) instanceof WeekCommand);
        assertTrue(parser.parseCommand(WeekCommand.COMMAND_WORD + " 3") instanceof WeekCommand);
    }

    @Test
    public void parseCommand_month() throws Exception {
        assertTrue(parser.parseCommand(MonthCommand.COMMAND_WORD) instanceof MonthCommand);
        assertTrue(parser.parseCommand(MonthCommand.COMMAND_WORD + " 3") instanceof MonthCommand);
    }

    @Test
    public void parseCommand_year() throws Exception {
        assertTrue(parser.parseCommand(YearCommand.COMMAND_WORD) instanceof YearCommand);
        assertTrue(parser.parseCommand(YearCommand.COMMAND_WORD + " 3") instanceof YearCommand);
    }

    @Test
    public void parseCommand_next() throws Exception {
        assertTrue(parser.parseCommand(NextCommand.COMMAND_WORD) instanceof NextCommand);
        assertTrue(parser.parseCommand(NextCommand.COMMAND_WORD + " 3") instanceof NextCommand);
    }

    @Test
    public void parseCommand_back() throws Exception {
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD) instanceof BackCommand);
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD + " 3") instanceof BackCommand);
    }

    @Test
    public void parseCommand_today() throws Exception {
        assertTrue(parser.parseCommand(TodayCommand.COMMAND_WORD) instanceof TodayCommand);
        assertTrue(parser.parseCommand(TodayCommand.COMMAND_WORD + " 3") instanceof TodayCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () -> parser
                        .parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
