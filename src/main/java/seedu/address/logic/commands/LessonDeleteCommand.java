package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
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
 * Deletes a lesson from a person identified using their respective displayed index from the address book.
 */
public class LessonDeleteCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Delete Lesson";

    public static final String COMMAND_WORD = "ldelete";

    public static final String COMMAND_PARAMETERS = "INDEX "
            + "LESSON_INDEX";

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

        personBeforeLessonDelete = CommandUtil.getPerson(lastShownList, index);

        List<Lesson> lessonList = new ArrayList<>(personBeforeLessonDelete.getLessons());
        Lesson toRemove = CommandUtil.getLesson(lessonList, lessonIndex);

        Set<Lesson> updatedLessons = createUpdatedLessons(lessonList, toRemove);
        personAfterLessonDelete = PersonUtil.createdEditedPerson(personBeforeLessonDelete, updatedLessons);

        model.setPerson(personBeforeLessonDelete, personAfterLessonDelete);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS,
                personAfterLessonDelete.getName(), toRemove), personAfterLessonDelete);
    }

    /**
     * Removes specified {@code Lesson} from the lesson list.
     *
     * @param lessonList The list of lessons.
     * @param toRemove The lesson to remove from the list.
     * @return An updated lesson set.
     */
    private Set<Lesson> createUpdatedLessons(List<Lesson> lessonList, Lesson toRemove) {
        assert lessonList != null;

        lessonList.remove(toRemove);
        TreeSet<Lesson> updatedLessonSet = new TreeSet<>(lessonList);
        return updatedLessonSet;
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
