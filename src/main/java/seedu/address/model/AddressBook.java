package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.Phone;
import seedu.address.model.applicant.UniqueApplicantList;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.UniqueInterviewList;
import seedu.address.model.position.Position;
import seedu.address.model.position.UniquePositionList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameApplicant comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueApplicantList applicants;
    private final UniqueInterviewList interviews;
    private final UniquePositionList positions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        applicants = new UniqueApplicantList();
        interviews = new UniqueInterviewList();
        positions = new UniquePositionList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the applicant list with {@code applicants}.
     * {@code applicants} must not contain duplicate applicants.
     */
    public void setApplicants(List<Applicant> applicants) {
        this.applicants.setApplicants(applicants);
    }

    /**
     * Replaces the contents of the position list with {@code positions}.
     * {@code positions} must not contain duplicate positions.
     */
    public void setPositions(List<Position> positions) {
        this.positions.setPositions(positions);
    }

    /**
     * Replaces the contents of the interview list with {@code interviews}.
     * {@code interviews} must not contain duplicate interviews.
     */
    public void setInterviews(List<Interview> interviews) {
        this.interviews.setInterviews(interviews);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setApplicants(newData.getApplicantList());
        setPositions(newData.getPositionList());
        setInterviews(newData.getInterviewList());

    }

    //// applicant-level operations

    /**
     * Returns true if an applicant with the same identity as {@code applicant} exists in the address book.
     */
    public boolean hasApplicant(Applicant applicant) {
        requireNonNull(applicant);
        return applicants.contains(applicant);
    }

    /**
     * Returns the {@code Applicant} with the {@code email} provided if exists; or null if no such applicant.
     */
    public Applicant getApplicantWithEmail(Email email) {
        requireNonNull(email);
        return applicants.getApplicantWithEmail(email);
    }

    /**
     * Returns the {@code Applicant} with the {@code phone} provided if exists; or null if no such applicant.
     */
    public Applicant getApplicantWithPhone(Phone phone) {
        requireNonNull(phone);
        return applicants.getApplicantWithPhone(phone);
    }

    /**
     * Adds an applicant to the address book.
     * The applicant must not already exist in the address book.
     */
    public void addApplicant(Applicant p) {
        applicants.add(p);
    }

    /**
     * Replaces the given applicant {@code target} in the list with {@code editedApplicant}.
     * {@code target} must exist in the address book.
     * The applicant identity of {@code editedApplicant} must not be the same as another existing applicant
     *  in the address book.
     */
    public void setApplicant(Applicant target, Applicant editedApplicant) {
        requireNonNull(editedApplicant);

        applicants.setApplicant(target, editedApplicant);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeApplicant(Applicant key) {
        applicants.remove(key);
    }

    /**
     * Sorts list of applicant using comparator
     */
    public void sortApplicant(Comparator<Applicant> comparator) {
        applicants.sort(comparator);
    }

    //// interview-level operations

    /**
     * Returns true if an interview that is the same as {@code interview} exists in the address book.
     */
    public boolean hasInterview(Interview i) {
        requireNonNull(i);
        return interviews.contains(i);
    }

    /**
     * Returns true if an interview has a conflict in timing as {@code interview} exists in the address book.
     */
    public boolean hasConflictingInterview(Interview i) {
        requireNonNull(i);
        return interviews.containsConflict(i);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeInterview(Interview key) {
        interviews.remove(key);
    }

    /**
     * Adds an interview to the address book.
     * The interview must not already exist in the address book.
     */
    public void addInterview(Interview i) {
        interviews.add(i);
    }

    /**
     * Returns interview(s) which are for the specified applicant.
     */
    public ArrayList<Interview> getApplicantsInterviews(Applicant applicant) {
        return interviews.getApplicantsInterviews(applicant);
    }

    /**
     * Returns interview(s) which are for the specified position.
     */
    public ArrayList<Interview> getPositionsInterview(Position position) {
        return interviews.getPositionsInterview(position);
    }

    /**
     * Checks if the specified applicant has an interview for the specified position.
     */
    public boolean isSameApplicantPosition(Applicant applicant, Position position) {
        return interviews.isSameApplicantPosition(applicant, position);
    }

    /**
     * Replaces the given interview {@code target} with {@code editedInterview}.
     * {@code target} must exist in HireLah.
     * The interview identity of {@code editedInterview} must not be the same as another existing interview
     * in HireLah.
     */
    public void setInterview(Interview target, Interview editedInterview) {
        requireNonNull(editedInterview);
        interviews.setInterview(target, editedInterview);
    }

    /**
     * Sorts list of interview using comparator
     */
    public void sortInterview(Comparator<Interview> comparator) {
        requireNonNull(comparator);
        interviews.sort(comparator);
    }

    //// position-level operations

    /**
     * Returns true if a position that is the same as {@code position} exists in the address book.
     */
    public boolean hasPosition(Position position) {
        requireNonNull(position);
        return positions.contains(position);
    }

    /**
     * Adds a position to the address book.
     * The position must not already exist in the address book.
     */
    public void addPosition(Position position) {
        positions.add(position);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePosition(Position key) {
        positions.remove(key);
    }

    /**
     * Updates all old instances of {@code positionToBeUpdated} with {@code newPosition}.
     * Existence of {@code positionToBeUpdated} and uniqueness of {@code newPosition} will be checked at
     * {@link #setPosition(Position, Position)}.
     */
    public void updatePosition(Position positionToBeUpdated, Position newPosition) {
        requireAllNonNull(positionToBeUpdated, newPosition);
        setPosition(positionToBeUpdated, newPosition);
        interviews.updatePositions(positionToBeUpdated, newPosition);
    }

    /**
     * Updates all old instances of {@code applicantToBeUpdated} with {@code newApplicant}.
     * Existence of {@code applicantToBeUpdated} and uniqueness of {@code newApplicant} will be checked at
     * {@link #setApplicant(Applicant, Applicant)}.
     */
    public void updateApplicant(Applicant applicantToBeUpdated, Applicant newApplicant) {
        requireAllNonNull(applicantToBeUpdated, newApplicant);
        setApplicant(applicantToBeUpdated, newApplicant);
        interviews.updateApplicants(applicantToBeUpdated, newApplicant);
    }

    /**
     * Replaces the given position {@code target} in the list with {@code editedPosition}.
     * {@code target} must exist in the address book.
     * The position identity of {@code editedPosition} must not be the same as another existing position
     *  in the address book.
     */
    public void setPosition(Position target, Position editedPosition) {
        requireNonNull(editedPosition);

        positions.setPosition(target, editedPosition);
    }

    /**
     * Sorts list of interview using comparator
     */
    public void sortPosition(Comparator<Position> comparator) {
        requireNonNull(comparator);
        positions.sort(comparator);
    }
    //// util methods

    @Override
    public String toString() {
        return applicants.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Applicant> getApplicantList() {
        return applicants.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Interview> getInterviewList() {
        return interviews.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Position> getPositionList() {
        return positions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && applicants.equals(((AddressBook) other).applicants));
    }

    @Override
    public int hashCode() {
        return applicants.hashCode();
    }

    public Applicant getApplicantUsingStorage(Applicant interviewApplicant) {
        return applicants.getApplicant(interviewApplicant);
    }

    public Position getPositionUsingStorage(Position interviewPosition) {
        return positions.getPosition(interviewPosition);
    }
}
