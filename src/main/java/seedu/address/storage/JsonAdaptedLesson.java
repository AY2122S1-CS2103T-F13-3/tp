package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private final String date;
    private final String startTime;
    private final String endTime;
    private final String subject;
    private final boolean isRecurring;
    private final List<JsonAdaptedHomework> homework = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given Lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("date") String date,
                             @JsonProperty("startTime") String startTime,
                             @JsonProperty("endTime") String endTime,
                             @JsonProperty("subject") String subject,
                             @JsonProperty("homework") List<JsonAdaptedHomework> homework) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        if (homework != null) {
            this.homework.addAll(homework);
        }
        isRecurring = true;
    }

    /**
     * Converts a given {@code Lesson} into this class for Json use.
     */
    public JsonAdaptedLesson(Lesson source) {
        date = source.getDate().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        subject = source.getSubject().subject;
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
        final Date modelDate = new Date(date);

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(startTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelStartTime = new Time(startTime);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelEndTime = new Time(endTime);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(subject);

        final Set<Homework> modelHomework = new HashSet<>(lessonHomework);
        return isRecurring
                ? new RecurringLesson(modelDate, modelStartTime, modelEndTime, modelSubject, modelHomework)
                : new MakeUpLesson(modelDate, modelStartTime, modelEndTime, modelSubject, modelHomework);
    }

}


