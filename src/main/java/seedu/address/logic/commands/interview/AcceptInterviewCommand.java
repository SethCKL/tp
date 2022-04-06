package seedu.address.logic.commands.interview;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.DataType;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;
import seedu.address.model.position.Position;

public class AcceptInterviewCommand extends Command {
    public static final String COMMAND_WORD = "accept";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ACCEPT_INTERVIEW_SUCCESS = "Accept Interview: %1$s";
    public static final String MESSAGE_INTERVIEW_CANNOT_BE_ACCEPTED = "Only passed interviews can be accepted!";

    private final Index targetIndex;

    public AcceptInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToAccept = lastShownList.get(targetIndex.getZeroBased());
        if (!interviewToAccept.isAcceptableInterview()) {
            throw new CommandException(MESSAGE_INTERVIEW_CANNOT_BE_ACCEPTED);
        }

        Position oldPosition = interviewToAccept.getPosition();
        Position newPosition = interviewToAccept.getPosition().acceptOffer();
        Applicant oldApplicant = interviewToAccept.getApplicant();
        Applicant newApplicant = interviewToAccept.getApplicant().setStatus(oldApplicant, newPosition);
        Interview acceptedInterview = new Interview(newApplicant, interviewToAccept.getDate(),
                newPosition);

        // Interview has default status of "Pending", need to make it passed and then accepted
        acceptedInterview.markAsPassed();
        acceptedInterview.markAsAccepted();

        model.setInterview(interviewToAccept, acceptedInterview);
        model.updateApplicant(oldApplicant, newApplicant);
        model.updatePosition(oldPosition, newPosition);

        return new CommandResult(String.format(MESSAGE_ACCEPT_INTERVIEW_SUCCESS, acceptedInterview),
                getCommandDataType());
    }

    @Override
    public DataType getCommandDataType() {
        return DataType.INTERVIEW;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcceptInterviewCommand // instanceof handles nulls
                && targetIndex.equals(((AcceptInterviewCommand) other).targetIndex)); // state check
    }
}
