package seedu.address.model.util;

import java.util.Set;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.AcadLevel;
import seedu.address.model.person.AcadStream;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * Helper functions for handling edits to Person.
 */
public class PersonUtil {
    /**
     * Creates a person with the same details but updated lessons.
     *
     * @param personToEdit Person to edit lessons.
     * @param updatedLessons The updated lessons set.
     * @return Person that is edited.
     */
    public static Person createdEditedPerson(Person personToEdit, Set<Lesson> updatedLessons) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Phone updatedParentPhone = personToEdit.getParentPhone();
        Email updatedParentEmail = personToEdit.getParentEmail();
        Address updatedAddress = personToEdit.getAddress();
        School updatedSchool = personToEdit.getSchool();
        AcadStream updatedAcadStream = personToEdit.getAcadStream();
        AcadLevel updatedAcadLevel = personToEdit.getAcadLevel();
        Fee updatedOutstandingFee = personToEdit.getFee();
        Remark updatedRemark = personToEdit.getRemark();
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedParentPhone, updatedParentEmail,
                updatedAddress, updatedSchool, updatedAcadStream, updatedAcadLevel, updatedOutstandingFee,
                updatedRemark, updatedTags, updatedLessons);
    }
}
