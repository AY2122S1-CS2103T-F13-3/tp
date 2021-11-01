package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    private static final String TITLE_PLACEHOLDER_MESSAGE = "Student Lessons";
    private static final String LIST_PLACEHOLDER_MESSAGE = "Type \"view INDEX\" to "
            + "view the lessons of the student identified by INDEX in the displayed list.";
    private static final String NO_EXISTING_LESSONS_MESSAGE = "This student has no lessons!";
    private static final double PLACEHOLDER_WIDTH = 0.65;
    private static final double TITLE_HEIGHT = 1.1;
    private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Lesson> lessonListView;
    @FXML
    private HBox titleBox;
    @FXML
    private Label lessonListTitle;
    @FXML
    private Label placeholder;

    /**
     * Creates a {@code LessonListPanel} with the given {@code ObservableList}.
     */
    public LessonListPanel(ObservableList<Lesson> lessonList) {
        super(FXML);
        initialiseLessonListView(lessonList);
        lessonListTitle.setText(TITLE_PLACEHOLDER_MESSAGE);
        placeholder.setText(LIST_PLACEHOLDER_MESSAGE);
        placeholder.visibleProperty().bind(Bindings.isEmpty(lessonListView.getItems()));
        placeholder.prefWidthProperty().bind(getRoot().widthProperty().multiply(PLACEHOLDER_WIDTH));
    }

    /**
     * Creates a {@code LessonListPanel} with the given {@code ObservableList}.
     */
    public LessonListPanel(ObservableList<Lesson> lessonList, Person student) {
        super(FXML);
        initialiseLessonListView(lessonList);

        lessonListTitle.setText(student.getName().fullName);
        titleBox.minHeightProperty().bind(lessonListTitle.heightProperty().multiply(TITLE_HEIGHT));
        if (lessonList.isEmpty()) {
            placeholder.setText(NO_EXISTING_LESSONS_MESSAGE);
        }
    }

    private void initialiseLessonListView(ObservableList<Lesson> lessonList) {
        lessonListView.getItems().addAll(lessonList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}.
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                LessonCard lessonCard = new LessonCard(lesson, getIndex() + 1);
                setGraphic(lessonCard.getRoot());
            }
        }
    }
}
