package pdd.task;

import java.util.ArrayList;
import java.util.List;

import pdd.PDDException;

/**
 * Wraps the in-memory list of tasks, and owns the operations that mutate
 * or query it (add, delete, mark/unmark, index lookup). Keeping this
 * separate from {@link Storage} (which only reads/writes disk) and from
 * {@link Ui} (which only prints) means the task-number bounds check lives
 * next to the data it's protecting.
 */
public class TaskList {
    private final List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    /** Returns the underlying tasks, e.g. for {@code Ui} to display or {@code Storage} to save. */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Converts a 1-based task number typed by the user into a validated
     * 0-based index into this list.
     */
    public int toIndex(String args) throws PDDException {
        int number;
        try {
            number = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            throw new PDDException("OOPS!!! Please provide a valid task number, e.g. mark 2");
        }
        if (number < 1 || number > tasks.size()) {
            throw new PDDException("OOPS!!! Task number " + number + " does not exist. "
                    + "You have " + tasks.size() + " task(s) in the list.");
        }
        return number - 1;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    /** Returns the tasks whose description contains the given keyword (case-insensitive). */
    public List<Task> findMatching(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }
}
