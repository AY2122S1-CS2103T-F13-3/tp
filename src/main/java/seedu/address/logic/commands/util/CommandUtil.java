package seedu.address.logic.commands.util;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Helper functions for commands.
 */
public class CommandUtil {
    /**
     * Returns the {@code Person} at the specified index in a {@code List<Person>}.
     * Throws a CommandException if the index is invalid.
     *
     * @param personList List of persons.
     * @param personIndex Index of person in the list.
     * @return Person at the specified index in a list.
     * @throws CommandException
     */
    public static Person getPerson(List<Person> personList, Index personIndex) throws CommandException {
        if (personIndex.getZeroBased() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        return personList.get(personIndex.getZeroBased());
    }

    /**
     * Returns the {@code Lesson} at the specified index in a {@code List<Lesson>}.
     * Throws a CommandException if the index is invalid.
     *
     * @param lessonList List of lessons.
     * @param lessonIndex Index of lesson in the list.
     * @return Lesson at the specified index in a list.
     * @throws CommandException
     */
    public static Lesson getLesson(List<Lesson> lessonList, Index lessonIndex) throws CommandException {
        if (lessonIndex.getZeroBased() >= lessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        return lessonList.get(lessonIndex.getZeroBased());
    }
}
