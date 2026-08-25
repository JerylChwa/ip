public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /** Serializes this task to a single line in the save-file format. */
    public String toFileFormat() {
        return "D | " + getStatusValue() + " | " + description + " | " + by;
    }
}
