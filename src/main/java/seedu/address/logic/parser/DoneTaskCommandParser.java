package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DoneTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DoneTaskCommandParser implements Parser<DoneTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DoneTaskCommand
     * and returns an DoneTaskCommand object for execution.
     * @param args
     * @return DoneTaskCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DoneTaskCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}