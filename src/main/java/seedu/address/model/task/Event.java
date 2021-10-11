package seedu.address.model.task;

import seedu.address.model.group.Group;

public class Event extends Task {
    private final String symbol = "[E]";

    public Event(Description description, Group group, Date date, TaskType type) {
        super(description, group, date, type);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (at: " + date.toString() + ")";
    }
}
