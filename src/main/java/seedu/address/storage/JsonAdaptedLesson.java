package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private final String date;
    private final String timeRange;
    private final String subject;
    private final String lessonRates;
    private final boolean isRecurring;
    private final List<JsonAdaptedHomework> homework = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given Lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("date") String date,
                             @JsonProperty("timeRange") String timeRange,
                             @JsonProperty("subject") String subject,
                             @JsonProperty("homework") List<JsonAdaptedHomework> homework,
                             @JsonProperty("lessonRates") String lessonRates) {
        this.date = date;
        this.timeRange = timeRange;
        this.subject = subject;
        this.lessonRates = lessonRates;
        if (homework != null) {
            this.homework.addAll(homework);
        }
        isRecurring = true;
    }

    /**
     * Converts a given {@code Lesson} into this class for Json use.
     */
    public JsonAdaptedLesson(Lesson source) {
        date = source.getStartDate().value;
        timeRange = source.getTimeRange().value;
        subject = source.getSubject().subject;
        lessonRates = source.getLessonRates().value;
        homework.addAll(source.getHomework().stream()
                .map(JsonAdaptedHomework::new)
                .collect(Collectors.toList()));
        isRecurring = source.isRecurring();

    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        final List<Homework> lessonHomework = new ArrayList<>();
        for (JsonAdaptedHomework hw : homework) {
            lessonHomework.add(hw.toModelType());
        }

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(StringUtil.stripLeadingZeroes(date));

        if (timeRange == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeRange.class.getSimpleName()));
        }
        if (!TimeRange.isValidTimeRange(timeRange)) {
            throw new IllegalValueException(TimeRange.MESSAGE_CONSTRAINTS);
        }
        final TimeRange modelTimeRange = new TimeRange(timeRange);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(subject);

        if (lessonRates == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonRates.class.getSimpleName()));
        }
        if (!LessonRates.isValidLessonRates(lessonRates)) {
            throw new IllegalValueException(LessonRates.MESSAGE_CONSTRAINTS);
        }
        final LessonRates modelLessonRates = new LessonRates(lessonRates);

        final Set<Homework> modelHomework = new HashSet<>(lessonHomework);
        return isRecurring
                ? new RecurringLesson(modelDate, modelTimeRange, modelSubject, modelHomework, modelLessonRates)
                : new MakeUpLesson(modelDate, modelTimeRange, modelSubject, modelHomework, modelLessonRates);
    }
}
