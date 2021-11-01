package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANCEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNCANCEL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CommandUtil;
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
 * Edits the details of an existing lesson in the address book.
 */
public class LessonEditCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Edit Lesson";

    public static final String COMMAND_WORD = "ledit";

    public static final String COMMAND_PARAMETERS = "INDEX (must be a positive integer) "
            + "LESSON_INDEX (must be a positive integer)\n"
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "HHmm-HHmm] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_RATES + "RATE] "
            + "[" + PREFIX_HOMEWORK + "HOMEWORK]... "
            + "[" + PREFIX_CANCEL + "CANCEL_DATE]... "
            + "[" + PREFIX_UNCANCEL + "UNCANCEL_DATE]...";

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " 1 1 "
            + PREFIX_HOMEWORK + "Textbook Pg2 "
            + PREFIX_SUBJECT + "Biology "
            + PREFIX_CANCEL + "5 oct 2021 "
            + PREFIX_UNCANCEL + "12 oct 2021";


    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the lesson identified by lesson index"
            + " of the student identified by the index number used in the displayed student list.\n"
            + "Besides cancelled lessons, existing values of other fields will be overwritten by the input values.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited lesson for student %1$s:\n%2$s\nto %3$s";
    public static final String MESSAGE_CLASHING_LESSON = "This edit will result in clashes with an existing lesson.";
    public static final String MESSAGE_NOT_EDITED = "You must be provide at least one field to edit!";
    public static final String MESSAGE_ATTEMPT_TO_EDIT_TYPE =
            "You cannot edit the type of a lesson. Please add another" + " lesson if you wish to do so.";

    public static final String MESSAGE_INVALID_CANCEL_DATE =
            "Failed to cancel lesson! This lesson does not occur on %1$s.";
    public static final String MESSAGE_INVALID_UNCANCEL_DATE =
            "Failed to uncancel lesson! This lesson does not have a cancelled lesson on %1$s.";


    private final Index index;
    private final Index lessonIndex;
    private final EditLessonDescriptor editLessonDescriptor;
    private Person personBeforeLessonEdit;
    private Person personAfterLessonEdit;

    /**
     * Creates a LessonEditCommand to edit the lesson at the specified lesson index.
     *
     * @param index of the person in the filtered person list to edit.
     * @param lessonIndex to edit.
     */
    public LessonEditCommand(Index index, Index lessonIndex, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(lessonIndex);

        this.index = index;
        this.lessonIndex = lessonIndex;
        this.editLessonDescriptor = editLessonDescriptor;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        personBeforeLessonEdit = CommandUtil.getPerson(lastShownList, index);

        List<Lesson> lessonList = personBeforeLessonEdit.getLessons().stream().sorted().collect(Collectors.toList());
        Lesson lessonToEdit = CommandUtil.getLesson(lessonList, lessonIndex);
        Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

        Set<Lesson> updatedLessons = createUpdatedLessons(lessonList, editedLesson, lessonToEdit);
        personAfterLessonEdit = PersonUtil.createdEditedPerson(personBeforeLessonEdit, updatedLessons);

        model.setPerson(personBeforeLessonEdit, personAfterLessonEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(
                String.format(MESSAGE_EDIT_LESSON_SUCCESS, personAfterLessonEdit.getName(), lessonToEdit, editedLesson),
                personAfterLessonEdit);
    }

    /**
     * Creates and returns a {@code Lesson} with the details of {@code lessonToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor)
            throws CommandException {
        assert lessonToEdit != null;
        Date updatedDate = editLessonDescriptor.getDate().orElse(lessonToEdit.getStartDate());
        TimeRange updatedTimeRange = editLessonDescriptor.getTimeRange().orElse(lessonToEdit.getTimeRange());
        Subject updatedSubject = editLessonDescriptor.getSubject().orElse(lessonToEdit.getSubject());
        Set<Homework> updatedHomeworkSet = editLessonDescriptor.getHomeworkSet().orElse(lessonToEdit.getHomework());
        LessonRates updatedRate = editLessonDescriptor.getRate().orElse(lessonToEdit.getLessonRates());
        // filter out dates after start date
        Set<Date> filteredCancelledDates = lessonToEdit.getCancelledDates().stream()
                .filter(date -> date.isOnRecurringDate(updatedDate)).collect(Collectors.toSet());

        // lesson before handling cancel and uncancel of dates
        Lesson lessonBeforeUpdateCancelledDates = lessonToEdit.isRecurring()
                ? new RecurringLesson(updatedDate, updatedTimeRange, updatedSubject, updatedHomeworkSet, updatedRate,
                        filteredCancelledDates)
                : new MakeUpLesson(updatedDate, updatedTimeRange, updatedSubject, updatedHomeworkSet, updatedRate,
                        filteredCancelledDates);

        if (!editLessonDescriptor.isCancelledDatesEdited()) {
            return lessonBeforeUpdateCancelledDates;
        }
        Set<Date> datesToCancel = new HashSet<>(editLessonDescriptor.getCancelDates().orElse(new HashSet<>()));
        Set<Date> datesToUncancel = new HashSet<>(editLessonDescriptor.getUncancelDates().orElse(new HashSet<>()));

        Set<Date> updatedCancelledDates = createUpdatedCancelledDates(lessonBeforeUpdateCancelledDates, datesToCancel,
                datesToUncancel);
        return lessonBeforeUpdateCancelledDates.updateCancelledDates(updatedCancelledDates);
    }

    /**
     * Creates and returns a set of cancelled dates edited with {@code datesToCancel} and {@code datesToUncancel}.
     *
     * @param lesson Lesson with the cancelled dates.
     * @param datesToCancel A set of lesson dates to add to cancelled dates.
     * @param datesToUncancel A set of lesson dates to remove from cancelled dates.
     * @return A set of updated cancelled dates.
     * @throws CommandException
     */
    private static Set<Date> createUpdatedCancelledDates(Lesson lesson, Set<Date> datesToCancel,
                                                         Set<Date> datesToUncancel) throws CommandException {
        assert lesson != null;
        Set<Date> updatedCancelledDates = new HashSet<>(lesson.getCancelledDates());

        // remove dates that appear in both cancelled and uncancelled input
        Set<Date> duplicates = new HashSet<>(datesToCancel);
        duplicates.retainAll(datesToUncancel);
        datesToCancel.removeAll(duplicates);
        datesToUncancel.removeAll(duplicates);

        validateCancelDates(lesson, datesToCancel);
        validateUncancelDates(updatedCancelledDates, datesToUncancel);
        updatedCancelledDates.addAll(datesToCancel);
        updatedCancelledDates.removeAll(datesToUncancel);

        return updatedCancelledDates;
    }


    /**
     * Checks if {@code datesToCancel} are valid lesson dates.
     *
     * @param lesson Lesson to cancel.
     * @param datesToCancel A set of dates to cancel.
     * @throws CommandException If the date to cancel is invalid.
     */
    private static void validateCancelDates(Lesson lesson, Set<Date> datesToCancel) throws CommandException {
        assert lesson != null;
        for (Date date : datesToCancel) {
            // check if date is a lesson date
            if (!lesson.hasLessonOnDate(date)) {
                throw new CommandException(String.format(MESSAGE_INVALID_CANCEL_DATE, date));
            }
        }
    }

    /**
     * Checks if {@code datesToUncancel} are valid cancelled lesson dates.
     *
     * @param cancelledDates A set of cancelled dates.
     * @param datesToUncancel A set of dates to remove from cancelled dates.
     * @throws CommandException If the date to uncancel is invalid.
     */
    private static void validateUncancelDates(Set<Date> cancelledDates, Set<Date> datesToUncancel)
            throws CommandException {
        for (Date date : datesToUncancel) {
            // check if date is a cancelled date
            if (!cancelledDates.contains(date)) {
                throw new CommandException(String.format(MESSAGE_INVALID_UNCANCEL_DATE, date));
            }
        }
    }

    /**
     * Replaces lesson {@code toEdit} with lesson {@code edited} in {@code lessonList}.
     *
     * @param lessonList A list of lessons to update.
     * @param edited The edited lesson.
     * @param toEdit The original lesson to be edited.
     * @return A set of updated lessons with the lesson edited.
     * @throws CommandException If the edited lesson results in a clash.
     */
    private Set<Lesson> createUpdatedLessons(List<Lesson> lessonList, Lesson edited, Lesson toEdit)
            throws CommandException {
        // Checks if the edited lesson clashes with any existing lessons apart from the one to be edited.
        if (model.hasClashingLesson(edited, toEdit)) {
            throw new CommandException(MESSAGE_CLASHING_LESSON);
        }
        lessonList.remove(toEdit);
        lessonList.add(edited);
        Set<Lesson> updatedLessonSet = new TreeSet<>(lessonList);

        return updatedLessonSet;
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
                && lessonIndex.equals(e.lessonIndex)
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
        private Set<Date> cancelDates;
        private Set<Date> uncancelDates;


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
            setCancelDates(toCopy.cancelDates);
            setUncancelDates(toCopy.uncancelDates);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, timeRange, subject, homeworkSet, rate, cancelDates,
                    uncancelDates);
        }

        public boolean isCancelledDatesEdited() {
            return CollectionUtil.isAnyNonNull(cancelDates, uncancelDates);
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

        public Optional<Set<Date>> getCancelDates() {
            return (cancelDates != null)
                    ? Optional.of(Collections.unmodifiableSet(cancelDates))
                    : Optional.empty();
        }

        public void setCancelDates(Set<Date> cancelDates) {
            this.cancelDates = (cancelDates != null) ? new HashSet<>(cancelDates) : null;
        }

        public Optional<Set<Date>> getUncancelDates() {
            return (uncancelDates != null)
                    ? Optional.of(Collections.unmodifiableSet(uncancelDates))
                    : Optional.empty();
        }

        public void setUncancelDates(Set<Date> uncancelDates) {
            this.uncancelDates = (uncancelDates != null) ? new HashSet<>(uncancelDates) : null;
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
                    && getRate().equals(e.getRate())
                    && getUncancelDates().equals(e.getUncancelDates())
                    && getCancelDates().equals(e.getCancelDates());
        }
    }
}
