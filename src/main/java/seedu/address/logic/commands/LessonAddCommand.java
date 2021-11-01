package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CommandUtil;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonUtil;

/**
 * Adds a lesson to a person in the address book.
 */
public class LessonAddCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Add Lesson";

    public static final String COMMAND_WORD = "ladd";

    public static final String COMMAND_PARAMETERS = "INDEX "
            + "[" + PREFIX_RECURRING + "[END_DATE]] "
            + PREFIX_DATE + "dd MMM yyyy "
            + PREFIX_TIME + "HHmm-HHmm "
            + PREFIX_RATES + "RATES "
            + PREFIX_SUBJECT + "SUBJECT "
            + "[" + PREFIX_HOMEWORK + "HOMEWORK]...";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String COMMAND_EXAMPLE_RECURRING_LESSON = COMMAND_WORD + " 1 "
            + PREFIX_RECURRING + " "
            + PREFIX_DATE + "09 Dec 2021 "
            + PREFIX_TIME + "1030-1230 "
            + PREFIX_RATES + "37.50 "
            + PREFIX_SUBJECT + "Math "
            + PREFIX_HOMEWORK + "TYS Page 2";

    public static final String COMMAND_EXAMPLE_MAKEUP_LESSON = COMMAND_WORD + " 1 "
            + PREFIX_DATE + "10 Oct 2021 "
            + PREFIX_TIME + "1430-1600 "
            + PREFIX_RATES + "37.50 "
            + PREFIX_SUBJECT + "Science "
            + PREFIX_HOMEWORK + "TYS Page 2 "
            + PREFIX_HOMEWORK + "Textbook Page 52";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the student identified "
            + "by the index number\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Examples:\n"
            + "Makeup lesson: " + COMMAND_EXAMPLE_MAKEUP_LESSON + "\n"
            + "Recurring lesson: " + COMMAND_EXAMPLE_RECURRING_LESSON;

    public static final String USER_TIP = "Try adding a lesson to a student using: \n"
            + COMMAND_WORD + " "
            + COMMAND_PARAMETERS + "\n"
            + "Example (recurring lesson): "
            + COMMAND_EXAMPLE_RECURRING_LESSON;

    public static final String MESSAGE_ADD_LESSON_SUCCESS = "Added new lesson for student %1$s:\n%2$s";
    public static final String MESSAGE_CLASHING_LESSON = "This lesson clashes with an existing lesson.";
    public static final String MESSAGE_INVALID_DATE_RANGE = "The end date cannot be earlier than the start date. Please"
            + " specify a valid date range.\n"
            + "You can specify the start date with " + PREFIX_DATE + "DATE and the end date with "
            + PREFIX_RECURRING + "END_DATE";

    private final Index index;
    private final Lesson toAdd;
    private Person personBeforeLessonAdd;
    private Person personAfterLessonAdd;

    /**
     * Creates a LessonAddCommand to add the specified {@code Lesson}
     */
    public LessonAddCommand(Index index, Lesson lesson) {
        requireNonNull(lesson);
        this.index = index;
        toAdd = lesson;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        personBeforeLessonAdd = CommandUtil.getPerson(lastShownList, index);
        Set<Lesson> lessons = personBeforeLessonAdd.getLessons();
        Set<Lesson> updatedLessons = createUpdatedLessons(lessons, toAdd);
        personAfterLessonAdd = PersonUtil.createdEditedPerson(personBeforeLessonAdd, updatedLessons);

        model.setPerson(personBeforeLessonAdd, personAfterLessonAdd);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_LESSON_SUCCESS, personAfterLessonAdd.getName(), toAdd),
                personAfterLessonAdd);
    }

    /**
     * Adds specified {@code Lesson} to the lessons for this person.
     *
     * @param lessons A list of lessons to update.
     * @param toAdd The lesson to add.
     * @return A set of updated lessons with the lesson added.
     * @throws CommandException If the added lesson results in a clash.
     */
    private Set<Lesson> createUpdatedLessons(Set<Lesson> lessons, Lesson toAdd)
            throws CommandException {
        if (model.hasClashingLesson(toAdd)) {
            Set<String> clashes = model.getClashingLessonsString(toAdd);
            String clashingLessons = CommandUtil.lessonsToString(clashes);
            throw new CommandException(MESSAGE_CLASHING_LESSON + clashingLessons);
        }

        // Check date ranges
        if (toAdd.getStartDate().isAfter(toAdd.getEndDate())) {
            throw new CommandException(MESSAGE_INVALID_DATE_RANGE);
        }

        Set<Lesson> updatedLessons = new TreeSet<>(lessons);
        updatedLessons.add(toAdd);
        return updatedLessons;
    }

    @Override
    protected void undo() {
        requireNonNull(model);

        model.setPerson(personAfterLessonAdd, personBeforeLessonAdd);
    }

    @Override
    protected void redo() {
        requireNonNull(model);

        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError(MESSAGE_REDO_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonAddCommand // instanceof handles nulls
                && index.equals(((LessonAddCommand) other).index)
                && toAdd.equals(((LessonAddCommand) other).toAdd));
    }
}
