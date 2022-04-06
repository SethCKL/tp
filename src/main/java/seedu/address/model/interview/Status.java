package seedu.address.model.interview;

/**
 * Represents the status of interview in HireLah.
 * Guarantees: is always initialized as "Pending" {@link #Status()}
 */
public class Status {

    private String value;

    /**
     * Constructs a Status with default value of "Pending".
     */
    public Status() {
        this("Pending");
    }

    public Status(String value) {
        this.value = value;
    }

    public void markAsPassed() {
        value = "Passed - Waiting for Applicant";
    }

    public void markAsFailed() {
        value = "Failed";
    }

    /**
     * Marks an interview as accepted only if status is passed.
     */
    public void markAsAccepted() {
        // Defensive programming to prevent acceptance before passing interview
        if (!isPassedStatus()) {
            throw new RuntimeException("The Interview should be passed before its can be accepted by candidate");
        }
        value = "Accepted";
    }

    /**
     * Marks an interview as rejected only if status is passed.
     */
    public void markAsRejected() {
        // Defensive programming to prevent rejecting before passing interview
        if (!isPassedStatus()) {
            throw new RuntimeException("The Interview should be passed before its can be accepted by candidate");
        }
        value = "Rejected";
    }

    /**
     * Checks whether the current status is Pending.
     */
    public boolean isPendingStatus() {
        return value.equals("Pending");
    }

    /**
     * Checks whether the current status is Passed.
     */
    public boolean isPassedStatus() {
        return value.equals("Passed - Waiting for Applicant");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
