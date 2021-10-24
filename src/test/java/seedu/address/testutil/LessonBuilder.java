package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.model.util.SampleDataUtil;

public class LessonBuilder {

    public static final String DEFAULT_DATE = "20 Mar 2000";
    public static final String DEFAULT_TIME_RANGE = "1400-1500";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    public static final String DEFAULT_LESSON_RATES = "50";
    public static final String DEFAULT_HOMEWORK = "Textbook Page 5";

    private Date date;
    private TimeRange timeRange;
    private Subject subject;
    private LessonRates lessonRates;
    private Set<Homework> homeworkSet;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        date = new Date(DEFAULT_DATE);
        timeRange = new TimeRange(DEFAULT_TIME_RANGE);
        lessonRates = new LessonRates(DEFAULT_LESSON_RATES);
        subject = new Subject(DEFAULT_SUBJECT);
        homeworkSet = new HashSet<>();
        homeworkSet.add(new Homework(DEFAULT_HOMEWORK));
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        date = lessonToCopy.getStartDate();
        timeRange = lessonToCopy.getTimeRange();
        lessonRates = lessonToCopy.getLessonRates();
        subject = lessonToCopy.getSubject();
        homeworkSet = new HashSet<>(lessonToCopy.getHomework());
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDate(String date) {
        this.date = new Date(date);
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
     * Parses the {@code homeworkList} into a {@code Set<Homework>} and
     * set it to the {@code Lesson} that we are building.
     */
    public LessonBuilder withHomeworkSet(String ... homeworkList) {
        homeworkSet = SampleDataUtil.getHomeworkSet(homeworkList);
        return this;
    }

    /**
     * Builds a recurring lesson with the specified information.
     *
     * @return {@code RecurringLesson} containing the information given.
     */
    public Lesson buildRecurring() {
        return new RecurringLesson(date, timeRange, subject, homeworkSet, lessonRates);
    }

    /**
     * Default to build a make up lesson with the details.
     * Builds a makeup lesson with the specified information.
     *
     * @return {@code MakeUpLesson} containing the information given.
     */
    public Lesson build() {
        return new MakeUpLesson(date, timeRange, subject, homeworkSet, lessonRates);
    }
}
