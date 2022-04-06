package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DATETIME = "Date time format should be yyyy-MM-dd HH:mm";
    public static final String MESSAGE_INVALID_DATE = "Date format should be yyyy-MM-dd";
    public static final String MESSAGE_INVALID_APPLICANT_DISPLAYED_INDEX = "The applicant index provided is invalid";
    public static final String MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX = "The interview index provided is invalid";
    public static final String MESSAGE_INVALID_POSITION_DISPLAYED_INDEX = "The position index provided is invalid";
    public static final String MESSAGE_INVALID_FLAG = "Flag is invalid!";
    public static final String MESSAGE_NO_FLAG = "No flag is found!";
    public static final String MESSAGE_APPLICANTS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_INTERVIEW = "This interview already exists in the address book";
    public static final String MESSAGE_CONFLICTING_INTERVIEW = "This interview would cause a conflict of timings with"
            + " a current interview in the address book. Interviews must be "
            + "at least 1 hour apart for the same candidate.";
    public static final String MESSAGE_APPLICANT_SAME_POSITION = "%1$s already has an interview for %2$s";
}
