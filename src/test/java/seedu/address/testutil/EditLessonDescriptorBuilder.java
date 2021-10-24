package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;

public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLessonDescriptor} with fields containing {@code lesson}'s details
     */
    public EditLessonDescriptorBuilder(Lesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setDate(lesson.getStartDate());
        descriptor.setTimeRange(lesson.getTimeRange());
        descriptor.setSubject(lesson.getSubject());
        descriptor.setHomeworkSet(lesson.getHomework());
        descriptor.setRate(lesson.getLessonRates());
    }

    /**
     * Sets the {@code Date} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(StringUtil.stripLeadingZeroes(date)));
        return this;
    }

    /**
     * Sets the {@code TimeRange} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withTimeRange(String timeRange) {
        descriptor.setTimeRange(new TimeRange(timeRange));
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withSubject(String subject) {
        descriptor.setSubject(new Subject(subject));
        return this;
    }

    /**
     * Parses the {@code homework pieces} into a {@code Set<Homework>} and set it to the {@code EditLessonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withHomeworkSet(String... homeworkList) {
        Set<Homework> homeworkSet = Stream.of(homeworkList).map(Homework::new).collect(Collectors.toSet());
        descriptor.setHomeworkSet(homeworkSet);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withRate(String rate) {
        descriptor.setRate(new LessonRates(rate));
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
