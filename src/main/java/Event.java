import java.time.LocalDate;

public class Event extends Task {
    protected LocalDate from;
    protected String to;

    public Event(String description, LocalDate from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_DATE_FORMAT) + " to: " + to + ")";
    }

    /** Serializes this task to a single line in the save-file format. */
    public String toFileFormat() {
        return "E | " + getStatusValue() + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return from.equals(date);
    }
}
