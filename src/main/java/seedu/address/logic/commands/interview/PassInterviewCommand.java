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
import seedu.address.model.position.Position;

public class PassInterviewCommand extends Command {
    public static final String COMMAND_WORD = "pass";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PASS_INTERVIEW_SUCCESS = "Passed Interview: %1$s";
    public static final String MESSAGE_INTERVIEW_CANNOT_BE_PASSED = "The interview cannot be passed, "
            + "as the number of current offers will exceed the number of available positions or because only pending"
            + " interviews can be passed";

    private final Index targetIndex;

    public PassInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToPass = lastShownList.get(targetIndex.getZeroBased());

        if (!interviewToPass.isPassableInterview()) {
            throw new CommandException(MESSAGE_INTERVIEW_CANNOT_BE_PASSED);
        }

        Position oldPosition = interviewToPass.getPosition();
        Position newPosition = interviewToPass.getPosition().extendOffer();
        Interview passedInterview = new Interview(interviewToPass.getApplicant(), interviewToPass.getDate(),
                newPosition);

        passedInterview.markAsPassed();
        model.setInterview(interviewToPass, passedInterview);
        model.updatePosition(oldPosition, newPosition);

        return new CommandResult(String.format(MESSAGE_PASS_INTERVIEW_SUCCESS, passedInterview),
                getCommandDataType());
    }

    @Override
    public DataType getCommandDataType() {
        return DataType.INTERVIEW;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PassInterviewCommand // instanceof handles nulls
                && targetIndex.equals(((PassInterviewCommand) other).targetIndex)); // state check
    }
}
