/** Marks the task at the given 1-based position as not done. */
public class UnmarkCommand extends Command {
    private final String taskNumberArg;

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
