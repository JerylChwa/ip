package pdd.command;

import pdd.PDDException;
import pdd.storage.Storage;
import pdd.task.Task;
import pdd.task.TaskList;
import pdd.ui.Ui;

/** Marks the task at the given 1-based position as not done. */
public class UnmarkCommand extends Command {
    private final String taskNumberArg;

    /** Creates a command that will mark the task at the given 1-based position (as typed by the user) not done. */
    public UnmarkCommand(String taskNumberArg) {
        this.taskNumberArg = taskNumberArg;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PDDException {
        int index = tasks.toIndex(taskNumberArg);
        Task task = tasks.get(index);
        task.markAsNotDone();
        storage.save(tasks.getTasks());
        ui.showUnmarked(task);
    }
}
