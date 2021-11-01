package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_PARENT_PHONE = new Prefix("pp/");
    public static final Prefix PREFIX_PARENT_EMAIL = new Prefix("pe/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix for academic details */
    public static final Prefix PREFIX_SCHOOL = new Prefix("sch/");
    public static final Prefix PREFIX_ACAD_STREAM = new Prefix("stream/");
    public static final Prefix PREFIX_ACAD_LEVEL = new Prefix("lvl/");

    /* Prefix definitions for lesson */
    public static final Prefix PREFIX_RECURRING = new Prefix("recurring/");
    public static final Prefix PREFIX_DATE = new Prefix("date/");
    public static final Prefix PREFIX_TIME = new Prefix("time/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("subject/");
    public static final Prefix PREFIX_HOMEWORK = new Prefix("hw/");
    public static final Prefix PREFIX_RATES = new Prefix("rates/");
    public static final Prefix PREFIX_OUTSTANDING_FEES = new Prefix("f/");
    public static final Prefix PREFIX_PAID_AMOUNT = new Prefix("amt/");
    public static final Prefix PREFIX_CANCEL = new Prefix("cancel/");
    public static final Prefix PREFIX_UNCANCEL = new Prefix("uncancel/");

    /* Prefix definitions for command options */
    public static final Prefix PREFIX_FIND_CONDITION = new Prefix("cond/");
}
