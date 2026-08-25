package pdd.task;

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

    /** Wraps the given list of tasks, e.g. one just loaded from {@code Storage}. */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /** Returns the number of tasks in the list. */
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

    /** Returns the task at the given 0-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Appends the given task to the end of the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at the given 0-based index. */
    public Task delete(int index) {
        return tasks.remove(index);
    }
}
