package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.Phone;
import seedu.address.model.interview.Interview;
import seedu.address.model.position.Position;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final String APPLICANT_CSV_FILE = "applicant.csv";
    private static final String INTERVIEW_CSV_FILE = "interview.csv";
    private static final String POSITION_CSV_FILE = "position.csv";
    private static final String APPLICANT_CSV_HEADER = "Name,Phone,Email,Age,Address,Gender,Hire status,Tags";
    private static final String INTERVIEW_CSV_HEADER = "Date,Interview Status,Name,Phone,Email,Age,Address,"
            + "Gender,Hire status,Tags,Position,Description,Number of openings,Number of offers,Requirements";
    private static final String POSITION_CSV_HEADER = "Position,Description,Number of openings,Number of offers"
            + "Requirements";

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Applicant> filteredApplicants;
    private final FilteredList<Interview> filteredInterviews;
    private final FilteredList<Position> filteredPositions;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);
        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApplicants = new FilteredList<>(this.addressBook.getApplicantList());
        filteredInterviews = new FilteredList<>(this.addressBook.getInterviewList());
        filteredPositions = new FilteredList<>(this.addressBook.getPositionList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasApplicant(Applicant applicant) {
        requireNonNull(applicant);
        return addressBook.hasApplicant(applicant);
    }

    @Override
    public Applicant getApplicantWithEmail(Email email) {
        requireNonNull(email);
        return addressBook.getApplicantWithEmail(email);
    }

    @Override
    public Applicant getApplicantWithPhone(Phone phone) {
        requireNonNull(phone);
        return addressBook.getApplicantWithPhone(phone);
    }

    @Override
    public void deleteApplicant(Applicant target) {
        addressBook.removeApplicant(target);
    }

    @Override
    public void addApplicant(Applicant applicant) {
        addressBook.addApplicant(applicant);
        updateFilteredApplicantList(PREDICATE_SHOW_ALL_APPLICANTS);
    }

    @Override
    public void setApplicant(Applicant target, Applicant editedApplicant) {
        requireAllNonNull(target, editedApplicant);

        addressBook.setApplicant(target, editedApplicant);
    }

    @Override
    public boolean hasInterview(Interview interview) {
        requireNonNull(interview);
        return addressBook.hasInterview(interview);
    }

    @Override
    public boolean hasConflictingInterview(Interview interview) {
        requireAllNonNull(interview);
        return addressBook.hasConflictingInterview(interview);
    }


    @Override
    public void deleteInterview(Interview target) {
        addressBook.removeInterview(target);
    }

    @Override
    public void addInterview(Interview interview) {
        addressBook.addInterview(interview);
        updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
    }


    @Override
    public void setInterview(Interview target, Interview editedInterview) {
        requireAllNonNull(target, editedInterview);

        addressBook.setInterview(target, editedInterview);
    }

    @Override
    public boolean hasPosition(Position position) {
        requireNonNull(position);
        return addressBook.hasPosition(position);
    }

    @Override
    public void addPosition(Position position) {
        addressBook.addPosition(position);
        updateFilteredPositionList(PREDICATE_SHOW_ALL_POSITIONS);
    }

    @Override
    public void deletePosition(Position target) {
        addressBook.removePosition(target);
    }

    @Override
    public void setPosition(Position target, Position editedPosition) {
        requireAllNonNull(target, editedPosition);

        addressBook.setPosition(target, editedPosition);
    }

    @Override
    public void updateApplicant(Applicant applicantToBeUpdated, Applicant newApplicant) {
        requireAllNonNull(applicantToBeUpdated, newApplicant);

        addressBook.updateApplicant(applicantToBeUpdated, newApplicant);
    }

    @Override
    public void updatePosition(Position positionToBeUpdated, Position newPosition) {
        requireAllNonNull(positionToBeUpdated, newPosition);
        addressBook.updatePosition(positionToBeUpdated, newPosition);
    }

    //=========== Filtered Applicant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Applicant} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Applicant> getFilteredApplicantList() {
        return filteredApplicants;
    }

    @Override
    public void updateFilteredApplicantList(Predicate<Applicant> predicate) {
        requireNonNull(predicate);
        filteredApplicants.setPredicate(predicate);
    }
    // Need to test
    @Override
    public void updateSortApplicantList(Comparator<Applicant> comparator) {
        requireNonNull(comparator);
        addressBook.sortApplicant(comparator);
        filteredApplicants.setPredicate(PREDICATE_SHOW_ALL_APPLICANTS);
    }

    @Override
    public void updateFilterAndSortApplicantList(Predicate<Applicant> predicate, Comparator<Applicant> comparator) {
        requireAllNonNull(predicate, comparator);
        addressBook.sortApplicant(comparator);
        filteredApplicants.setPredicate(predicate);
    }

    @Override
    public void exportCsvApplicant() throws FileNotFoundException {
        File csvOutputFile = new File(APPLICANT_CSV_FILE);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(APPLICANT_CSV_HEADER);
            filteredApplicants.stream()
                    .map(Applicant::convertToCsv)
                    .forEach(pw::println);
        }
        assert(csvOutputFile.exists());
    }

    //=========== Filtered Interview List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Interview} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Interview> getFilteredInterviewList() {
        return filteredInterviews;
    }

    @Override
    public void updateFilteredInterviewList(Predicate<Interview> predicate) {
        requireNonNull(predicate);
        filteredInterviews.setPredicate(predicate);
    }

    @Override
    public void updateSortInterviewList(Comparator<Interview> comparator) {
        requireNonNull(comparator);
        addressBook.sortInterview(comparator);
        filteredInterviews.setPredicate(PREDICATE_SHOW_ALL_INTERVIEWS);
    }

    @Override
    public void updateFilterAndSortInterviewList(Predicate<Interview> predicate, Comparator<Interview> comparator) {
        requireAllNonNull(predicate, comparator);
        addressBook.sortInterview(comparator);
        filteredInterviews.setPredicate(predicate);
    }

    @Override
    public ArrayList<Interview> getApplicantsInterviews(Applicant applicant) {
        return addressBook.getApplicantsInterviews(applicant);
    }

    @Override
    public ArrayList<Interview> getPositionsInterviews(Position position) {
        return addressBook.getPositionsInterview(position);
    }

    @Override
    public void exportCsvInterview() throws FileNotFoundException {
        File csvOutputFile = new File(INTERVIEW_CSV_FILE);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(INTERVIEW_CSV_HEADER);
            filteredInterviews.stream()
                    .map(Interview::convertToCsv)
                    .forEach(pw::println);
        }
        assert (csvOutputFile.exists());
    }

    @Override
    public boolean isSameApplicantPosition(Applicant applicant, Position position) {
        return addressBook.isSameApplicantPosition(applicant, position);
    }

    //=========== Filtered Position List Accessors =============================================================
    @Override
    public ObservableList<Position> getFilteredPositionList() {
        return filteredPositions;
    }

    @Override
    public void updateFilteredPositionList(Predicate<Position> predicate) {
        requireNonNull(predicate);
        filteredPositions.setPredicate(predicate);
    }

    @Override
    public void updateSortPositionList(Comparator<Position> comparator) {
        requireNonNull(comparator);
        addressBook.sortPosition(comparator);
        filteredPositions.setPredicate(PREDICATE_SHOW_ALL_POSITIONS);
    }

    @Override
    public void updateFilterAndSortPositionList(Predicate<Position> predicate, Comparator<Position> comparator) {
        requireAllNonNull(predicate, comparator);
        addressBook.sortPosition(comparator);
        filteredPositions.setPredicate(predicate);
    }

    @Override
    public void exportCsvPosition() throws FileNotFoundException {
        File csvOutputFile = new File(POSITION_CSV_FILE);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(POSITION_CSV_HEADER);
            filteredPositions.stream()
                    .map(Position::convertToCsv)
                    .forEach(pw::println);
        }
        assert(csvOutputFile.exists());
    }

    //=========== Utility methods =============================================================
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredApplicants.equals(other.filteredApplicants);
    }
}
