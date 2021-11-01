package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
    private Label dateRangePlaceholder;
    @FXML
    private Label dateRange;
    @FXML
    private Label time;
    @FXML
    private Label rates;
    @FXML
    private Label outstandingFees;
    @FXML
    private Label cancelPlaceholder;
    @FXML
    private Label cancelledDates;
    @FXML
    private VBox homeworkList;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        lessonId.setText(displayedIndex + ". ");
        title.setText(lesson.getSubject() + " (" + lesson.getTypeOfLesson() + ")");
        date.setText(lesson.getDisplayDate().toString());
        time.setText(lesson.getTimeRange().toString());
        rates.setText(lesson.getLessonRates().toString());
        outstandingFees.setText(lesson.getOutstandingFees().toString());

        dateRangePlaceholder.setManaged(lesson.isRecurring());
        dateRange.setManaged(lesson.isRecurring());
        String endDateString = lesson.getEndDate().equals(Date.MAX_DATE) ? "∞" : lesson.getEndDate().toString();
        String dateRangeString =
                String.format("%1$s - %2$s", lesson.getStartDate().toString(), endDateString);
        dateRange.setText(dateRangeString);

        homeworkList.setManaged(!lesson.getHomework().isEmpty());
        lesson.getHomework().stream()
                .sorted(Comparator.comparing(homework -> homework.description))
                .forEach(homework -> homeworkList.getChildren()
                        .add(createHomeworkLabel(homework.toString())));

        setCancelledDated(lesson);
    }

    private void setCancelledDated(Lesson lesson) {
        Set<Date> lessonCancelledDates = lesson.getCancelledDates();
        if (lessonCancelledDates.isEmpty()) {
            cancelPlaceholder.setManaged(false);
            cancelledDates.setManaged(false);
            return;
        }
        if (lesson.isRecurring()) {
            List<String> dates = lesson.getCancelledDates().stream().sorted()
                    .map(Date::toString).collect(Collectors.toList());
            cancelledDates.setText(String.join(",\n", dates));
        } else if (lesson.getCancelledDates().size() > 0) {
            cancelledDates.setManaged(false);
            cancelPlaceholder.setText("Cancelled!");
        }
    }

    private Label createHomeworkLabel(String homework) {
        Label label = new Label(homework);
        label.setWrapText(true);
        return label;
    }
}
