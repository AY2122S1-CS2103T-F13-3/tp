package seedu.address.ui;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;

/**
 * The Center Panel of the App that can switch between {@code Person Panel} and {@code Calendar Panel}.
 * Center Panel subscribes to Events meant for Person Panel and Calendar Panel
 * in order to handle the switching between the displays.
 */
public class CenterPanel extends UiPart<Region> {

    private static final String FXML = "CenterPanel.fxml";

    private WeekPanel weekPanel;

    private PersonListPanel personListPanel;

    @FXML
    private StackPane centerPanelPlaceholder;

    /**
     * Constructor for a CenterPanel.
     *
     * @param calendar The calendar in the CenterPanel.
     * @param personList The ObservableList of persons.
     */
    public CenterPanel(Calendar calendar, ObservableList<Person> personList) {
        super(FXML);
        personListPanel = new PersonListPanel(personList);
        weekPanel = new WeekPanel(calendar);
        displayPersonListPanel();
    }

    /**
     * Display the week page of the calendar interface
     */
    public void showWeek() {
        displaySchedulePanel();
        weekPanel.showWeek();
    }

    /**
     * Display the month page of the calendar interface
     */
    public void showMonth() {
        displaySchedulePanel();
        weekPanel.showMonth();
    }

    /**
     * Shows the next week of the calendar.
     */
    public void goNext() {
        displaySchedulePanel();
        weekPanel.goNext();
    }

    /**
     * Shows the current week of the calendar.
     */
    public void goToday() {
        displaySchedulePanel();
        weekPanel.goToday();
    }

    /**
     * Shows the previous week of the calendar.
     */
    public void goBack() {
        displaySchedulePanel();
        weekPanel.goBack();
    }

    /**
     * Bring PersonListPanel to top of the stack's child list.
     */
    public void displayPersonListPanel() {
        if (!centerPanelPlaceholder.getChildren().contains(personListPanel.getRoot())) {
            centerPanelPlaceholder.getChildren().setAll(personListPanel.getRoot());
        }
    }

    /**
     * Bring WeekPanel to top of the stack's child list.
     */
    public void displaySchedulePanel() {
        if (!centerPanelPlaceholder.getChildren().contains(weekPanel.getRoot())) {
            centerPanelPlaceholder.getChildren().setAll(weekPanel.getRoot());
        }
    }
}
