package sweebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.logic.commands.CommandTestUtil.DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;

import org.junit.jupiter.api.Test;

import sweebook.logic.commands.EditCommand.EditPersonDescriptor;
import sweebook.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different group -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withGroups(VALID_GROUP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different telegram -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTelegram(VALID_TELEGRAM_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different github -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withGitHub(VALID_GITHUB_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}