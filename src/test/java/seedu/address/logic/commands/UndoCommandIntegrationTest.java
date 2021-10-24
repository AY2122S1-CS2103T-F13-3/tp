package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackTestUtil.assertStackStatus;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOMEWORK_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

class UndoCommandIntegrationTest {
    private static final Model DEFAULT_MODEL = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand();
    private final DummyUndoableCommand dummyUndoableCommandOne = new DummyUndoableCommand();
    private final DummyUndoableCommand dummyUndoableCommandTwo = new DummyUndoableCommand();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private UndoRedoStack undoRedoStack = new UndoRedoStack();

    @Test
    public void execute_success() {
        //Non-Undoable Command executed before undo chain
        UndoCommand undoCommand = prepareUndoCommand(Arrays.asList(dummyCommand,
                dummyUndoableCommandOne, dummyUndoableCommandTwo));

        //2 Undoable commands already called
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne),
                Collections.singletonList(dummyUndoableCommandTwo), undoRedoStack);

        //1 Undoable command already called
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.emptyList(),
                Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne), undoRedoStack);
    }

    @Test
    public void execute_failure() {
        //non-Undoable Command executed before undo
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(dummyCommand));
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertStackStatus(Collections.emptyList(), Collections.emptyList(), undoRedoStack);

        //No commands to undo
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertStackStatus(Collections.emptyList(), Collections.emptyList(), undoRedoStack);
    }

    @Test
    public void execute_undoAddCommand() {
        //Add valid person to empty default model and empty undoRedoStack
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(addCommand));

        //Check stack after Add Command executed
        assertStackStatus(Collections.singletonList(addCommand), Collections.emptyList(), undoRedoStack);

        //Undo Add Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(addCommand), undoRedoStack);
    }

    @Test
    public void execute_undoClearCommand() {
        ClearCommand clearCommand = new ClearCommand();
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(clearCommand));

        //Check stack after Clear Command
        assertStackStatus(Collections.singletonList(clearCommand), Collections.emptyList(), undoRedoStack);

        //Undo Clear Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(clearCommand), undoRedoStack);
    }

    @Test
    public void execute_undoDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(deleteCommand));

        //Check stack after Delete Command
        assertStackStatus(Collections.singletonList(deleteCommand), Collections.emptyList(), undoRedoStack);

        //Undo Delete Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(deleteCommand), undoRedoStack);
    }

    @Test
    public void execute_undoEditCommand() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(editCommand));

        //Check stack after Edit Command
        assertStackStatus(Collections.singletonList(editCommand), Collections.emptyList(), undoRedoStack);

        //Undo Edit Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(editCommand), undoRedoStack);
    }

    @Test
    public void execute_undoLessonAddCommand() {
        Lesson sampleLesson = new LessonBuilder().build();
        LessonAddCommand lessonAddCommand = new LessonAddCommand(INDEX_FIRST_PERSON, sampleLesson);
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(lessonAddCommand));

        //Check stack after Lesson Add
        assertStackStatus(Collections.singletonList(lessonAddCommand), Collections.emptyList(), undoRedoStack);

        //Undo Lesson Add Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, DEFAULT_MODEL);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(lessonAddCommand), undoRedoStack);
    }

    @Test
    public void execute_undoLessonEditCommand() {
        //Add lesson to first student
        Lesson lesson = new LessonBuilder().build();
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withLessons(lesson).build();
        model.setPerson(firstPerson, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lesson)
                .withHomeworkSet(VALID_HOMEWORK_TEXTBOOK)
                .build();
        LessonEditCommand lessonEditCommand = new LessonEditCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON, descriptor);
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(lessonEditCommand));

        //Check stack after Lesson Edit
        assertStackStatus(Collections.singletonList(lessonEditCommand), Collections.emptyList(), undoRedoStack);

        //Undo Lesson Add Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(lessonEditCommand), undoRedoStack);
    }

    @Test
    public void execute_undoLessonDeleteCommand() {
        //Add lesson to first student
        Lesson lesson = new LessonBuilder().build();
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withLessons(lesson).build();
        model.setPerson(firstPerson, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LESSON);
        UndoCommand undoCommand = prepareUndoCommand(Collections.singletonList(lessonDeleteCommand));

        //Check stack after Lesson Delete Command
        assertStackStatus(Collections.singletonList(lessonDeleteCommand), Collections.emptyList(), undoRedoStack);

        //Undo Lesson Delete Command
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        assertStackStatus(Collections.emptyList(), Collections.singletonList(lessonDeleteCommand), undoRedoStack);
    }

    private UndoCommand prepareUndoCommand(List<Command> commandsBeforeUndo) {
        //preparing the stack
        undoRedoStack = new UndoRedoStack();
        commandsBeforeUndo.forEach(command -> {
            command.setDependencies(model, undoRedoStack);
            try {
                command.execute();
                undoRedoStack.pushUndoableCommand(command);
            } catch (CommandException ce) {
                throw new AssertionError("Commands prepared should not induce error.");
            }
        });
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setDependencies(model, undoRedoStack);
        return undoCommand;
    }


    private class DummyCommand extends Command {
        @Override
        public CommandResult execute() {
            return new CommandResult("");
        }
    }

    private class DummyUndoableCommand extends UndoableCommand {
        @Override
        public CommandResult executeUndoableCommand() {
            return new CommandResult("");
        }
        @Override
        protected void undo() {}
        @Override
        protected void redo() {}
    }
}
