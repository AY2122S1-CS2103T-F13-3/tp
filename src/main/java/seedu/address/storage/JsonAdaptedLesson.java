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
import seedu.address.model.lesson.OutstandingFees;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    public static final String MESSAGE_INVALID_CANCELLED_DATE = "Cancelled date is not a date of this lesson.";

    private final String date;
    private final String endDate;
    private final String timeRange;
    private final String subject;
    private final String lessonRates;
    private final boolean isRecurring;
    private final String outstandingFees;
    private final List<JsonAdaptedHomework> homework = new ArrayList<>();
    private final List<JsonAdaptedDate> cancelledDates = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given Lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("date") String date, @JsonProperty("endDate") String endDate,
                             @JsonProperty("timeRange") String timeRange,
                             @JsonProperty("subject") String subject,
                             @JsonProperty("homework") List<JsonAdaptedHomework> homework,
                             @JsonProperty("lessonRates") String lessonRates,
                             @JsonProperty("outstandingFees") String outstandingFees,
                             @JsonProperty("cancelledDate") List<JsonAdaptedDate> cancelledDates) {
        this.date = date;
        this.endDate = endDate;
        this.timeRange = timeRange;
        this.subject = subject;
        this.lessonRates = lessonRates;
        this.outstandingFees = outstandingFees;
        if (homework != null) {
            this.homework.addAll(homework);
        }
        if (cancelledDates != null) {
            this.cancelledDates.addAll(cancelledDates);
        }
        isRecurring = true;
    }

    /**
     * Converts a given {@code Lesson} into this class for Json use.
     */
    public JsonAdaptedLesson(Lesson source) {
        date = source.getStartDate().value;
        endDate = source.getEndDate().value;
        timeRange = source.getTimeRange().value;
        subject = source.getSubject().value;
        lessonRates = source.getLessonRates().value;
        outstandingFees = source.getOutstandingFees().value;
        homework.addAll(source.getHomework().stream()
                .map(JsonAdaptedHomework::new)
                .collect(Collectors.toList()));
        isRecurring = source.isRecurring();
        cancelledDates.addAll(source.getCancelledDates().stream()
                .map(JsonAdaptedDate::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        checkNullFields();

        String strippedDate = date.strip();
        String strippedEndDate = endDate.strip();
        if (!Date.isValidDate(date) || !Date.isValidDate(endDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(StringUtil.stripLeadingZeroes(strippedDate));
        final Date modelEndDate = new Date(StringUtil.stripLeadingZeroes(strippedEndDate));

        String strippedTimeRange = timeRange.strip();
        if (!TimeRange.isValidTimeRange(strippedTimeRange)) {
            throw new IllegalValueException(TimeRange.MESSAGE_CONSTRAINTS);
        }
        final TimeRange modelTimeRange = new TimeRange(strippedTimeRange);

        String strippedSubject = subject.strip();
        if (!Subject.isValidSubject(strippedSubject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(strippedSubject);

        String strippedLessonRates = lessonRates.strip();
        if (!LessonRates.isValidMonetaryField(strippedLessonRates)) {
            throw new IllegalValueException(LessonRates.MESSAGE_CONSTRAINTS);
        }
        final LessonRates modelLessonRates = new LessonRates(strippedLessonRates);

        String strippedOutstandingFees = outstandingFees.strip();
        if (!OutstandingFees.isValidMonetaryField(strippedOutstandingFees)) {
            throw new IllegalValueException(OutstandingFees.MESSAGE_CONSTRAINTS);
        }
        final OutstandingFees modelOutstandingFees = new OutstandingFees(outstandingFees);

        final List<Homework> lessonHomework = new ArrayList<>();
        for (JsonAdaptedHomework hw : homework) {
            lessonHomework.add(hw.toModelType());
        }
        final Set<Homework> modelHomework = new HashSet<>(lessonHomework);

        Set<Date> modelCancelledDates = new HashSet<>();

        // lesson used to check if cancelled dates are valid
        Lesson lessonWithoutCancelledDates = isRecurring
                ? new RecurringLesson(modelDate, modelEndDate, modelTimeRange, modelSubject, modelHomework,
                        modelLessonRates, modelOutstandingFees, modelCancelledDates)
                : new MakeUpLesson(modelDate, modelTimeRange, modelSubject, modelHomework, modelLessonRates,
                        modelOutstandingFees, modelCancelledDates);

        final List<Date> dates = new ArrayList<>();

        for (JsonAdaptedDate date : cancelledDates) {
            Date cancelledDate = date.toModelType();
            if (!lessonWithoutCancelledDates.hasLessonOnDate(cancelledDate)) {
                throw new IllegalValueException(MESSAGE_INVALID_CANCELLED_DATE);
            }
            dates.add(cancelledDate);
        }
        modelCancelledDates = new HashSet<>(dates);

        return lessonWithoutCancelledDates.updateCancelledDates(modelCancelledDates);
    }

    private void checkNullFields() throws IllegalValueException {
        if (date == null || endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (timeRange == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeRange.class.getSimpleName()));
        }
        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (lessonRates == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonRates.class.getSimpleName()));
        }
        if (outstandingFees == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OutstandingFees.class.getSimpleName()));
        }
    }
}
