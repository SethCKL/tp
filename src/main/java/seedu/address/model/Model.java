package seedu.address.model;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.Phone;
import seedu.address.model.interview.Interview;
import seedu.address.model.position.Position;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Applicant> PREDICATE_SHOW_ALL_APPLICANTS = unused -> true;
    Predicate<Interview> PREDICATE_SHOW_ALL_INTERVIEWS = unused -> true;
    Predicate<Position> PREDICATE_SHOW_ALL_POSITIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a applicant with the same identity as {@code applicant} exists in the address book.
     */
    boolean hasApplicant(Applicant applicant);

    /**
     * Returns the {@code Applicant} with the {@code email} provided if exists; or null if no such applicant.
     */
    Applicant getApplicantWithEmail(Email email);

    /**
     * Returns the {@code Applicant} with the {@code phone} provided if exists; or null if no such applicant.
     */
    Applicant getApplicantWithPhone(Phone phone);

    /**
     * Deletes the given applicant.
     * The applicant must exist in the address book.
     */
    void deleteApplicant(Applicant target);

    /**
     * Adds the given applicant.
     * {@code applicant} must not already exist in the address book.
     */
    void addApplicant(Applicant applicant);

    /**
     * Replaces the given applicant {@code target} with {@code editedApplicant}.
     * {@code target} must exist in the address book.
     * The applicant identity of {@code editedApplicant} must not be the same as another existing applicant
     * in the address book.
     */
    void setApplicant(Applicant target, Applicant editedApplicant);

    /** Returns an unmodifiable view of the filtered applicant list */
    ObservableList<Applicant> getFilteredApplicantList();

    /**
     * Updates the filter of the filtered applicant list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredApplicantList(Predicate<Applicant> predicate);

    /**
     * Returns true if an interview already exists in the address book.
     */
    boolean hasInterview(Interview interview);

    /**
     * Returns true if an applicant already has an interview for that timeslot.
     */
    boolean hasConflictingInterview(Interview interview);

    /**
     * Deletes the given interview.
     * The interview must exist in the address book.
     */
    void deleteInterview(Interview target);

    /**
     * Adds the given interview.
     * {@code interview} must not already exist in HireLah.
     */
    void addInterview(Interview interview);


    /**
     * Replaces the given interview {@code target} with {@code editedInterview}.
     * {@code target} must exist in the address book.
     * The interview identity of {@code editedInterview} must not be the same as another existing interview
     * in the address book.
     */
    void setInterview(Interview target, Interview editedInterview);

    /** Returns an unmodifiable view of the filtered interview list */
    ObservableList<Interview> getFilteredInterviewList();

    /**
     * Updates the filter of the filtered interview list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInterviewList(Predicate<Interview> predicate);

    /**
     * Returns interview(s) which are for the specified applicant.
     */
    ArrayList<Interview> getApplicantsInterviews(Applicant applicant);

    /**
     * Returns interview(s) which are for the specified position.
     */
    ArrayList<Interview> getPositionsInterviews(Position position);

    /**
     * Checks if the specified applicant has an interview for the specified position.
     */
    boolean isSameApplicantPosition(Applicant applicant, Position position);

    /**
     * Updates the filter of the filtered position list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPositionList(Predicate<Position> predicate);

    /**
     * Deletes the given position.
     * The position must exist in the address book.
     */
    void deletePosition(Position target);

    /**
     * Returns true if a position already exists in the address book.
     */
    boolean hasPosition(Position position);

    /**
     * Adds the given position.
     * {@code position} must not already exist in the address book.
     */
    void addPosition(Position position);

    /**
     * Replaces the given position {@code target} with {@code editedPosition}.
     * {@code target} must exist in the address book.
     * The position identity of {@code editedPosition} must not be the same as another existing position
     * in the address book.
     */
    void setPosition(Position target, Position editedPosition);

    /**
     * Replaces all instances of {@code positionToBeUpdated} with {@code newPosition}.
     * {@code positionToBeUpdated} must exist in the address book.
     * The position identity of {@code newPosition} must not be the same as another existing position
     * in the address book.
     */
    void updatePosition(Position positionToBeUpdated, Position newPosition);

    /**
     * Replaces all instances of {@code applicantToBeUpdated} with {@code newApplicant}.
     * {@code applicantToBeUpdated} must exist in the address book.
     * The applicant identity of {@code newApplicant} must not be the same as another existing applicant
     * in the address book.
     */
    void updateApplicant(Applicant applicantToBeUpdated, Applicant newApplicant);

    /** Returns an unmodifiable view of the filtered position list */
    ObservableList<Position> getFilteredPositionList();

    void updateSortApplicantList(Comparator<Applicant> comparator);

    void updateSortInterviewList(Comparator<Interview> comparator);

    void updateSortPositionList(Comparator<Position> comparator);

    void updateFilterAndSortApplicantList(Predicate<Applicant> predicate, Comparator<Applicant> comparator);

    void updateFilterAndSortInterviewList(Predicate<Interview> predicate, Comparator<Interview> comparator);

    void updateFilterAndSortPositionList(Predicate<Position> predicate, Comparator<Position> comparator);

    void exportCsvApplicant() throws FileNotFoundException;

    void exportCsvInterview() throws FileNotFoundException;

    void exportCsvPosition() throws FileNotFoundException;
}
