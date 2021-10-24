package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonUtil;


/**
 * Contains integration tests (interaction with the Model) and unit tests for LessonDeleteCommand.
 */

public class LessonDeleteCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Delete Lesson";

    public static final String COMMAND_WORD = "ldelete";

    public static final String COMMAND_PARAMETERS = "INDEX (must be a positive integer) "
            + "LESSON_INDEX (must be a positive integer)";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " 1 " + "1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the lesson identified by lesson index"
            + " of the student identified by the index number used in the displayed student list.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson for student %1$s:\n%2$s";

    private final Index index;
    private final Index lessonIndex;
    private Person personBeforeLessonDelete;
    private Person personAfterLessonDelete;

    /**
     * @param index of the person in the filtered person list to delete lesson from
     */
    public LessonDeleteCommand(Index index, Index lessonIndex) {
        requireNonNull(index);
        requireNonNull(lessonIndex);
        this.index = index;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        personBeforeLessonDelete = lastShownList.get(index.getZeroBased());

        Set<Lesson> lessons = new TreeSet<>(personBeforeLessonDelete.getLessons());
        if (lessonIndex.getZeroBased() >= lessons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        List<Lesson> lessonList = lessons.stream().collect(Collectors.toList());
        Lesson toRemove = lessonList.get(lessonIndex.getZeroBased());

        personAfterLessonDelete = createEditedPerson(personBeforeLessonDelete, lessonList, toRemove);

        model.setPerson(personBeforeLessonDelete, personAfterLessonDelete);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS,
                personAfterLessonDelete.getName(), toRemove), personAfterLessonDelete);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * Removes specified {@code Lesson} from the updatedLessons for this person.
     */
    private static Person createEditedPerson(Person personToEdit, List<Lesson> updatedLessons, Lesson toRemove) {
        assert personToEdit != null;

        updatedLessons.remove(toRemove);
        TreeSet<Lesson> updatedLessonSet = new TreeSet<>(updatedLessons);

        return PersonUtil.createdEditedPerson(personToEdit, updatedLessonSet);
    }

    @Override
    protected void undo() {
        requireNonNull(model);

        model.setPerson(personAfterLessonDelete, personBeforeLessonDelete);
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
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonDeleteCommand)) {
            return false;
        }

        // state check
        LessonDeleteCommand e = (LessonDeleteCommand) other;
        return index.equals(e.index)
                && lessonIndex.equals(e.lessonIndex);
    }
}
