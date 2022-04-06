package seedu.address.logic.commands.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.DataType;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;
import seedu.address.model.position.Position;

/**
 * Edits the details of an existing interview in the address book.
 */
public class EditInterviewCommand extends EditCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " -i: Edits the details of an interview in Hirelah\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPLICANT + "APPLICANT_INDEX"
            + PREFIX_DATE + "DATE "
            + PREFIX_POSITION + "POSITION_INDEX"
            + "\nExample: " + COMMAND_WORD + " -i " + "1 "
            + PREFIX_APPLICANT + "1"
            + PREFIX_DATE + "2022-05-03 16:00 "
            + PREFIX_POSITION + "2";

    public static final String MESSAGE_NOT_EDITED = "At least one field in Interview to edit must be provided.";
    public static final String MESSAGE_EDIT_INTERVIEW_SUCCESS = "Edited interview: %1$s";
    public static final String MESSAGE_NOT_PENDING = "Only interviews that are pending can be edited.";

    private final Index index;
    private final EditInterviewDescriptor editInterviewDescriptor;

    /**
     * @param index                   of the interview in the filtered interview list to edit
     * @param editInterviewDescriptor details to edit the interview with
     */
    public EditInterviewCommand(Index index, EditInterviewDescriptor editInterviewDescriptor) {
        requireNonNull(index);
        requireNonNull(editInterviewDescriptor);

        this.index = index;
        this.editInterviewDescriptor = new EditInterviewDescriptor(editInterviewDescriptor);
    }

    /**
     * Creates and returns a {@code Interview} with the details of {@code interviewToEdit}
     * edited with {@code editInterviewDescriptor}.
     */
    private static Interview createEditedInterview(Interview interviewToEdit, EditInterviewDescriptor
                                                    editInterviewDescriptor, Model model) throws CommandException {
        assert interviewToEdit != null;

        Index updatedApplicantIndex = editInterviewDescriptor.getApplicantIndex().orElse(null);
        Applicant updatedApplicant;
        if (updatedApplicantIndex == null) {
            updatedApplicant = interviewToEdit.getApplicant();
        } else {
            List<Applicant> lastShownApplicantList = model.getFilteredApplicantList();
            if (updatedApplicantIndex.getZeroBased() >= lastShownApplicantList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_APPLICANT_DISPLAYED_INDEX);
            }
            updatedApplicant = lastShownApplicantList.get(updatedApplicantIndex.getZeroBased());
        }

        LocalDateTime updatedDate = editInterviewDescriptor.getDate().orElse(interviewToEdit.getDate());

        Index updatedPositionIndex = editInterviewDescriptor.getPositionIndex().orElse(null);
        Position updatedPosition;
        if (updatedPositionIndex == null) {
            updatedPosition = interviewToEdit.getPosition();
        } else {
            List<Position> lastShownPositionList = model.getFilteredPositionList();
            if (updatedPositionIndex.getZeroBased() >= lastShownPositionList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_POSITION_DISPLAYED_INDEX);
            }
            updatedPosition = lastShownPositionList.get(updatedPositionIndex.getZeroBased());
        }

        Interview updatedInterview = new Interview(updatedApplicant, updatedDate, updatedPosition);

        return updatedInterview;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToEdit = lastShownList.get(index.getZeroBased());

        if (!interviewToEdit.getStatus().isPendingStatus()) {
            throw new CommandException(MESSAGE_NOT_PENDING);
        }

        Interview editedInterview = createEditedInterview(interviewToEdit, editInterviewDescriptor, model);

        boolean applicantEdited = !(interviewToEdit.getApplicant().equals(editedInterview.getApplicant()));
        boolean positionEdited = !(interviewToEdit.getPosition().equals(editedInterview.getPosition()));

        if ((applicantEdited || positionEdited)
                && model.isSameApplicantPosition(editedInterview.getApplicant(), editedInterview.getPosition())) {
            throw new CommandException(String.format(Messages.MESSAGE_APPLICANT_SAME_POSITION,
                    editedInterview.getApplicant().getName().fullName,
                    editedInterview.getPosition().getPositionName().positionName));
        }

        boolean dateEdited = !(interviewToEdit.getDate().equals(editedInterview.getDate()));
        if (dateEdited && model.hasConflictingInterview(editedInterview)) {
            throw new CommandException(Messages.MESSAGE_CONFLICTING_INTERVIEW);
        }

        if (!interviewToEdit.equals(editedInterview) && model.hasInterview(editedInterview)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_INTERVIEW);
        }

        model.setInterview(interviewToEdit, editedInterview);
        model.updateFilteredInterviewList(Model.PREDICATE_SHOW_ALL_INTERVIEWS);

        return new CommandResult(String.format(MESSAGE_EDIT_INTERVIEW_SUCCESS, editedInterview), getCommandDataType());
    }

    @Override
    public DataType getCommandDataType() {
        return DataType.INTERVIEW;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof seedu.address.logic.commands.position.EditPositionCommand)) {
            return false;
        }

        // state check
        EditInterviewCommand e = (EditInterviewCommand) other;
        return index.equals(e.index) && editInterviewDescriptor.equals(e.editInterviewDescriptor);
    }

    /**
     * Store the details to edit the interview with. Each non-empty field value will replace the
     * corresponding field value of the interview.
     */
    public static class EditInterviewDescriptor {
        private Index applicantIndex;
        private LocalDateTime date;
        private Index positionIndex;

        public EditInterviewDescriptor() {
        }

        /**
         * Constructs an EditInterviewDescriptor.
         *
         * @param toCopy descriptor object to copy
         */
        public EditInterviewDescriptor(EditInterviewDescriptor toCopy) {
            setApplicantIndex(toCopy.applicantIndex);
            setDate(toCopy.date);
            setPositionIndex(toCopy.positionIndex);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(applicantIndex, date, positionIndex);
        }

        public Optional<Index> getApplicantIndex() {
            return Optional.ofNullable(applicantIndex);
        }

        public void setApplicantIndex(Index applicantIndex) {
            this.applicantIndex = applicantIndex;
        }

        public Optional<LocalDateTime> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public Optional<Index> getPositionIndex() {
            return Optional.ofNullable(positionIndex);
        }

        public void setPositionIndex(Index positionIndex) {
            this.positionIndex = positionIndex;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditInterviewDescriptor)) {
                return false;
            }

            // state check
            EditInterviewDescriptor e = (EditInterviewDescriptor) other;

            return getApplicantIndex().equals(e.getApplicantIndex())
                    && getDate().equals(e.getDate())
                    && getPositionIndex().equals(e.getPositionIndex());
        }
    }
}
