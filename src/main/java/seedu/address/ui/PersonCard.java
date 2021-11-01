package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.AcadLevel;
import seedu.address.model.person.AcadStream;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phonePlaceholder;
    @FXML
    private Label phone;
    @FXML
    private Label emailPlaceholder;
    @FXML
    private Label email;
    @FXML
    private Label ppPlaceholder;
    @FXML
    private Label parentPhone;
    @FXML
    private Label pePlaceholder;
    @FXML
    private Label parentEmail;
    @FXML
    private Label address;
    @FXML
    private Label schPlaceholder;
    @FXML
    private Label school;
    @FXML
    private Label streamPlaceholder;
    @FXML
    private Label acadStream;
    @FXML
    private Label lvlPlaceholder;
    @FXML
    private Label acadLevel;
    @FXML
    private Label outstandingFees;
    @FXML
    private Label remarkPlaceholder;
    @FXML
    private Label remark;
    @FXML
    private VBox tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        address.setText(person.getAddress().value);

        setPhoneField(phonePlaceholder, person.getPhone(), phone);
        setEmailField(emailPlaceholder, person.getEmail(), email);
        setPhoneField(ppPlaceholder, person.getParentPhone(), parentPhone);
        setEmailField(pePlaceholder, person.getParentEmail(), parentEmail);
        setSchoolField(person.getSchool());
        setAcadStreamField(person.getAcadStream());
        setAcadLevelField(person.getAcadLevel());
        outstandingFees.setText(person.getOutstandingFees().toString());
        setRemarkField(person.getRemark());
        setTagsField(person.getTags());
    }

    private void setPhoneField(Label phonePlaceholder, Phone phoneData, Label phoneLabel) {
        phonePlaceholder.setManaged(!phoneData.isEmpty());
        phoneLabel.setManaged(!phoneData.isEmpty());
        phoneLabel.setText(phoneData.value);
    }

    private void setEmailField(Label emailPlaceholder, Email emailData, Label emailLabel) {
        emailPlaceholder.setManaged(!emailData.isEmpty());
        emailLabel.setManaged(!emailData.isEmpty());
        emailLabel.setText(emailData.value);
    }

    private void setSchoolField(School schoolData) {
        schPlaceholder.setManaged(!schoolData.isEmpty());
        school.setManaged(!schoolData.isEmpty());
        school.setText(schoolData.value);
    }

    private void setAcadStreamField(AcadStream acadStreamData) {
        streamPlaceholder.setManaged(!acadStreamData.isEmpty());
        acadStream.setManaged(!acadStreamData.isEmpty());
        acadStream.setText(acadStreamData.value);
    }

    private void setAcadLevelField(AcadLevel acadLevelData) {
        acadLevel.setManaged(!acadLevelData.isEmpty());
        lvlPlaceholder.setManaged(!acadLevelData.isEmpty());
        acadLevel.setText(acadLevelData.value);
    }

    private void setRemarkField(Remark remarkData) {
        remarkPlaceholder.setManaged(!remarkData.isEmpty());
        remark.setManaged(!remarkData.isEmpty());
        remark.setText(remarkData.value);
    }

    private void setTagsField(Set<Tag> tagSet) {
        tags.setManaged(!person.getTags().isEmpty());
        tagSet.stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .forEach(tag -> tags.getChildren().add(createTagLabel(tag.getTagName())));
    }

    private Label createTagLabel(String tag) {
        Label label = new Label(tag);
        label.setWrapText(true);
        return label;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
