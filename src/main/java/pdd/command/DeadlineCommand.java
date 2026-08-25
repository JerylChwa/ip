package pdd.command;

import pdd.storage.Storage;
import pdd.task.Deadline;
import pdd.task.TaskList;
import pdd.ui.Ui;

/** Adds an already-parsed {@link Deadline} to the task list. */
public class DeadlineCommand extends Command {
    private final Deadline deadline;

    public DeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(deadline);
        storage.save(tasks.getTasks());
        ui.showAdded(deadline, tasks.size());
    }
}
