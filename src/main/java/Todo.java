public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /** Serializes this task to a single line in the save-file format. */
    public String toFileFormat() {
        return "T | " + getStatusValue() + " | " + description;
    }
}
