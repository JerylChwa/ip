public abstract class Task {
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
}
