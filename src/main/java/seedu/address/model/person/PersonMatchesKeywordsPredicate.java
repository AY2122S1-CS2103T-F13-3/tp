package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand.FindCondition;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.TimeRange;

/**
 * Tests that a {@code Person}'s fields matches the keywords given.
 */
public class PersonMatchesKeywordsPredicate implements Predicate<Person> {
    private List<String> nameKeywords;
    private List<String> phoneKeywords;
    private List<String> emailKeywords;
    private List<String> parentPhoneKeywords;
    private List<String> parentEmailKeywords;
    private List<String> addressKeywords;
    private List<String> schoolKeywords;
    private List<String> acadStreamKeywords;
    private List<String> acadLevelKeywords;
    private List<String> remarkKeywords;
    private List<String> tagKeywords;

    // Lesson Keywords
    private Date date;
    private Date cancelledDate;
    private TimeRange timeRange;
    private List<String> subjectKeywords;
    private List<String> ratesKeywords;
    private List<String> homeworkKeywords;

    private FindCondition condition = FindCondition.ALL; // default find condition is match all

    /**
     * Returns true if at least one field is searched.
     *
     * @return True if at least one field is searched.
     */
    public boolean isAnyFieldSearched() {
        return CollectionUtil.isAnyNonNull(nameKeywords, phoneKeywords, emailKeywords,
                parentPhoneKeywords, parentEmailKeywords, addressKeywords,
                schoolKeywords, acadStreamKeywords, acadLevelKeywords, remarkKeywords,
                timeRange, date, cancelledDate, subjectKeywords, ratesKeywords, homeworkKeywords,
                tagKeywords);
    }

    /**
     * Returns the find condition.
     */
    public FindCondition getCondition() {
        return condition;
    }

    /**
     * Sets find condition.
     *
     * @param condition Find condition
     */
    public void setCondition(FindCondition condition) {
        this.condition = condition;
    }

    /**
     * Returns optional name keywords.
     */
    public Optional<List<String>> getNameKeywords() {
        return Optional.ofNullable(nameKeywords);
    }

    /**
     * Sets keywords to match with a Person's name.
     *
     * @param keywords Name keywords to find.
     */
    public void setNameKeywords(List<String> keywords) {
        nameKeywords = keywords;
    }

    /**
     * Returns optional phone keywords.
     */
    public Optional<List<String>> getPhoneKeywords() {
        return Optional.ofNullable(phoneKeywords);
    }

    /**
     * Sets keywords to match with a Person's phone number.
     *
     * @param keywords Phone keywords to find.
     */
    public void setPhoneKeywords(List<String> keywords) {
        phoneKeywords = keywords;
    }

    /**
     * Returns optional email keywords.
     */
    public Optional<List<String>> getEmailKeywords() {
        return Optional.ofNullable(emailKeywords);
    }

    /**
     * Sets keywords to match with a Person's email.
     *
     * @param keywords Email keywords to find.
     */
    public void setEmailKeywords(List<String> keywords) {
        emailKeywords = keywords;
    }

    /**
     * Returns optional parent phone keywords.
     */
    public Optional<List<String>> getParentPhoneKeywords() {
        return Optional.ofNullable(parentPhoneKeywords);
    }

    /**
     * Sets keywords to match with a Person's parent phone number.
     *
     * @param keywords Parent phone keywords to find.
     */
    public void setParentPhoneKeywords(List<String> keywords) {
        parentPhoneKeywords = keywords;
    }

    /**
     * Returns optional parent email keywords.
     */
    public Optional<List<String>> getParentEmailKeywords() {
        return Optional.ofNullable(parentEmailKeywords);
    }

    /**
     * Sets keywords to match with a Person's parent email.
     *
     * @param keywords Parent email keywords to find.
     */
    public void setParentEmailKeywords(List<String> keywords) {
        parentEmailKeywords = keywords;
    }

    /**
     * Returns optional address keywords.
     */
    public Optional<List<String>> getAddressKeywords() {
        return Optional.ofNullable(addressKeywords);
    }

    /**
     * Sets keywords to match with a Person's address.
     *
     * @param keywords Address keywords to find.
     */
    public void setAddressKeywords(List<String> keywords) {
        addressKeywords = keywords;
    }

    /**
     * Returns optional school keywords.
     */
    public Optional<List<String>> getSchoolKeywords() {
        return Optional.ofNullable(schoolKeywords);
    }

    /**
     * Sets keywords to match with a Person's school.
     *
     * @param keywords School keywords to find.
     */
    public void setSchoolKeywords(List<String> keywords) {
        schoolKeywords = keywords;
    }

    /**
     * Returns optional academic stream keywords.
     */
    public Optional<List<String>> getAcadStreamKeywords() {
        return Optional.ofNullable(acadStreamKeywords);
    }

    /**
     * Sets keywords to match with a Person's academic stream.
     *
     * @param keywords Academic stream keywords to find.
     */
    public void setAcadStreamKeywords(List<String> keywords) {
        acadStreamKeywords = keywords;
    }

    /**
     * Returns optional academic level keywords.
     */
    public Optional<List<String>> getAcadLevelKeywords() {
        return Optional.ofNullable(acadLevelKeywords);
    }

    /**
     * Sets keywords to match with a Person's academic level.
     *
     * @param keywords Academic level keywords to find.
     */
    public void setAcadLevelKeywords(List<String> keywords) {
        acadLevelKeywords = keywords;
    }

    /**
     * Returns optional remark level keywords.
     */
    public Optional<List<String>> getRemarkKeywords() {
        return Optional.ofNullable(remarkKeywords);
    }

    /**
     * Sets keywords to match with a Person's remarks.
     *
     * @param keywords Remark keywords to find.
     */
    public void setRemarkKeywords(List<String> keywords) {
        this.remarkKeywords = keywords;
    }

    /**
     * Sets keywords to match with a Person's Lesson's time range.
     *
     * @param timeRange TimeRange to find.
     */
    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * Returns optional time range.
     */
    public Optional<TimeRange> getTimeRange() {
        return Optional.ofNullable(timeRange);
    }

    /**
     * Sets keywords to match with a Person's Lesson's date.
     *
     * @param date Valid date to find.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns optional date.
     */
    public Optional<Date> getDate() {
        return Optional.ofNullable(date);
    }

    /**
     * Sets keywords to match with a Person's Lesson's cancelled date.
     *
     * @param cancelledDate Valid cancelled date to find.
     */
    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    /**
     * Returns optional cancelledDate.
     */
    public Optional<Date> getCancelledDate() {
        return Optional.ofNullable(cancelledDate);
    }

    /**
     * Sets keywords to match with a Person's Lesson's subject.
     *
     * @param keywords Subject keywords to find.
     */
    public void setSubjectKeywords(List<String> keywords) {
        subjectKeywords = keywords;
    }

    /**
     * Returns optional subject keywords.
     */
    public Optional<List<String>> getSubjectKeywords() {
        return Optional.ofNullable(subjectKeywords);
    }

    /**
     * Sets keywords to match with a Person's Lesson's rates.
     *
     * @param keywords Rate keywords to find.
     */
    public void setRatesKeywords(List<String> keywords) {
        ratesKeywords = keywords;
    }

    /**
     * Returns optional rate keywords.
     */
    public Optional<List<String>> getRateKeywords() {
        return Optional.ofNullable(ratesKeywords);
    }

    /**
     * Sets keywords to match with a Person's Lesson's homework.
     *
     * @param keywords homework keywords to find.
     */
    public void setHomeworkKeywords(List<String> keywords) {
        homeworkKeywords = keywords;
    }

    /**
     * Returns optional homework keywords.
     */
    public Optional<List<String>> getHomeworkKeywords() {
        return Optional.ofNullable(homeworkKeywords);
    }

    /**
     * Returns optional tag keywords.
     */
    public Optional<List<String>> getTagKeywords() {
        return Optional.ofNullable(tagKeywords);
    }

    /**
     * Sets tag keywords to match with a Person's tags.
     *
     * @param keywords Phone keywords to find.
     */
    public void setTagKeywords(List<String> keywords) {
        this.tagKeywords = keywords;
    }

    /**
     * Returns true if field matches any of the keywords given.
     * There is a match if a keyword is a substring of the field.
     *
     * @param keywords Keywords to find.
     * @param field    Person's field to match with keywords.
     * @return True if field matches keywords.
     */
    private boolean isMatch(List<String> keywords, String field) {
        requireAllNonNull(keywords, field);
        return keywords.stream().allMatch(keyword -> StringUtil.containsSubstringIgnoreCase(field, keyword));
    }

    // Person Predicates -----------------------------------------------------------------------------------------------
    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code Name} matches the keywords given.
     *
     * @return A predicate that tests a person's name.
     */
    private Predicate<Person> getNameMatchPredicate() {
        return person -> isMatch(nameKeywords, person.getName().fullName);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code Phone} matches the keywords given.
     *
     * @return A predicate that tests a person's phone number.
     */
    private Predicate<Person> getPhoneMatchPredicate() {
        return person -> isMatch(phoneKeywords, person.getPhone().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code Email} matches the keywords given.
     *
     * @return A predicate that tests a person's email.
     */
    private Predicate<Person> getEmailMatchPredicate() {
        return person -> isMatch(emailKeywords, person.getEmail().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s parent {@code Phone} matches the keywords given.
     *
     * @return A predicate that tests a person's parent phone number.
     */
    private Predicate<Person> getParentPhoneMatchPredicate() {
        return person -> isMatch(parentPhoneKeywords, person.getParentPhone().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s parent {@code Email} matches the keywords given.
     *
     * @return A predicate that tests a person's parent email.
     */
    private Predicate<Person> getParentEmailMatchPredicate() {
        return person -> isMatch(parentEmailKeywords, person.getParentEmail().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code Address} matches the keywords given.
     *
     * @return A predicate that tests a person's address.
     */
    private Predicate<Person> getAddressMatchPredicate() {
        return person -> isMatch(addressKeywords, person.getAddress().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code School} matches the keywords given.
     *
     * @return A predicate that tests a person's school.
     */
    private Predicate<Person> getSchoolMatchPredicate() {
        return person -> isMatch(schoolKeywords, person.getSchool().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code AcadStream} matches the keywords given.
     *
     * @return A predicate that tests a person's academic stream.
     */
    private Predicate<Person> getAcadStreamMatchPredicate() {
        return person -> isMatch(acadStreamKeywords, person.getAcadStream().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code AcadLevel} matches the keywords given.
     *
     * @return A predicate that tests a person's academic level.
     */
    private Predicate<Person> getAcadLevelMatchPredicate() {
        return person -> isMatch(acadLevelKeywords, person.getAcadLevel().value);
    }

    /**
     * Returns a {@code Predicate} that tests that a {@code Person}'s {@code Remark} matches the keywords given.
     *
     * @return A predicate that tests a person's remarks.
     */
    private Predicate<Person> getRemarkMatchPredicate() {
        return person -> isMatch(remarkKeywords, person.getRemark().value);
    }


    // Person Lesson predicates ----------------------------------------------------------------------------------------

    private Predicate<Person> getTimeRangeMatchPredicate() {
        return getLessonAnyMatch(lesson -> timeRange.isClashing(lesson.getTimeRange()));
    }

    private Predicate<Person> getDateMatchPredicate() {
        return getLessonAnyMatch(lesson -> lesson.hasLessonOnDate(date));
    }

    private Predicate<Person> getCancelledDateMatchPredicate() {
        return getLessonAnyMatch(lesson -> lesson.getCancelledDates().contains(cancelledDate));
    }

    private Predicate<Person> getSubjectMatchPredicate() {
        return getLessonAnyMatch(lesson -> isMatch(subjectKeywords, lesson.getSubject().value));
    }

    private Predicate<Person> getRatesMatchPredicate() {
        return getLessonAnyMatch(lesson -> isMatch(ratesKeywords, lesson.getLessonRates().value));
    }

    private Predicate<Person> getHomeworkMatchPredicate() {
        return getLessonAnyMatch(lesson -> lesson.getHomework().stream()
                .anyMatch(homework -> isMatch(homeworkKeywords, homework.description)));
    }

    private Predicate<Person> getLessonAnyMatch(Predicate<Lesson> predicate) {
        return person -> person.getLessons().stream().anyMatch(predicate);
    }

    /**
     * Returns a {@code List<Predicate<Person>>} that tests if each keyword matches a {@code Person}'s {@code Tag}s.
     *
     * @return A predicate that tests a person's tags.
     */
    private List<Predicate<Person>> getTagsMatchPredicates() {
        return tagKeywords.stream().map(this::getTagMatchPredicate).collect(Collectors.toList());
    }

    /**
     * Returns a {@code Predicate} that tests that one of a {@code Person}'s {@code tag}s matches the keyword given.
     * Ignores case, but the keyword must match the full tag name.
     *
     * @return A predicate that tests a person's phone number.
     */
    private Predicate<Person> getTagMatchPredicate(String keyword) {
        return person -> person.getTags().stream().anyMatch(tag -> keyword.equalsIgnoreCase(tag.getTagName()));
    }

    /**
     * Returns a list of predicates that test a person's fields against non-null keywords/fields.
     *
     * @return A list of person predicates.
     */
    private List<Predicate<Person>> getPredicates() {
        List<Predicate<Person>> predicates = new ArrayList<>();
        if (getNameKeywords().isPresent()) {
            predicates.add(getNameMatchPredicate());
        }
        if (getPhoneKeywords().isPresent()) {
            predicates.add(getPhoneMatchPredicate());
        }
        if (getEmailKeywords().isPresent()) {
            predicates.add(getEmailMatchPredicate());
        }
        if (getParentPhoneKeywords().isPresent()) {
            predicates.add(getParentPhoneMatchPredicate());
        }
        if (getParentEmailKeywords().isPresent()) {
            predicates.add(getParentEmailMatchPredicate());
        }
        if (getAddressKeywords().isPresent()) {
            predicates.add(getAddressMatchPredicate());
        }
        if (getSchoolKeywords().isPresent()) {
            predicates.add(getSchoolMatchPredicate());
        }
        if (getAcadStreamKeywords().isPresent()) {
            predicates.add(getAcadStreamMatchPredicate());
        }
        if (getAcadLevelKeywords().isPresent()) {
            predicates.add(getAcadLevelMatchPredicate());
        }
        if (getRemarkKeywords().isPresent()) {
            predicates.add(getRemarkMatchPredicate());
        }
        if (getTagKeywords().isPresent()) {
            predicates.addAll(getTagsMatchPredicates());
        }

        // Lesson predicates
        if (getTimeRange().isPresent()) {
            predicates.add(getTimeRangeMatchPredicate());
        }
        if (getDate().isPresent()) {
            predicates.add(getDateMatchPredicate());
        }
        if (getCancelledDate().isPresent()) {
            predicates.add(getCancelledDateMatchPredicate());
        }
        if (getSubjectKeywords().isPresent()) {
            predicates.add(getSubjectMatchPredicate());
        }
        if (getRateKeywords().isPresent()) {
            predicates.add(getRatesMatchPredicate());
        }
        if (getHomeworkKeywords().isPresent()) {
            predicates.add(getHomeworkMatchPredicate());
        }

        return predicates;
    }

    /**
     * Returns a composed predicate that represents matching all the given predicates.
     *
     * @param predicates A list of predicates to match.
     * @return A predicate that returns true only if all predicates test true on a person.
     */
    private Predicate<Person> matchAll(List<Predicate<Person>> predicates) {
        return predicates.stream().reduce(x -> true, Predicate::and);
    }

    /**
     * Returns a composed predicate that represents matching any one of the given predicates.
     *
     * @param predicates A list of predicates to match.
     * @return A predicate that returns true if any one of the predicates test true on a person.
     */
    private Predicate<Person> matchAny(List<Predicate<Person>> predicates) {
        return predicates.stream().reduce(x -> false, Predicate::or);
    }

    /**
     * Returns a composed predicate that represents matching none of the given predicates.
     *
     * @param predicates A list of predicates to match.
     * @return A predicate that returns true only if none of the predicates test true on a person.
     */
    private Predicate<Person> matchNone(List<Predicate<Person>> predicates) {
        return matchAny(predicates).negate();
    }

    /**
     * Evaluates this predicate on the given person.
     *
     * @param person The person to test on.
     * @return True if the person matches the predicate, otherwise false
     */
    @Override
    public boolean test(Person person) {
        List<Predicate<Person>> predicates = getPredicates();
        assert condition != null;
        switch (condition) {
        case ALL:
            return matchAll(predicates).test(person);
        case ANY:
            return matchAny(predicates).test(person);
        case NONE:
            return matchNone(predicates).test(person);
        default:
            // should not reach here
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonMatchesKeywordsPredicate)) {
            return false;
        }

        PersonMatchesKeywordsPredicate p = (PersonMatchesKeywordsPredicate) other;
        return getNameKeywords().equals(p.getNameKeywords())
                && getPhoneKeywords().equals(p.getPhoneKeywords())
                && getEmailKeywords().equals(p.getEmailKeywords())
                && getParentPhoneKeywords().equals(p.getParentPhoneKeywords())
                && getParentEmailKeywords().equals(p.getParentEmailKeywords())
                && getAddressKeywords().equals(p.getAddressKeywords())
                && getSchoolKeywords().equals(p.getSchoolKeywords())
                && getAcadStreamKeywords().equals(p.getAcadStreamKeywords())
                && getAcadLevelKeywords().equals(p.getAcadLevelKeywords())
                && getRemarkKeywords().equals(p.getRemarkKeywords())
                && getTimeRange().equals(p.getTimeRange())
                && getDate().equals(p.getDate())
                && getCancelledDate().equals(p.getCancelledDate())
                && getSubjectKeywords().equals(p.getSubjectKeywords())
                && getRateKeywords().equals(p.getRateKeywords())
                && getHomeworkKeywords().equals(p.getHomeworkKeywords())
                && getCondition().equals(p.getCondition());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("match " + condition.toString() + " of these keywords.");

        if (getNameKeywords().isPresent()) {
            builder.append("\nName: ").append(String.join(" ", getNameKeywords().get()));
        }
        if (getPhoneKeywords().isPresent()) {
            builder.append("\nPhone: ").append(String.join(" ", getPhoneKeywords().get()));
        }
        if (getEmailKeywords().isPresent()) {
            builder.append("\nEmail: ").append(String.join(" ", getEmailKeywords().get()));
        }
        if (getParentPhoneKeywords().isPresent()) {
            builder.append("\nParent Phone: ").append(String.join(" ", getParentPhoneKeywords().get()));
        }
        if (getParentEmailKeywords().isPresent()) {
            builder.append("\nParent Email: ").append(String.join(" ", getParentEmailKeywords().get()));
        }
        if (getAddressKeywords().isPresent()) {
            builder.append("\nAddress: ").append(String.join(" ", getAddressKeywords().get()));
        }
        if (getSchoolKeywords().isPresent()) {
            builder.append("\nSchool: ").append(String.join(" ", getSchoolKeywords().get()));
        }
        if (getAcadStreamKeywords().isPresent()) {
            builder.append("\nAcademic Stream: ").append(String.join(" ", getAcadStreamKeywords().get()));
        }
        if (getAcadLevelKeywords().isPresent()) {
            builder.append("\nAcademic Level: ").append(String.join(" ", getAcadLevelKeywords().get()));
        }
        if (getRemarkKeywords().isPresent()) {
            builder.append("\nRemark: ").append(String.join(" ", getRemarkKeywords().get()));
        }
        if (getTagKeywords().isPresent()) {
            builder.append("\nTags: ").append(String.join("; ", getTagKeywords().get()));
        }
        if (getSubjectKeywords().isPresent()) {
            builder.append("\nLesson Subject: ").append(String.join("; ", getSubjectKeywords().get()));
        }
        if (getDate().isPresent()) {
            builder.append("\nLesson Date: ").append(String.join("; ", getDate().get().toString()));
        }
        if (getCancelledDate().isPresent()) {
            builder.append("\nCancelled Date: ").append(String.join("; ", getCancelledDate().get().toString()));
        }
        if (getTimeRange().isPresent()) {
            builder.append("\nLesson Time: ").append(String.join("; ", getTimeRange().get().toString()));
        }
        if (getRateKeywords().isPresent()) {
            builder.append("\nLesson Rates: ").append(String.join("; ", getRateKeywords().get()));
        }
        if (getHomeworkKeywords().isPresent()) {
            builder.append("\nLesson Homework: ").append(String.join("; ", getHomeworkKeywords().get()));
        }
        return builder.toString();
    }

}
