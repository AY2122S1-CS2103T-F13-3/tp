package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Date;
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
    private Label cancelledDates;
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

        Set<Date> lessonCancelledDates = lesson.getCancelledDates();
        if (lessonCancelledDates.isEmpty()) {
            cancelledDates.setManaged(false);
            return;
        }
        if (lesson.isRecurring()) {
            List<String> dates = lesson.getCancelledDates().stream().sorted()
                    .map(Date::toString).collect(Collectors.toList());
            cancelledDates.setText("Cancelled Dates:\n" + String.join(",\n", dates));
        } else if (lesson.getCancelledDates().size() > 0) {
            cancelledDates.setText("Cancelled!");
        }
    }

    private Label homeworkLabel(String homework) {
        Label label = new Label(homework);
        label.setWrapText(true);
        return label;
    }
}
