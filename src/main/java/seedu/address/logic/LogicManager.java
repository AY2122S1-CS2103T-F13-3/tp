package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.LastUpdatedDate;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final UndoRedoStack undoRedoStack;
    private final AddressBookParser addressBookParser;
    private final Calculator feesCalculator;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        feesCalculator = new FeesCalculator(model.getLastUpdatedDate(), LocalDateTime.now());
        this.model = feesCalculator.updateAllLessonOutstandingFees(model);

        // After model is updated. Save model to storage.
        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException io) {
            logger.warning("SYSTEM WILL NOT SAVE LAST UPDATED DATE. PLEASE RESTART THE APPLICATION.");
        }

        this.storage = storage;
        undoRedoStack = new UndoRedoStack();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        command.setDependencies(model, undoRedoStack); //equivalent to setting parameters for command.execute()
        commandResult = command.execute();
        undoRedoStack.pushUndoableCommand(command);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }


    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Tag> getObservableTagList() {
        return model.getObservableTagList();
    }

    @Override
    public ObservableMap<Tag, Integer> getTagCounter() {
        return model.getTagCounter();
    }

    public ObservableList<Lesson> getLessonList(Person student) {
        ObservableList<Lesson> internalList = FXCollections.observableArrayList();
        internalList.addAll(student.getLessons());
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public ObservableList<Lesson> getEmptyLessonList() {
        ObservableList<Lesson> internalList = FXCollections.observableArrayList();
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Calendar getCalendar() {
        return model.getCalendar();
    }

    @Override
    public ObservableList<Entry<Lesson>> getUpcomingLessons() {
        return model.getUpcomingLessons();
    }

    @Override
    public void updateUpcomingLessons() {
        logger.info("Update upcoming lessons");
        model.updateUpcomingLessons();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public LastUpdatedDate getLastUpdatedDate() {
        return model.getLastUpdatedDate();
    }
}
