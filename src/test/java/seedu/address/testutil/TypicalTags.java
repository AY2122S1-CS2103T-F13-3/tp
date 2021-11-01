package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FORGETFUL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNPAID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ZOOM;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {
    public static final Tag TAG_FORGETFUL = new Tag(VALID_TAG_FORGETFUL);
    public static final Tag TAG_UNPAID = new Tag(VALID_TAG_UNPAID);
    public static final Tag TAG_ZOOM = new Tag(VALID_TAG_ZOOM);
}
