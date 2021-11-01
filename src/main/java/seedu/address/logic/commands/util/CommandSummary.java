package seedu.address.logic.commands.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DayCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LessonAddCommand;
import seedu.address.logic.commands.LessonDeleteCommand;
import seedu.address.logic.commands.LessonEditCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MonthCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.PaidCommand;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TodayCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.WeekCommand;
import seedu.address.logic.commands.YearCommand;

/**
 * Wrapper class for command usage summary with the respective command action, format and example.
 */
public class CommandSummary {
    /** Description of what the command does. */
    private StringProperty action;

    /** Format of a valid command. */
    private StringProperty format;

    /** Example of a valid command. */
    private StringProperty example;

    /**
     * Constructs a CommandSummary object.
     *
     * @param action Description of what the command does.
     * @param format Format of a valid command.
     * @param example Example of a valid command.
     */
    public CommandSummary(String action, String format, String example) {
        this.action = new SimpleStringProperty(action);
        this.format = new SimpleStringProperty(format);
        this.example = new SimpleStringProperty(example);
    }

    /**
     * Returns the description of what the command does.
     *
     * @return Description of what the command does.
     */
    public String getAction() {
        return action.get();
    }

    /**
     * Returns the format of a valid command.
     *
     * @return Format of a valid command.
     */
    public String getFormat() {
        return format.get();
    }

    /**
     * Returns the example of a valid command.
     *
     * @return Example of a valid command.
     */
    public String getExample() {
        return example.get();
    }

    /**
     * Returns the StringProperty for action.
     * Note: Naming convention needs to start with the name of the property as described in
     * https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
     *
     * @return StringProperty for action.
     */
    public StringProperty actionProperty() {
        return action;
    }

    /**
     * Returns the StringProperty for format.
     * Note: Naming convention needs to start with the name of the property as described in
     * https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
     *
     * @return StringProperty for format.
     */
    public StringProperty formatProperty() {
        return format;
    }

    /**
     * Returns the StringProperty for example.
     * Note: Naming convention needs to start with the name of the property as described in
     * https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
     *
     * @return StringProperty for example.
     */
    public StringProperty exampleProperty() {
        return example;
    }

    /**
     * Creates and returns an observable list of command usage with action, format and example.
     *
     * @return An observable list of command usage.
     */
    public static ObservableList<CommandSummary> getCommandSummaryList() {
        return FXCollections.observableArrayList(
                new CommandSummary(HelpCommand.COMMAND_ACTION, HelpCommand.COMMAND_WORD,
                        HelpCommand.COMMAND_WORD),
                new CommandSummary(AddCommand.COMMAND_ACTION, AddCommand.COMMAND_FORMAT,
                        AddCommand.COMMAND_EXAMPLE),
                new CommandSummary(EditCommand.COMMAND_ACTION, EditCommand.COMMAND_FORMAT,
                        EditCommand.COMMAND_EXAMPLE),
                new CommandSummary(DeleteCommand.COMMAND_ACTION, DeleteCommand.COMMAND_FORMAT,
                        DeleteCommand.COMMAND_EXAMPLE),
                new CommandSummary(FindCommand.COMMAND_ACTION, FindCommand.COMMAND_FORMAT,
                        FindCommand.COMMAND_EXAMPLE),
                new CommandSummary(ListCommand.COMMAND_ACTION, ListCommand.COMMAND_WORD,
                        ListCommand.COMMAND_WORD),
                new CommandSummary(LessonAddCommand.COMMAND_ACTION, LessonAddCommand.COMMAND_FORMAT,
                        LessonAddCommand.COMMAND_EXAMPLE_RECURRING_LESSON),
                new CommandSummary(LessonEditCommand.COMMAND_ACTION, LessonEditCommand.COMMAND_FORMAT,
                        LessonEditCommand.COMMAND_EXAMPLE),
                new CommandSummary(PaidCommand.COMMAND_ACTION, PaidCommand.COMMAND_FORMAT,
                        PaidCommand.COMMAND_EXAMPLE),
                new CommandSummary(LessonDeleteCommand.COMMAND_ACTION, LessonDeleteCommand.COMMAND_FORMAT,
                        LessonDeleteCommand.COMMAND_EXAMPLE),
                new CommandSummary(ViewCommand.COMMAND_ACTION, ViewCommand.COMMAND_FORMAT,
                        ViewCommand.COMMAND_EXAMPLE),
                new CommandSummary(RemindCommand.COMMAND_ACTION, RemindCommand.COMMAND_WORD,
                        RemindCommand.COMMAND_WORD),
                new CommandSummary(CalendarCommand.COMMAND_ACTION, CalendarCommand.COMMAND_WORD,
                        CalendarCommand.COMMAND_WORD),
                new CommandSummary(DayCommand.COMMAND_ACTION, DayCommand.COMMAND_WORD,
                        DayCommand.COMMAND_WORD),
                new CommandSummary(WeekCommand.COMMAND_ACTION, WeekCommand.COMMAND_WORD,
                        WeekCommand.COMMAND_WORD),
                new CommandSummary(MonthCommand.COMMAND_ACTION, MonthCommand.COMMAND_WORD,
                        MonthCommand.COMMAND_WORD),
                new CommandSummary(YearCommand.COMMAND_ACTION, YearCommand.COMMAND_WORD,
                        YearCommand.COMMAND_WORD),
                new CommandSummary(NextCommand.COMMAND_ACTION, NextCommand.COMMAND_WORD,
                        NextCommand.COMMAND_WORD),
                new CommandSummary(BackCommand.COMMAND_ACTION, BackCommand.COMMAND_WORD,
                        BackCommand.COMMAND_WORD),
                new CommandSummary(TodayCommand.COMMAND_ACTION, TodayCommand.COMMAND_WORD,
                        TodayCommand.COMMAND_WORD),
                new CommandSummary(ClearCommand.COMMAND_ACTION, ClearCommand.COMMAND_WORD,
                        ClearCommand.COMMAND_WORD),
                new CommandSummary(TagCommand.COMMAND_ACTION, TagCommand.COMMAND_WORD, TagCommand.COMMAND_WORD),
                new CommandSummary(ExitCommand.COMMAND_ACTION, ExitCommand.COMMAND_WORD,
                        ExitCommand.COMMAND_WORD));
    }
}
