package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonUtil;

/**
 * Edits the details of an existing person in the address book.
 */
public class LessonEditCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Edit Lesson";

    public static final String COMMAND_WORD = "ledit";

    public static final String COMMAND_PARAMETERS = "INDEX (must be a positive integer) "
            + "LESSON_INDEX (must be a positive integer)\n"
            + "[" + PREFIX_TIME + "HHmm-HHmm] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_RATES + "RATE] "
            + "[" + PREFIX_HOMEWORK + "HOMEWORK]...";

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " 1 1 "
            + PREFIX_HOMEWORK + "Textbook Pg2 "
            + PREFIX_SUBJECT + "Biology";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the lesson identified by lesson index"
            + " of the student identified by the index number used in the displayed student list.\n"
            + "All fields of a lesson can be edited, with the exception of the start date.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited lesson for student %1$s:\n%2$s\nto %3$s";
    public static final String MESSAGE_CLASHING_LESSON = "This edit will result in clashes with an existing lesson.";
    public static final String MESSAGE_NOT_EDITED = "You must be provide at least one field to edit!";
    public static final String MESSAGE_ATTEMPT_TO_EDIT_TYPE = "You cannot edit the type of a lesson. Please add another"
            + " lesson if you wish to do so.";

    private final Index index;
    private final Index indexToEdit;
    private Person personBeforeLessonEdit;
    private Person personAfterLessonEdit;
    private final EditLessonDescriptor editLessonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit.
     * @param indexToEdit to edit.
     */
    public LessonEditCommand(Index index, Index indexToEdit, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(indexToEdit);

        this.index = index;
        this.indexToEdit = indexToEdit;
        this.editLessonDescriptor = editLessonDescriptor;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        personBeforeLessonEdit = lastShownList.get(index.getZeroBased());

        Set<Lesson> lessons = new TreeSet<>(personBeforeLessonEdit.getLessons());
        if (indexToEdit.getZeroBased() >= lessons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        /*
        Check if the edited lesson clashes with any existing lessons apart from the one to be
        edited.
         */
        List<Lesson> lessonList = lessons.stream().collect(Collectors.toList());
        Lesson toEdit = lessonList.get(indexToEdit.getZeroBased());
        Lesson editedLesson = createEditedLesson(toEdit, editLessonDescriptor);
        if (model.hasClashingLesson(editedLesson, toEdit)) {
            throw new CommandException(MESSAGE_CLASHING_LESSON);
        }

        personAfterLessonEdit = createEditedPerson(personBeforeLessonEdit, toEdit, editedLesson);

        model.setPerson(personBeforeLessonEdit, personAfterLessonEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, personAfterLessonEdit.getName(),
                toEdit, editedLesson), personAfterLessonEdit);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, Lesson toEdit, Lesson editedLesson) {
        assert personToEdit != null;

        Set<Lesson> updatedLessons = new TreeSet<>(personToEdit.getLessons().stream()
                .map(lesson -> lesson.equals(toEdit) ? editedLesson : lesson)
                .collect(Collectors.toSet()));

        return PersonUtil.createdEditedPerson(personToEdit, updatedLessons);
    }

    /**
     * Creates and returns a {@code Lesson} with the details of {@code lessonToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;

        Date updatedDate = editLessonDescriptor.getDate()
                .orElse(lessonToEdit.getStartDate());
        TimeRange updatedTimeRange = editLessonDescriptor.getTimeRange()
                .orElse(lessonToEdit.getTimeRange());
        Subject updatedSubject = editLessonDescriptor.getSubject()
                .orElse(lessonToEdit.getSubject());
        Set<Homework> updatedHomeworkSet = editLessonDescriptor.getHomeworkSet()
                .orElse(lessonToEdit.getHomework());
        LessonRates updatedRate = editLessonDescriptor.getRate().orElse(lessonToEdit.getLessonRates());

        return lessonToEdit.isRecurring()
                ? new RecurringLesson(updatedDate, updatedTimeRange, updatedSubject, updatedHomeworkSet, updatedRate)
                : new MakeUpLesson(updatedDate, updatedTimeRange, updatedSubject, updatedHomeworkSet, updatedRate);
    }

    @Override
    protected void undo() {
        requireNonNull(model);

        model.setPerson(personAfterLessonEdit, personBeforeLessonEdit);
    }

    @Override
    protected void redo() {
        requireNonNull(model);

        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError(MESSAGE_REDO_FAILURE);
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonEditCommand)) {
            return false;
        }

        // state check
        LessonEditCommand e = (LessonEditCommand) other;
        return index.equals(e.index)
            && indexToEdit.equals(indexToEdit)
            && editLessonDescriptor.equals(e.editLessonDescriptor);
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static class EditLessonDescriptor {

        private Date date;
        private TimeRange timeRange;
        private Subject subject;
        private Set<Homework> homeworkSet;
        private LessonRates rate;

        // Absence of isRecurring boolean field as changes to type of lesson is disallowed.

        public EditLessonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code homeworkSet} is used internally.
         */
        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            setDate(toCopy.date);
            setTimeRange(toCopy.timeRange);
            setSubject(toCopy.subject);
            setHomeworkSet(toCopy.homeworkSet);
            setRate(toCopy.rate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, timeRange, subject, homeworkSet, rate);
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<TimeRange> getTimeRange() {
            return Optional.ofNullable(timeRange);
        }

        public void setTimeRange(TimeRange timeRange) {
            this.timeRange = timeRange;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        /**
         * Sets {@code homeworkSet} to this object's {@code homeworkSet}.
         * A defensive copy of {@code homeworkSet} is used internally.
         */
        public Optional<Set<Homework>> getHomeworkSet() {
            return (homeworkSet != null)
                ? Optional.of(Collections.unmodifiableSet(homeworkSet))
                : Optional.empty();
        }

        /**
         * Returns an unmodifiable homework set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code homeworkSet} is null.
         */
        public void setHomeworkSet(Set<Homework> homeworkSet) {
            this.homeworkSet = (homeworkSet != null) ? new HashSet<>(homeworkSet) : null;
        }

        public Optional<LessonRates> getRate() {
            return Optional.ofNullable(rate);
        }

        public void setRate(LessonRates rate) {
            this.rate = rate;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            // state check
            EditLessonDescriptor e = (EditLessonDescriptor) other;

            return getDate().equals(e.getDate())
                && getTimeRange().equals(e.getTimeRange())
                && getSubject().equals(e.getSubject())
                && getHomeworkSet().equals(e.getHomeworkSet())
                && getRate().equals(e.getRate());
        }
    }
}
