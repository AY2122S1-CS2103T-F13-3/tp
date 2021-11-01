package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand.FindCondition;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Subject;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    // TODO: Tests for parsing fees
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_FEE = "$9,999.999";
    private static final String INVALID_TAG = "#friend";

    private static final String INVALID_TIME = "1200";
    private static final String INVALID_DATE = "32 Dec 2021";
    private static final String INVALID_SUBJECT = "^%123";
    private static final String INVALID_HOMEWORK = "this string contains more than 50 characters"
            + "and is far too long to be accepted as a homework description string";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_FEE = "999.99";
    private static final String VALID_REMARK = "She's gr3@t with algebra, bu+ trig0 needs work.";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_TIME = "12:00";
    private static final String VALID_DATE = "12 Dec 2021";
    private static final String VALID_SUBJECT = "Math";
    private static final String VALID_HOMEWORK_1 = "TYS Page 2";
    private static final String VALID_HOMEWORK_2 = "Textbook Page 204";

    private static final String VALID_KEYWORD_1 = "Amy";
    private static final String VALID_KEYWORD_2 = "tan";
    private static final String VALID_FIND_CONDITION = "any";
    private static final String INVALID_FIND_CONDITION = "every";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil
                .parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseFee_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFee((String) null));
    }

    @Test
    public void parseFee_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFee(INVALID_FEE));
    }

    @Test
    public void parseFee_validValueWithoutWhitespace_returnsFee() throws Exception {
        Fee expectedFee = new Fee(VALID_FEE);
        assertEquals(expectedFee, ParserUtil.parseFee(VALID_FEE));
    }

    @Test
    public void parseFee_validValueWithWhitespace_returnsTrimmedFee() throws Exception {
        String feeWithWhitespace = WHITESPACE + VALID_FEE + WHITESPACE;
        Fee expectedFee = new Fee(VALID_FEE);
        assertEquals(expectedFee, ParserUtil.parseFee(feeWithWhitespace));
    }

    @Test
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((String) null));
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsFee() {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK));
    }

    @Test
    public void parseRemark_validValueWithWhitespace_returnsTrimmedFee() {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(remarkWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseSubject_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject((String) null));
    }

    @Test
    public void parseSubject_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject(INVALID_SUBJECT));
    }

    @Test
    public void parseSubject_validValueWithoutWhitespace_returnsSubject() throws Exception {
        Subject expectedSubject = new Subject(VALID_SUBJECT);
        assertEquals(expectedSubject, ParserUtil.parseSubject(VALID_SUBJECT));
    }

    @Test
    public void parseSubject_validValueWithWhitespace_returnsTrimmedSubject() throws Exception {
        String subjectWithWhitespace = WHITESPACE + VALID_SUBJECT + WHITESPACE;
        Subject expectedSubject = new Subject(VALID_SUBJECT);
        assertEquals(expectedSubject, ParserUtil.parseSubject(subjectWithWhitespace));
    }

    @Test
    public void parseIndividualPieceOfHomework_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil
                .parseIndividualPieceOfHomework(null));
    }

    @Test
    public void parseIndividualPieceOfHomework_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseIndividualPieceOfHomework(INVALID_HOMEWORK));
    }

    @Test
    public void parseIndividualPieceOfHomework_validValueWithoutWhitespace_returnsHomework() throws Exception {
        Homework expectedHomework = new Homework(VALID_HOMEWORK_1);
        assertEquals(
                expectedHomework,
                ParserUtil.parseIndividualPieceOfHomework(VALID_HOMEWORK_1));
    }

    @Test
    public void parseIndividualPieceOfHomework_validValueWithWhitespace_returnsTrimmedHomework() throws Exception {
        String homeworkWithWhitespace = WHITESPACE + VALID_HOMEWORK_1 + WHITESPACE;
        Homework expectedHomework = new Homework(VALID_HOMEWORK_1);
        assertEquals(
                expectedHomework,
                ParserUtil.parseIndividualPieceOfHomework(homeworkWithWhitespace));
    }

    @Test
    public void parseHomeworkList_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHomeworkList(null));
    }

    @Test
    public void parseHomeworkList_collectionWithInvalidHomeworks_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseHomeworkList(Arrays.asList(VALID_HOMEWORK_1, INVALID_HOMEWORK)));
    }

    @Test
    public void parseHomeworkList_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseHomeworkList(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseHomeworkList_collectionWithValidHomeworks_returnsHomeworkSet() throws Exception {
        Set<Homework> actualHomeworkSet = ParserUtil.parseHomeworkList(
                Arrays.asList(VALID_HOMEWORK_1, VALID_HOMEWORK_2));
        Set<Homework> expectedHomeworkSet = new HashSet<Homework>(
                Arrays.asList(new Homework(VALID_HOMEWORK_1), new Homework(VALID_HOMEWORK_2)));

        assertEquals(expectedHomeworkSet, actualHomeworkSet);
    }


    @Test
    public void parseKeywords_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseKeywords((String) null));
    }

    @Test
    public void parseKeywords_validKeywordWithoutWhitespace_returnsKeywordStringList() throws Exception {
        assertEquals(Arrays.asList(VALID_KEYWORD_1), ParserUtil.parseKeywords(VALID_KEYWORD_1));
    }

    @Test
    public void parseKeywords_validKeywordWithWhitespace_returnsStrippedKeywordStringList() throws Exception {
        String keywordWithWhitespace = WHITESPACE + VALID_KEYWORD_1 + WHITESPACE;
        assertEquals(Arrays.asList(VALID_KEYWORD_1), ParserUtil.parseKeywords(keywordWithWhitespace));
    }

    @Test
    public void parseKeywords_invalidKeyword_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseKeywords(WHITESPACE));
    }

    @Test
    public void parseKeywords_validKeywords_returnsKeywordStringList() throws Exception {
        // split by whitespace
        assertEquals(
                Arrays.asList(VALID_KEYWORD_1, VALID_KEYWORD_2),
                ParserUtil.parseKeywords(VALID_KEYWORD_1 + WHITESPACE + VALID_KEYWORD_2));
    }

    @Test
    public void parseTagKeyword_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil
                .parseTagKeyword(null));
    }

    @Test
    public void parseTagKeyword_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseTagKeyword(WHITESPACE));
    }

    @Test
    public void parseTagKeyword_validKeywordWithoutWhitespace_returnsTagKeywordString() throws Exception {
        assertEquals(VALID_KEYWORD_1, ParserUtil.parseTagKeyword(VALID_KEYWORD_1));
    }

    @Test
    public void parseTagKeyword_validKeywordWithWhitespace_returnsStrippedTagKeywordString() throws Exception {
        String keywordWithWhitespace = WHITESPACE + VALID_KEYWORD_1 + WHITESPACE;
        assertEquals(VALID_KEYWORD_1, ParserUtil.parseTagKeyword(keywordWithWhitespace));
    }

    @Test
    public void parseTagKeywords_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTagKeywords(null));
    }

    @Test
    public void parseTagKeywords_collectionWithInvalidKeyword_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseTagKeywords(Arrays.asList(VALID_KEYWORD_1, WHITESPACE)));
    }

    @Test
    public void parseTagKeywords_emptyCollection_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseTagKeywords(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseFindCondition_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFindCondition(null));
    }

    @Test
    public void parseFindCondition_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFindCondition(INVALID_FIND_CONDITION));
    }

    @Test
    public void parseFindCondition_validValueWithoutWhitespace_returnsFindCondition() throws Exception {
        FindCondition expectedCondition = FindCondition.valueOfName(VALID_FIND_CONDITION);
        assertEquals(expectedCondition, ParserUtil.parseFindCondition(VALID_FIND_CONDITION));
    }

    @Test
    public void parseFindCondition_validValueWithWhitespace_returnsStrippedFindCondition() throws Exception {
        String conditionWithWhitespace = WHITESPACE + VALID_FIND_CONDITION + WHITESPACE;
        FindCondition expectedCondition = FindCondition.valueOfName(VALID_FIND_CONDITION);
        assertEquals(expectedCondition, ParserUtil.parseFindCondition(conditionWithWhitespace));
    }

}
