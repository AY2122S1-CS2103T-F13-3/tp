package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Homework;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonRates;
import seedu.address.model.lesson.MakeUpLesson;
import seedu.address.model.lesson.OutstandingFees;
import seedu.address.model.lesson.RecurringLesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.TimeRange;
import seedu.address.model.person.AcadLevel;
import seedu.address.model.person.AcadStream;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Phone EMPTY_PHONE = new Phone("");
    public static final Email EMPTY_EMAIL = new Email("");
    public static final School EMPTY_SCHOOL = new School("");
    public static final AcadStream EMPTY_ACAD_STREAM = new AcadStream("");
    public static final AcadLevel EMPTY_ACAD_LEVEL = new AcadLevel("");
    public static final Remark EMPTY_REMARK = new Remark("");
    public static final TreeSet<Lesson> EMPTY_LESSON_SET = new TreeSet<>();
    public static final Set<Date> EMPTY_CANCELLED_DATES_SET = new HashSet<>();

    public static Lesson[] getSampleLessons() {
        return new Lesson[] {
            new RecurringLesson(
                    getDate(LocalDate.now().withDayOfMonth(3)),
                    getDate(LocalDate.MAX),
                    new TimeRange("1630-1830"),
                    new Subject("Science"),
                    getHomeworkSet("Ex3", "ch4"),
                    new LessonRates("50.00"),
                    new OutstandingFees("100.00"),
                    Set.of(getDate(LocalDate.now().withDayOfMonth(10)))),
            new MakeUpLesson(
                    getDate(LocalDate.now().withDayOfMonth(12)),
                    new TimeRange("1700-1900"),
                    new Subject("Science"),
                    getHomeworkSet("As1"),
                    new LessonRates("50.00"),
                    new OutstandingFees("100.00"),
                    EMPTY_CANCELLED_DATES_SET),
            new RecurringLesson(
                    getDate(LocalDate.now().withDayOfMonth(3).minusWeeks(4)),
                    getDate(LocalDate.MAX),
                    new TimeRange("1930-2130"),
                    new Subject("Math"),
                    getHomeworkSet("pg24"),
                    new LessonRates("60.00"),
                    new OutstandingFees("100.00"),
                    EMPTY_CANCELLED_DATES_SET),
            new RecurringLesson(
                    getDate(LocalDate.now().withDayOfMonth(6).minusWeeks(4)),
                    getDate(LocalDate.MAX),
                    new TimeRange("1500-1800"),
                    new Subject("Math"),
                    getHomeworkSet("workbook 2"),
                    new LessonRates("60.00"),
                    new OutstandingFees("100.00"),
                    EMPTY_CANCELLED_DATES_SET),
            new RecurringLesson(
                    getDate(LocalDate.now().withDayOfMonth(8).minusWeeks(4)),
                    getDate(LocalDate.MAX),
                    new TimeRange("1200-1400"),
                    new Subject("Math"),
                    getHomeworkSet("Ch7 pg10"),
                    new LessonRates("60.00"),
                    new OutstandingFees("100.00"),
                    EMPTY_CANCELLED_DATES_SET)
        };
    }

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    EMPTY_SCHOOL, EMPTY_ACAD_STREAM, EMPTY_ACAD_LEVEL,
                    EMPTY_REMARK, getTagSet("unpaid"),
                    getLessonSet(getSampleLessons()[0], getSampleLessons()[1])),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    EMPTY_PHONE, EMPTY_EMAIL,
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    EMPTY_SCHOOL, EMPTY_ACAD_STREAM, EMPTY_ACAD_LEVEL,
                    EMPTY_REMARK, getTagSet("forgetful", "unpaid"),
                    getLessonSet(getSampleLessons()[2])),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    EMPTY_PHONE, new Email("olivealice@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new School("NYJC"), EMPTY_ACAD_STREAM, EMPTY_ACAD_LEVEL,
                    EMPTY_REMARK, getTagSet("zoom"),
                    getLessonSet(getSampleLessons()[3])),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Phone("91009182"), EMPTY_EMAIL,
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    EMPTY_SCHOOL, EMPTY_ACAD_STREAM, EMPTY_ACAD_LEVEL,
                    new Remark("Weak at trigo"), getTagSet("zoom"),
                    getLessonSet(getSampleLessons()[4])),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    EMPTY_PHONE, EMPTY_EMAIL,
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    EMPTY_SCHOOL, new AcadStream("NA"), new AcadLevel("s5"),
                    EMPTY_REMARK, getTagSet("new"), EMPTY_LESSON_SET),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    EMPTY_PHONE, EMPTY_EMAIL,
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new School("DHS"), new AcadStream("IP"), new AcadLevel("Y3"),
                    EMPTY_REMARK, getTagSet("forgetful"), EMPTY_LESSON_SET)
        };
    }


    public static Date getDate(LocalDate localDate) {
        return new Date(localDate.format(Date.FORMATTER));
    }

    public static Set<Lesson> getLessonSet(Lesson... lessons) {
        return Arrays.stream(lessons)
                .collect(Collectors.toSet());
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a homework set containing the list of strings given.
     */
    public static Set<Homework> getHomeworkSet(String... strings) {
        return Arrays.stream(strings)
            .map(Homework::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a cancelled date set containing the list of strings given.
     */
    public static Set<Date> getCancelledDateSet(String... strings) {
        return Arrays.stream(strings)
                .map(Date::new)
                .collect(Collectors.toSet());
    }
}
