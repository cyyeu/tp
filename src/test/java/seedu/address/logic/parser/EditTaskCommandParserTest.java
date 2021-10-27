package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RECURRING_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.RECURRING_FREQUENCY_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.RECURRING_FREQUENCY_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.TASK_TYPE_DESC_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_TYPE_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECURRING_FREQUENCY_WEEK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECURRING_FREQUENCY_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TYPE_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TYPE_TODO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.RecurringFrequency;
import seedu.address.model.task.TaskType;
import seedu.address.testutil.EditTaskDescriptorBuilder;


public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION_TODO, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DESCRIPTION_DESC_TODO, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DESCRIPTION_DESC_TODO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC,
                Priority.MESSAGE_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1" + INVALID_TASK_TYPE_DESC,
                TaskType.MESSAGE_CONSTRAINTS); // invalid task type
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_RECURRING_FREQUENCY_DESC,
                RecurringFrequency.MESSAGE_CONSTRAINTS); // invalid recurring freq

        // invalid description followed by valid date
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + DATE_DESC_TODO,
                Description.MESSAGE_CONSTRAINTS);

        // valid description followed by invalid description. The test case for invalid description
        // followed by valid description is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_TODO + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + INVALID_DATE_DESC + INVALID_PRIORITY_DESC,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_TODO + GROUP_DESC_TODO + PRIORITY_DESC_TODO
                + DATE_DESC_TODO + RECURRING_FREQUENCY_DESC_TODO + TASK_TYPE_DESC_TODO;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_TODO).withGroup(VALID_GROUP_CS2101).withPriority(VALID_PRIORITY_LOW)
                .withDate(VALID_DATE_1).withRecurringFrequency(VALID_RECURRING_FREQUENCY_WEEK)
                .withTaskType(VALID_TASK_TYPE_TODO).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_TODO + RECURRING_FREQUENCY_DESC_EVENT;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_TODO)
                .withRecurringFrequency(VALID_RECURRING_FREQUENCY_YEAR).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_TODO;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_TODO).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // group
        userInput = targetIndex.getOneBased() + GROUP_DESC_EVENT;
        descriptor = new EditTaskDescriptorBuilder().withGroup(VALID_GROUP_CS2101).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_EVENT;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_HIGH).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // recurring frequency
        userInput = targetIndex.getOneBased() + RECURRING_FREQUENCY_DESC_EVENT;
        descriptor = new EditTaskDescriptorBuilder().withRecurringFrequency(VALID_RECURRING_FREQUENCY_YEAR).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_EVENT;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_3).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task type
        userInput = targetIndex.getOneBased() + TASK_TYPE_DESC_DEADLINE;
        descriptor = new EditTaskDescriptorBuilder().withTaskType(VALID_TASK_TYPE_DEADLINE).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_TODO;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_TODO).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DATE_DESC_TODO + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_TODO;
        descriptor = new EditTaskDescriptorBuilder()
                .withDate(VALID_DATE_1)
                .withDescription(VALID_DESCRIPTION_TODO)
                .build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}