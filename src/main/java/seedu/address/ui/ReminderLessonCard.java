package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private FlowPane homeworkList;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ReminderLessonCard(Lesson lesson, int displayedIndex, String lessonTitle) {
        super(FXML);
        this.lesson = lesson;
        this.lessonTitle.setText(lessonTitle);
        lessonId.setText(displayedIndex + ". ");
        date.setText("Date: " + lesson.getDisplayDate().value);
        time.setText("Time: " + lesson.getTimeRange().toString());
        rates.setText("Rates: $" + lesson.getLessonRates().toString());
        lesson.getHomework().stream()
                .sorted(Comparator.comparing(homework -> homework.description))
                .forEach(homework -> homeworkList.getChildren()
                        .add(new Label(homework + "\n")));
    }
}
