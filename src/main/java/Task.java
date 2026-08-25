import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    /** Shared display format for task dates, e.g. "Oct 15 2019". */
    protected static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /** Returns 1 if this task is done, 0 otherwise, for use in the save-file format. */
    protected int getStatusValue() {
        return isDone ? 1 : 0;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /** Serializes this task to a single line in the save-file format. */
    public abstract String toFileFormat();

    /** Returns whether this task occurs on the given date. Tasks without a date never match. */
    public boolean occursOn(LocalDate date) {
        return false;
    }
}
