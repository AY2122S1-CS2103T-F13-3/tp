package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonUtil;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;
    private ObservableList<Person> personList;
    PersonGridPanel personGridPanel;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, PersonGridPanel personGridPanel) {
        super(FXML);
        this.personList = personList;
        this.personGridPanel = personGridPanel;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    public void handleMouseClick() {
        int index = personListView.getSelectionModel().getSelectedIndex();
        Person student = personList.get(index);
        personGridPanel.fillListPanels(student, PersonUtil.getLessonList(student));
        personGridPanel.setListPanels();
        logger.info("Showing list of lessons for " + student.getName());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
                setOnMouseClicked(event -> handleMouseClick());
            }
        }
    }
}
