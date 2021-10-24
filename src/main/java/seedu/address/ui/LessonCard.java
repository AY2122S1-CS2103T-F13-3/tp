package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;

public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label lessonId;
    @FXML
    private Label title;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label rates;
    @FXML
    private FlowPane homeworkList;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        lessonId.setText(displayedIndex + ". ");
        title.setText(lesson.getSubject() + " (" + lesson.getTypeOfLesson() + ")");
        date.setText("Date: " + lesson.getDisplayDate().value);
        time.setText("Time: " + lesson.getTimeRange().toString());
        rates.setText("Rates: $" + lesson.getLessonRates().toString());
        lesson.getHomework().stream()
            .sorted(Comparator.comparing(homework -> homework.description))
            .forEach(homework -> homeworkList.getChildren()
                .add(homeworkLabel(homework.toString())));
    }

    private Label homeworkLabel(String homework) {
        Label label = new Label(homework);
        label.setWrapText(true);
        return label;
    }
}
