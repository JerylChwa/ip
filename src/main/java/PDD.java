public class PDD {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        Storage storage = new Storage("./data/pdd.txt");
        TaskList tasks = new TaskList(storage.load());
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
                    addTask(tasks, Parser.parseTodo(commandArgs), storage, ui);
                    break;
                case DEADLINE:
                    addTask(tasks, Parser.parseDeadline(commandArgs), storage, ui);
                    break;
                case EVENT:
                    addTask(tasks, Parser.parseEvent(commandArgs), storage, ui);
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

    private static void addTask(TaskList tasks, Task task, Storage storage, Ui ui) {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showAdded(task, tasks.size());
    }
}
