/**
 * Entry point for the PDD chatbot. Wires together the {@link Storage},
 * {@link TaskList} and {@link Ui} collaborators and runs the main
 * read-command / dispatch / respond loop.
 */
public class PDD {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public PDD(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            if (input.equals("bye")) {
                ui.showLine();
                ui.showGoodbye();
                ui.showLine();
                break;
            }
            ui.showLine();
            try {
                String commandWord = Parser.getCommandWord(input);
                String commandArgs = Parser.getCommandArgs(input);
                Parser.CommandType cmd = Parser.parseCommandType(commandWord);
                switch (cmd) {
                case LIST:
                    ui.showTaskList(tasks.getTasks());
                    break;
                case MARK: {
                    int index = tasks.toIndex(commandArgs);
                    Task task = tasks.get(index);
                    task.markAsDone();
                    storage.save(tasks.getTasks());
                    ui.showMarked(task);
                    break;
                }
                case UNMARK: {
                    int index = tasks.toIndex(commandArgs);
                    Task task = tasks.get(index);
                    task.markAsNotDone();
                    storage.save(tasks.getTasks());
                    ui.showUnmarked(task);
                    break;
                }
                case DELETE: {
                    int index = tasks.toIndex(commandArgs);
                    Task removed = tasks.delete(index);
                    storage.save(tasks.getTasks());
                    ui.showDeleted(removed, tasks.size());
                    break;
                }
                case TODO:
                    addTask(Parser.parseTodo(commandArgs));
                    break;
                case DEADLINE:
                    addTask(Parser.parseDeadline(commandArgs));
                    break;
                case EVENT:
                    addTask(Parser.parseEvent(commandArgs));
                    break;
                case ON:
                    ui.showTasksOn(Parser.parseOnDate(commandArgs), tasks.getTasks());
                    break;
                default:
                    throw new PDDException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (PDDException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    private void addTask(Task task) {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showAdded(task, tasks.size());
    }

    public static void main(String[] args) {
        new PDD("./data/pdd.txt").run();
    }
}
