package pdd.command;

import pdd.storage.Storage;
import pdd.task.TaskList;
import pdd.task.Todo;
import pdd.ui.Ui;

/** Adds an already-parsed {@link Todo} to the task list. */
public class TodoCommand extends Command {
    private final Todo todo;

    /** Creates a command that will add the given already-parsed todo to the task list. */
    public TodoCommand(Todo todo) {
        this.todo = todo;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(todo);
        storage.save(tasks.getTasks());
        ui.showAdded(todo, tasks.size());
    }
}
