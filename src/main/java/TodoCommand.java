/** Adds an already-parsed {@link Todo} to the task list. */
public class TodoCommand extends Command {
    private final Todo todo;

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
