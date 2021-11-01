package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_STREAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACAD_STREAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FEE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FEE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FORGETFUL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ZOOM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    // all fields present
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withParentPhone("93251352")
            .withParentEmail("papapauline@example.com")
            .withSchool("RI")
            .withAcadStream("IP")
            .withAcadLevel("Y6")
            .withFee("")
            .withRemark("She likes Cheesecake")
            .withTags("forgetful").build();

    // all fields present with multiple tags
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withParentPhone("94328765").withParentEmail("ben@example.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withSchool("HCI").withAcadStream("IP").withAcadLevel("Y3")
            .withFee("99.99").withRemark("He likes chocolate ice cream")
            .withTags("unpaid", "forgetful").build();

    // some optional fields missing
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com")
            .withAddress("wall street")
            .withSchool("ACSI").withAcadStream("IB").withAcadLevel("Y1")
            .withRemark("He likes french fries").build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withSchool("CCHM")
            .withTags("forgetful").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94822244")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withAcadStream("IELTS").build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94824278")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withAcadLevel("S5").build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94824422")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withAcadLevel("J2")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84842424")
            .withEmail("stefan@example.com")
            .withAddress("little india").build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84832131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withParentPhone(VALID_PARENT_PHONE_AMY)
            .withParentEmail(VALID_PARENT_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withSchool(VALID_SCHOOL_AMY)
            .withAcadStream(VALID_ACAD_STREAM_AMY)
            .withAcadLevel(VALID_ACAD_LEVEL_AMY)
            .withFee(VALID_FEE_AMY)
            .withRemark(VALID_REMARK_AMY)
            .withTags(VALID_TAG_FORGETFUL).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withParentPhone(VALID_PARENT_PHONE_BOB)
            .withParentEmail(VALID_PARENT_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withSchool(VALID_SCHOOL_BOB)
            .withAcadStream(VALID_ACAD_STREAM_BOB)
            .withAcadLevel(VALID_ACAD_LEVEL_BOB)
            .withFee(VALID_FEE_BOB)
            .withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_ZOOM, VALID_TAG_FORGETFUL).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER in name

    public static final String KEYWORD_MATCHING_AVE = "Ave"; // A keyword that matches AVE in address

    public static final String KEYWORD_MATCHING_FORGETFUL = "Forgetful"; // A keyword that matches the tag FORGETFUL

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
