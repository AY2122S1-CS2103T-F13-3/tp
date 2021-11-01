package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.Money;
import seedu.address.model.lesson.OutstandingFees;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonUtil;

/**
 * Pays the specific lesson a specified amount. Updated Outstanding Fees.
 */
public class PaidCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Pay Lesson Fees";

    public static final String COMMAND_WORD = "paid";

    public static final String COMMAND_PARAMETERS = "INDEX "
            + "LESSON_INDEX "
            + PREFIX_PAID_AMOUNT + "AMOUNT_PAID";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " 1 " + "1 " + PREFIX_PAID_AMOUNT + "150.00";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pays the specified amount to the lesson "
            + "identified by lesson index of the student identified by the"
            + " index number used in the displayed student list.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_PAID_LESSON_SUCCESS = "Paid for %1$s's lesson:\n%2$s";

    private final Index index;
    private final Index indexToEdit;
    private final Money payment;
    private Person personBeforeLessonPaid;
    private Person personAfterLessonPaid;

    /**
     * @param index of the person in the filtered person list to pay.
     * @param indexToEdit to edit.
     * @param payment amount to the lesson.
     */
    public PaidCommand(Index index, Index indexToEdit, Money payment) {
        requireAllNonNull(index, indexToEdit, payment);

        this.index = index;
        this.indexToEdit = indexToEdit;
        this.payment = payment;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        personBeforeLessonPaid = lastShownList.get(index.getZeroBased());

        Set<Lesson> lessons = new TreeSet<>(personBeforeLessonPaid.getLessons());
        if (indexToEdit.getZeroBased() >= lessons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }


        List<Lesson> lessonList = new ArrayList<>(lessons);
        Lesson toPay = lessonList.get(indexToEdit.getZeroBased());
        Lesson paidLesson = createEditedLesson(toPay, payment);

        personAfterLessonPaid = createEditedPerson(personBeforeLessonPaid, toPay, paidLesson);

        model.setPerson(personBeforeLessonPaid, personAfterLessonPaid);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_PAID_LESSON_SUCCESS, personAfterLessonPaid.getName(),
                toPay, paidLesson), personAfterLessonPaid);
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
    private static Lesson createEditedLesson(Lesson lessonToEdit, Money payment) throws CommandException {
        assert lessonToEdit != null;

        Date copiedDate = lessonToEdit.getStartDate();
        Date copiedEndDate = lessonToEdit.getEndDate();
        TimeRange copiedTimeRange = lessonToEdit.getTimeRange();
        Subject copiedSubject = lessonToEdit.getSubject();
        Set<Homework> copiedHomeworkSet = lessonToEdit.getHomework();
        LessonRates copiedLessonRates = lessonToEdit.getLessonRates();
        Set<Date> copiedCancelledDates = lessonToEdit.getCancelledDates();

        OutstandingFees updatedOutstandingFees;
        try {
            updatedOutstandingFees = lessonToEdit.getOutstandingFees().pay(payment);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return lessonToEdit.isRecurring()
                ? new RecurringLesson(copiedDate, copiedEndDate, copiedTimeRange, copiedSubject, copiedHomeworkSet,
                copiedLessonRates, updatedOutstandingFees, copiedCancelledDates)
                : new MakeUpLesson(copiedDate, copiedTimeRange, copiedSubject, copiedHomeworkSet,
                copiedLessonRates, updatedOutstandingFees, copiedCancelledDates);
    }

    @Override
    protected void undo() {
        requireNonNull(model);

        model.setPerson(personAfterLessonPaid, personBeforeLessonPaid);
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
        if (!(other instanceof PaidCommand)) {
            return false;
        }

        // state check
        PaidCommand e = (PaidCommand) other;
        return index.equals(e.index)
                && indexToEdit.equals(indexToEdit)
                && payment.equals(e.payment);
    }
}
