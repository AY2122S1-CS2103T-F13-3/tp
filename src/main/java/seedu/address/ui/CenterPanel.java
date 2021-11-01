package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The Center Panel of the App that can switch between {@code Person Panel} and {@code Calendar Panel}.
 * Center Panel subscribes to Events meant for Person Panel and Calendar Panel
 * in order to handle the switching between the displays.
 */
public class CenterPanel extends UiPart<Region> {

    private static final String FXML = "CenterPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(CenterPanel.class);

    // Independent Ui parts residing in this Ui container
    private SchedulePanel schedulePanel;
    private PersonGridPanel personGridPanel;
    private TagListPanel tagListPanel;

    @FXML
    private StackPane centerPanelPlaceholder;

    /**
     * Constructor for a CenterPanel.
     *
     * @param calendar The calendar in the CenterPanel.
     * @param personList The ObservableList of persons.
     */
    public CenterPanel(Calendar calendar, ObservableList<Person> personList, ObservableList<Lesson> lessonList,
            ObservableList<Tag> tagList, ObservableMap<Tag, Integer> tagCounter) {
        super(FXML);
        schedulePanel = new SchedulePanel(calendar);
        tagListPanel = new TagListPanel(tagList, tagCounter);
        personGridPanel = new PersonGridPanel(personList, lessonList);
        displayPersonGridPanel(lessonList);
    }

    /**
     * Display the day page of the calendar interface
     */
    public void showDay() {
        displaySchedulePanel();
        schedulePanel.showDay();
    }

    /**
     * Display the week page of the calendar interface
     */
    public void showWeek() {
        displaySchedulePanel();
        schedulePanel.showWeek();
    }

    /**
     * Display the month page of the calendar interface
     */
    public void showMonth() {
        displaySchedulePanel();
        schedulePanel.showMonth();
    }

    /**
     * Display the year page of the calendar interface
     */
    public void showYear() {
        displaySchedulePanel();
        schedulePanel.showYear();
    }

    /**
     * Shows the next week of the calendar.
     */
    public void goNext() {
        displaySchedulePanel();
        schedulePanel.goNext();
    }

    /**
     * Shows the current week of the calendar.
     */
    public void goToday() {
        displaySchedulePanel();
        schedulePanel.goToday();
    }

    /**
     * Shows the previous week of the calendar.
     */
    public void goBack() {
        displaySchedulePanel();
        schedulePanel.goBack();
    }

    public ListView<Person> getPersonListView() {
        return personGridPanel.getPersonListView();
    }

    /**
     * Bring PersonGridPanel to top of the stack's child list.
     */
    public void displayPersonGridPanel(ObservableList<Lesson> lessons) {
        logger.info("Showing the student list without lessons.");
        personGridPanel.fillListPanels(lessons);
        personGridPanel.setListPanels();
        centerPanelPlaceholder.getChildren().setAll(personGridPanel.getRoot());
    }

    /**
     * Bring PersonGridPanel to top of the stack's child list.
     *
     * @param student Selected student to view.
     * @param lessons Lessons of the student.
     */
    public void displayPersonGridPanel(Person student, ObservableList<Lesson> lessons) {
        logger.info("Showing the student list with lessons.");
        personGridPanel.fillListPanels(student, lessons);
        personGridPanel.setListPanels();
        centerPanelPlaceholder.getChildren().setAll(personGridPanel.getRoot());
        personGridPanel.getPersonListView().getSelectionModel().select(student);
    }

    /**
     * Brings SchedulePanel to top of the stack's child list.
     */
    public void displaySchedulePanel() {
        logger.info("Showing the schedule calendar.");
        if (!centerPanelPlaceholder.getChildren().contains(schedulePanel.getRoot())) {
            centerPanelPlaceholder.getChildren().setAll(schedulePanel.getRoot());
        }
    }

    /**
     * Brings TagListPanel to top of the stack's child list.
     */
    public void displayTagListPanel() {
        logger.info("Showing the list of tags.");
        if (!centerPanelPlaceholder.getChildren().contains(tagListPanel.getRoot())) {
            centerPanelPlaceholder.getChildren().setAll(tagListPanel.getRoot());
        }
    }
}
