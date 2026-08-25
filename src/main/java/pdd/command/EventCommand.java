package pdd.command;

import pdd.storage.Storage;
import pdd.task.Event;
import pdd.task.TaskList;
import pdd.ui.Ui;

/** Adds an already-parsed {@link Event} to the task list. */
public class EventCommand extends Command {
    private final Event event;

    public EventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(event);
        storage.save(tasks.getTasks());
        ui.showAdded(event, tasks.size());
    }
}
