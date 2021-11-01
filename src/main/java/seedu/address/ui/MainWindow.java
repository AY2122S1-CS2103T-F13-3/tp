package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LessonAddCommand;
import seedu.address.logic.commands.WeekCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private static final String QUICK_START_INSTRUCTIONS = "💡 Quick Tips:\n"
            + AddCommand.USER_TIP + "\n\n"
            + DeleteCommand.USER_TIP + "\n\n"
            + LessonAddCommand.USER_TIP + "\n\n"
            + WeekCommand.USER_TIP + "\n\n"
            + ClearCommand.USER_TIP + "\n\n"
            + HelpCommand.USER_TIP + "\n\n"
            + "Have fun using TAB! \\ (๑ > ᴗ < ๑) / ♡";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CenterPanel centerPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReminderWindow reminderWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem remindMenuItem;

    @FXML
    private MenuItem studentsMenuItem;

    @FXML
    private MenuItem calendarMenuItem;

    @FXML
    private MenuItem tagsMenuItem;

    @FXML
    private StackPane centerPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        reminderWindow = new ReminderWindow(logic.getUpcomingLessons());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(studentsMenuItem, KeyCombination.valueOf("F2"));
        setAccelerator(calendarMenuItem, KeyCombination.valueOf("F3"));
        setAccelerator(tagsMenuItem, KeyCombination.valueOf("F4"));
        setAccelerator(remindMenuItem, KeyCombination.valueOf("F5"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * ListViews will also consume F2 function key events.
         *
         * For now, we add following event filter to capture such key events
         * purposely so to support accelerators even when focus is
         * in CommandBox, ResultDisplay, or CenterPanel.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        centerPanel = new CenterPanel(logic.getCalendar(), logic.getFilteredPersonList(), logic.getEmptyLessonList(),
                logic.getObservableTagList(), logic.getTagCounter());
        centerPanelPlaceholder.getChildren().add(centerPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser(QUICK_START_INSTRUCTIONS);

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        initListeners();

        initKeyPressEventHandler(commandBox);
    }

    private void initKeyPressEventHandler(CommandBox commandBox) {
        // Add event handlers
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (commandBox.getCommandTextField().isFocused()) {
                return; // Don't filter if already in focus
            }
            if (isTextInputKeyCode(event.getCode())) {
                commandBox.getCommandTextField().requestFocus();
                commandBox.getCommandTextField().selectEnd();
            }
        });
    }

    /**
     * Determines if keycode is a punctuation key base on optionally fixed virtual key codes
     *
     * @param keyCode The {@code KeyCode} to check
     * @return True if it's a punctuation
     */
    private boolean isPunctuationKey(KeyCode keyCode) {
        KeyCode[] punctuationKeyCodes = {
            KeyCode.SPACE, KeyCode.BACK_SPACE,
            KeyCode.BACK_QUOTE, KeyCode.MINUS, KeyCode.EQUALS, KeyCode.SLASH, // first row
            KeyCode.OPEN_BRACKET, KeyCode.CLOSE_BRACKET, KeyCode.BACK_SLASH, // second row
            KeyCode.SEMICOLON, KeyCode.QUOTE, KeyCode.COMMA, KeyCode.PERIOD, KeyCode.SLASH, // third row
            KeyCode.DIVIDE, KeyCode.MULTIPLY, KeyCode.SUBTRACT, KeyCode.ADD, KeyCode.DECIMAL // Num pad Keys
        };
        for (KeyCode code : punctuationKeyCodes) {
            if (code.equals(keyCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTextInputKeyCode(KeyCode keyCode) {
        return keyCode.isLetterKey() || keyCode.isDigitKey()
                || isPunctuationKey(keyCode);
    }

    private void initListeners() {
        // Add listeners
        centerPanel.getPersonListView().getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        handlePersonGridPanel(newVal);
                    }
                });
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleReminder() {
        logic.updateUpcomingLessons();
        if (!reminderWindow.isShowing()) {
            reminderWindow.show();
        } else {
            reminderWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        reminderWindow.hide();
        primaryStage.hide();
    }

    @FXML
    private void handleCalendar() {
        centerPanel.displaySchedulePanel();
    }

    private void handleDay() {
        centerPanel.showDay();
    }

    private void handleWeek() {
        centerPanel.showWeek();
    }

    private void handleMonth() {
        centerPanel.showMonth();
    }

    private void handleYear() {
        centerPanel.showYear();
    }

    private void handleNext() {
        centerPanel.goNext();
    }

    private void handleToday() {
        centerPanel.goToday();
    }

    private void handleBack() {
        centerPanel.goBack();
    }

    @FXML
    private void handlePersonGridPanel() {
        centerPanel.displayPersonGridPanel(logic.getEmptyLessonList());
    }

    private void handlePersonGridPanel(Person student) {
        requireNonNull(student);
        centerPanel.displayPersonGridPanel(student, logic.getLessonList(student));
    }

    /**
     * Displays tag list instead of the default person list.
     */
    @FXML
    private void handleShowTagList() {
        centerPanel.displayTagListPanel();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            switch (commandResult.getDisplayType()) {
            case HELP:
                handleHelp();
                break;

            case REMINDER:
                handleReminder();
                break;

            case EXIT:
                handleExit();
                break;

            case STUDENTS:
                if (commandResult.getStudent().isPresent()) {
                    Person student = commandResult.getStudent().get();
                    handlePersonGridPanel(student);
                } else {
                    handlePersonGridPanel();
                }
                break;

            case TAGS:
                handleShowTagList();
                break;

            case CALENDAR:
                handleCalendar();
                break;

            case DAY:
                handleDay();
                break;

            case WEEK:
                handleWeek();
                break;

            case MONTH:
                handleMonth();
                break;

            case YEAR:
                handleYear();
                break;

            case NEXT:
                handleNext();
                break;

            case TODAY:
                handleToday();
                break;

            case BACK:
                handleBack();
                break;

            default:
                throw new AssertionError("Should not reach here.");
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
