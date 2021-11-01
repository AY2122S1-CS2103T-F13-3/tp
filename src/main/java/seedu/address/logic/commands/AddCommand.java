package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_STREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_ACTION = "Add Student";

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_PARAMETERS = PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_PARENT_PHONE + "PARENT_PHONE] "
            + "[" + PREFIX_PARENT_EMAIL + "PARENT_EMAIL] "
            + "[" + PREFIX_SCHOOL + "SCHOOL] "
            + "[" + PREFIX_ACAD_STREAM + "ACAD_STREAM] "
            + "[" + PREFIX_ACAD_LEVEL + "ACAD_LEVEL] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + COMMAND_PARAMETERS;

    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ADDRESS + "311 Dover Rd, #02-25 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@gmail.com "
            + PREFIX_PARENT_PHONE + "91234567 "
            + PREFIX_PARENT_EMAIL + "jackd@gmail.com "
            + PREFIX_SCHOOL + "SJI "
            + PREFIX_ACAD_STREAM + "Express "
            + PREFIX_ACAD_LEVEL + "S1 "
            + PREFIX_REMARK + "weak at trigo "
            + PREFIX_TAG + "retained "
            + PREFIX_TAG + "new";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to TAB.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Note: at least one contact field must be present and not empty. \n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String USER_TIP = "Try adding a student using: \n"
            + COMMAND_WORD + " " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in TAB!";

    private final Person toAdd; //the person to be added, should not be modified in execution of command

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * To undo add student, delete added student
     */
    @Override
    protected void undo() {
        requireNonNull(model);

        model.deletePerson(toAdd);
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
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return "Add Command: " + toAdd;
    }
}
