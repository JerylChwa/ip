package pdd.command;

import pdd.PDDException;
import pdd.storage.Storage;
import pdd.task.Task;
import pdd.task.TaskList;
import pdd.ui.Ui;

/** Marks the task at the given 1-based position as done. */
public class MarkCommand extends Command {
    private final String taskNumberArg;

    public MarkCommand(String taskNumberArg) {
        this.taskNumberArg = taskNumberArg;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PDDException {
        int index = tasks.toIndex(taskNumberArg);
        Task task = tasks.get(index);
        task.markAsDone();
        storage.save(tasks.getTasks());
        ui.showMarked(task);
    }
}
