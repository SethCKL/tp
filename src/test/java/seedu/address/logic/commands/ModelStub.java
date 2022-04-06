package seedu.address.logic.commands;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.Phone;
import seedu.address.model.interview.Interview;
import seedu.address.model.position.Position;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addApplicant(Applicant applicant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasApplicant(Applicant applicant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Applicant getApplicantWithEmail(Email email) {
        return null;
    }

    @Override
    public Applicant getApplicantWithPhone(Phone phone) {
        return null;
    }

    @Override
    public boolean isSameApplicantPosition(Applicant applicant, Position position) {
        return true;
    }

    @Override
    public void deleteApplicant(Applicant target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setApplicant(Applicant target, Applicant editedApplicant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Applicant> getFilteredApplicantList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Interview> getFilteredInterviewList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Position> getFilteredPositionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSortApplicantList(Comparator<Applicant> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSortInterviewList(Comparator<Interview> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSortPositionList(Comparator<Position> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilterAndSortApplicantList(Predicate<Applicant> predicate, Comparator<Applicant> comparator) {

    }

    @Override
    public void updateFilterAndSortInterviewList(Predicate<Interview> predicate, Comparator<Interview> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilterAndSortPositionList(Predicate<Position> predicate, Comparator<Position> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportCsvApplicant() throws FileNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportCsvInterview() throws FileNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportCsvPosition() throws FileNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredApplicantList(Predicate<Applicant> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addInterview(Interview interview) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInterview(Interview target, Interview editedInterview) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredInterviewList(Predicate<Interview> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPositionList(Predicate<Position> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Interview> getApplicantsInterviews(Applicant applicant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Interview> getPositionsInterviews(Position position) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePosition(Position target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPosition(Position position) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasConflictingInterview(Interview interview) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPosition(Position target, Position editedPosition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updatePosition(Position positionToBeUpdated, Position newPosition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateApplicant(Applicant applicantToBeUpdated, Applicant newApplicant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPosition(Position position) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasInterview(Interview interview) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteInterview(Interview target) {
        throw new AssertionError("This method should not be called.");
    }
}
