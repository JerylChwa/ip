package pdd.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import pdd.PDDException;
import pdd.task.Deadline;
import pdd.task.Event;
import pdd.task.Task;
import pdd.task.Todo;

/**
 * Handles reading and writing the task list to a save file on disk, so
 * tasks persist across program runs.
 *
 * The save file uses one line per task, in the format:
 * <pre>
 * T | 1 | read book
 * D | 0 | return book | 2019-06-06
 * E | 0 | project meeting | 2019-08-06 | 2-4pm
 * </pre>
 * where the second column is 1 for a done task and 0 otherwise, and
 * deadline/event dates are stored in ISO {@code yyyy-MM-dd} format.
 */
public class Storage {
    private final Path filePath;

    /** Creates a storage backed by the save file at the given path. */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Loads the task list from disk. If the save file (or its containing
     * folder) doesn't exist yet, returns an empty list rather than
     * failing, since that's the expected state on a machine's first run.
     * Lines that don't match the expected format are skipped with a
     * warning, so a single corrupted line doesn't prevent the rest of the
     * file from loading.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                try {
                    tasks.add(parseTask(line));
                } catch (PDDException e) {
                    System.out.println("Skipping corrupted line in data file: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not read the data file: " + e.getMessage());
        }
        return tasks;
    }

    /** Saves the given task list to disk, overwriting any existing save file. */
    public void save(List<Task> tasks) {
        try {
            File parentDir = filePath.toAbsolutePath().getParent().toFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not save the data file: " + e.getMessage());
        }
    }

    private Task parseTask(String line) throws PDDException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new PDDException("not enough fields");
        }
        String type = parts[0];
        boolean isDone = parseStatus(parts[1]);
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                if (parts.length != 3) {
                    throw new PDDException("wrong number of fields for a todo");
                }
                task = new Todo(description);
                break;
            case "D":
                if (parts.length != 4) {
                    throw new PDDException("wrong number of fields for a deadline");
                }
                task = new Deadline(description, parseIsoDate(parts[3]));
                break;
            case "E":
                if (parts.length != 5) {
                    throw new PDDException("wrong number of fields for an event");
                }
                task = new Event(description, parseIsoDate(parts[3]), parts[4]);
                break;
            default:
                throw new PDDException("unknown task type: " + type);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private LocalDate parseIsoDate(String text) throws PDDException {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            throw new PDDException("invalid date: " + text);
        }
    }

    private boolean parseStatus(String status) throws PDDException {
        if (status.equals("1")) {
            return true;
        } else if (status.equals("0")) {
            return false;
        }
        throw new PDDException("invalid done-status flag: " + status);
    }
}
