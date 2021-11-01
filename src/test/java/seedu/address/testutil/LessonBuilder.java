package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

public class LessonBuilder {

    public static final String DEFAULT_DATE = "20 Mar 2000";
    public static final String DEFAULT_TIME_RANGE = "1400-1500";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    public static final String DEFAULT_LESSON_RATES = "50";
    public static final String DEFAULT_OUTSTANDING_FEES = "100";
    public static final String DEFAULT_HOMEWORK = "Textbook Page 5";

    private Date date;
    private Date endDate;
    private TimeRange timeRange;
    private Subject subject;
    private LessonRates lessonRates;
    private OutstandingFees outstandingFees;
    private Set<Homework> homeworkSet;
    private Set<Date> cancelledDatesSet;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        date = new Date(DEFAULT_DATE);
        endDate = Date.MAX_DATE; // default
        timeRange = new TimeRange(DEFAULT_TIME_RANGE);
        lessonRates = new LessonRates(DEFAULT_LESSON_RATES);
        subject = new Subject(DEFAULT_SUBJECT);
        homeworkSet = new HashSet<>();
        outstandingFees = new OutstandingFees(DEFAULT_OUTSTANDING_FEES); //default lastAdded = LocalDate.now()
        homeworkSet.add(new Homework(DEFAULT_HOMEWORK));
        cancelledDatesSet = new HashSet<>();
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        date = lessonToCopy.getStartDate();
        endDate = lessonToCopy.getEndDate();
        timeRange = lessonToCopy.getTimeRange();
        lessonRates = lessonToCopy.getLessonRates();
        subject = lessonToCopy.getSubject();
        homeworkSet = new HashSet<>(lessonToCopy.getHomework());
        outstandingFees = lessonToCopy.getOutstandingFees();
        cancelledDatesSet = new HashSet<>(lessonToCopy.getCancelledDates());
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDate(String date) {
        this.date = new Date(StringUtil.stripLeadingZeroes(date));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building
     * with given {@code Date} object.
     */
    public LessonBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndDate(String date) {
        this.endDate = new Date(date);
        return this;
    }

    /**
     * Sets the {@code TimeRange} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withTimeRange(String timeRange) {
        this.timeRange = new TimeRange(timeRange);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code LessonRates} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLessonRates(String lessonRates) {
        this.lessonRates = new LessonRates(lessonRates);
        return this;
    }

    /**
     * Sets the {@code OutstandingFees} of the {@code Lesson} that we are building.
     * The lastAdded field would be the current date.
     */
    public LessonBuilder withOutstandingFees(String fees) {
        this.outstandingFees = new OutstandingFees(fees);
        return this;
    }


    /**
     * Parses the {@code homeworkList} into a {@code Set<Homework>} and
     * set it to the {@code Lesson} that we are building.
     */
    public LessonBuilder withHomeworkSet(String... homeworkList) {
        homeworkSet = SampleDataUtil.getHomeworkSet(homeworkList);
        return this;
    }

    /**
     * Parses the {@code cancelledDates} into a {@code Set<Date>} and
     * set it to the {@code Lesson} that we are building.
     */
    public LessonBuilder withCancelledDatesSet(String... cancelledDates) {
        cancelledDatesSet = SampleDataUtil.getCancelledDateSet(cancelledDates);
        return this;
    }

    /**
     * Sets the {@code cancelledDates} to the {@code Lesson} that we are building.
     */
    public LessonBuilder withCancelledDatesSet(Date... cancelledDates) {
        cancelledDatesSet = Set.of(cancelledDates);
        return this;
    }

    /**
     * Builds a recurring lesson with the specified information.
     *
     * @return {@code RecurringLesson} containing the information given.
     */
    public Lesson buildRecurring() {
        return new RecurringLesson(date, endDate, timeRange,
                subject, homeworkSet, lessonRates, outstandingFees, cancelledDatesSet);
    }

    /**
     * Default to build a make up lesson with the details.
     * Builds a makeup lesson with the specified information.
     *
     * @return {@code MakeUpLesson} containing the information given.
     */
    public Lesson build() {
        return new MakeUpLesson(date, timeRange, subject, homeworkSet,
                lessonRates, outstandingFees, cancelledDatesSet);
    }
}
