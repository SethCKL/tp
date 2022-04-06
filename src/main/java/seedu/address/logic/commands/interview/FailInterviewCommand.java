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
import seedu.address.model.interview.Interview;

public class FailInterviewCommand extends Command {
    public static final String COMMAND_WORD = "fail";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAIL_INTERVIEW_SUCCESS = "Failed Interview: %1$s";
    public static final String MESSAGE_INTERVIEW_CANNOT_BE_FAILED = "The interview cannot be failed, "
            + "because only pending interviews can be failed";

    private final Index targetIndex;

    public FailInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToFail = lastShownList.get(targetIndex.getZeroBased());
        if (!interviewToFail.isFailableInterview()) {
            throw new CommandException(MESSAGE_INTERVIEW_CANNOT_BE_FAILED);
        }

        Interview failedInterview = new Interview(interviewToFail.getApplicant(), interviewToFail.getDate(),
                interviewToFail.getPosition());

        failedInterview.markAsFailed();
        model.setInterview(interviewToFail, failedInterview);

        return new CommandResult(String.format(MESSAGE_FAIL_INTERVIEW_SUCCESS, interviewToFail),
                getCommandDataType());
    }

    @Override
    public DataType getCommandDataType() {
        return DataType.INTERVIEW;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FailInterviewCommand // instanceof handles nulls
                && targetIndex.equals(((FailInterviewCommand) other).targetIndex)); // state check
    }
}
