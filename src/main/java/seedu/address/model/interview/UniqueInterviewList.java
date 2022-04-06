package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;
import seedu.address.model.position.Position;

public class UniqueInterviewList implements Iterable<Interview> {
    private final ObservableList<Interview> internalList = FXCollections.observableArrayList();
    private final ObservableList<Interview> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent interview as the given argument.
     */
    public boolean contains(Interview toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an interview which has a conflict in timing as the given argument.
     */
    public boolean containsConflict(Interview toCheck) {
        requireNonNull(toCheck);
        for (Interview i : internalList) {
            if (i.isConflictingInterview(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an interview to the list.
     * The interview must not already exist in the list.
     */
    public void add(Interview toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInterviewException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the interview {@code target} in the list with {@code editedInterivew}.
     * {@code target} must exist in the list.
     * The interview {@code editedInterview} must not be the same as another existing interview in the list.
     */
    public void setInterview(Interview target, Interview editedInterview) {
        requireAllNonNull(target, editedInterview);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InterviewNotFoundException();
        }

        if (!target.equals(editedInterview) && contains(editedInterview)) {
            throw new DuplicateInterviewException();
        }

        internalList.set(index, editedInterview);
    }

    /**
     * Removes the equivalent interview from the list.
     * The interview must exist in the list.
     */
    public void remove(Interview toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new InterviewNotFoundException();
        }
    }

    /**
     * Returns interview(s) which are for the specified applicant.
     */
    public ArrayList<Interview> getApplicantsInterviews(Applicant applicant) {
        ArrayList<Interview> interviews = new ArrayList<>();

        for (Interview i : internalList) {
            if (i.isInterviewForApplicant(applicant)) {
                interviews.add(i);
            }
        }

        return interviews;
    }

    /**
     * Returns interview(s) which are for the specified position.
     */
    public ArrayList<Interview> getPositionsInterview(Position position) {
        ArrayList<Interview> interviews = new ArrayList<>();

        for (Interview i : internalList) {
            if (i.isInterviewForPosition(position)) {
                interviews.add(i);
            }
        }

        return interviews;
    }

    /**
     * Checks if the specified applicant has an interview for the specified position.
     */
    public boolean isSameApplicantPosition(Applicant applicant, Position position) {
        for (Interview i : getApplicantsInterviews(applicant)) {
            if (i.isInterviewForPosition(position)) {
                return true;
            }
        }
        return false;
    }

    public void setInterviews(UniqueInterviewList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code interview}.
     * {@code interview} must not contain duplicate interview.
     */
    public void setInterviews(List<Interview> interview) {
        requireAllNonNull(interview);
        if (!interviewsAreUnique(interview)) {
            throw new DuplicateInterviewException();
        }

        internalList.setAll(interview);
    }

    /**
     * Sorts a list of interviews
     */
    public void sort(Comparator<Interview> comparator) {
        requireNonNull(comparator);
        internalList.sort(comparator);
    }

    /**
     * Updates all interview containing instance of {@code positionToBeUpdated} to {@code newPosition}.
     * Effects of editing a Position cascades to update all instances of the old position to the edited position.
     */
    public void updatePositions(Position positionToBeUpdated, Position newPosition) {
        requireAllNonNull(positionToBeUpdated, newPosition);
        for (int i = 0; i < internalList.size(); i++) {
            Interview curr = internalList.get(i);
            if (curr.getPosition().equals(positionToBeUpdated)) {
                internalList.set(i, new Interview(curr.getApplicant(), curr.getDate(), newPosition, curr.getStatus()));
            }
        }
    }

    /**
     * Updates all interview containing instance of {@code applicantToBeUpdated} to {@code newApplicant}.
     * Effects of editing an Applicant cascades to update all instances of the old applicant to the edited applicant.
     */
    public void updateApplicants(Applicant applicantToBeUpdated, Applicant newApplicant) {
        requireAllNonNull(applicantToBeUpdated, newApplicant);
        for (int i = 0; i < internalList.size(); i++) {
            Interview curr = internalList.get(i);
            if (curr.getApplicant().equals(applicantToBeUpdated)) {
                internalList.set(i, new Interview(newApplicant, curr.getDate(), curr.getPosition(), curr.getStatus()));
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Interview> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Interview> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueInterviewList // instanceof handles nulls
                && internalList.equals(((UniqueInterviewList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code interviews} contains only unique interviews.
     */
    private boolean interviewsAreUnique(List<Interview> interviews) {
        for (int i = 0; i < interviews.size() - 1; i++) {
            for (int j = i + 1; j < interviews.size(); j++) {
                if (interviews.get(i).equals(interviews.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
