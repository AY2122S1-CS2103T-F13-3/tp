package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.Lesson;

public class ReminderLessonCard extends UiPart<Region> {

    private static final String FXML = "ReminderLessonCard.fxml";

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label lessonTitle;
    @FXML
    private Label lessonId;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label rates;
    @FXML
    private Label outstandingFees;
    @FXML
    private VBox homeworkList;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ReminderLessonCard(Lesson lesson, int displayedIndex, String lessonTitle) {
        super(FXML);
        this.lesson = lesson;
        this.lessonTitle.setText(lessonTitle);
        lessonId.setText(displayedIndex + ". ");
        date.setText(lesson.getDisplayDate().value);
        time.setText(lesson.getTimeRange().toString());
        rates.setText(lesson.getLessonRates().toString());
        outstandingFees.setText(lesson.getOutstandingFees().toString());
        homeworkList.setManaged(!lesson.getHomework().isEmpty());
        lesson.getHomework().stream()
                .sorted(Comparator.comparing(homework -> homework.description))
                .forEach(homework -> homeworkList.getChildren()
                        .add(new Label(homework + "\n")));
    }
}
