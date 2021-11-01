package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.LessonEditCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.OutstandingFees;
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
     * Returns an {@code EditLessonDescriptor} with fields containing {@code lesson}'s details.
     * Excludes cancelled dates.
     */
    public EditLessonDescriptorBuilder(Lesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setDate(lesson.getStartDate());
        descriptor.setEndDate(lesson.getEndDate());
        descriptor.setTimeRange(lesson.getTimeRange());
        descriptor.setSubject(lesson.getSubject());
        descriptor.setHomeworkSet(lesson.getHomework());
        descriptor.setCancelDates(lesson.getCancelledDates());
        descriptor.setUncancelDates(new HashSet<>());
        descriptor.setRecurring(lesson.isRecurring());
        descriptor.setLessonRate(lesson.getLessonRates());
        descriptor.setOutstandingFees(lesson.getOutstandingFees());
    }

    /**
     * Sets the {@code isRecurring} flag of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withRecurrence() {
        descriptor.setRecurring(true);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(StringUtil.stripLeadingZeroes(date)));
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withEndDate(String date) {
        descriptor.setEndDate(new Date(StringUtil.stripLeadingZeroes(date)));
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
    public EditLessonDescriptorBuilder withLessonRates(String rate) {
        descriptor.setLessonRate(new LessonRates(rate));
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withOutstandingFees(String outstandingFees) {
        descriptor.setOutstandingFees(new OutstandingFees(outstandingFees));
        return this;
    }

    /**
     * Parses the {@code cancelledDates} into a {@code Set<Date>} and set it to the {@code EditLessonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withCancelDates(String... cancelDates) {
        Set<Date> cancelDatesSet = Stream.of(cancelDates)
                .map(date -> new Date(StringUtil.stripLeadingZeroes(date))).collect(Collectors.toSet());
        descriptor.setCancelDates(cancelDatesSet);
        return this;
    }

    /**
     * Parses the {@code uncancelledDates} into a {@code Set<Date>} and set it to the {@code EditLessonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withUncancelDates(String... uncancelDates) {
        Set<Date> uncancelDatesSet = Stream.of(uncancelDates)
                .map(date -> new Date(StringUtil.stripLeadingZeroes(date))).collect(Collectors.toSet());
        descriptor.setUncancelDates(uncancelDatesSet);
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
