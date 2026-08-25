import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PDD {
    private enum Command { LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, ON }

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
            int spaceIndex = input.indexOf(' ');
            String command = spaceIndex == -1 ? input : input.substring(0, spaceIndex);
            String commandArgs = spaceIndex == -1 ? "" : input.substring(spaceIndex + 1).trim();
            try {
                Command cmd;
                try {
                    cmd = Command.valueOf(command.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new PDDException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
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
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    addTask(tasks, new Todo(commandArgs), storage, ui);
                    break;
                case DEADLINE: {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String[] parts = commandArgs.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! A deadline needs a description and a '/by' "
                                + "date, e.g. deadline return book /by 2019-10-15");
                    }
                    LocalDate by = parseDate(parts[1].trim());
                    addTask(tasks, new Deadline(parts[0].trim(), by), storage, ui);
                    break;
                }
                case EVENT: {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of an event cannot be empty.");
                    }
                    String[] parts = commandArgs.split(" /from ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! An event needs a description, a '/from' date and a "
                                + "'/to' time, e.g. event meeting /from 2019-10-15 /to 4pm");
                    }
                    String[] fromTo = parts[1].split(" /to ", 2);
                    if (fromTo.length < 2 || fromTo[0].trim().isEmpty() || fromTo[1].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! An event needs a description, a '/from' date and a "
                                + "'/to' time, e.g. event meeting /from 2019-10-15 /to 4pm");
                    }
                    LocalDate from = parseDate(fromTo[0].trim());
                    addTask(tasks, new Event(parts[0].trim(), from, fromTo[1].trim()), storage, ui);
                    break;
                }
                case ON: {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! Please provide a date, e.g. on 2019-10-15");
                    }
                    LocalDate date = parseDate(commandArgs);
                    ui.showTasksOn(date, tasks.getTasks());
                    break;
                }
                default:
                    throw new PDDException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (PDDException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    private static LocalDate parseDate(String text) throws PDDException {
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new PDDException("OOPS!!! Please enter the date in yyyy-MM-dd format, e.g. 2019-10-15.");
        }
    }

    private static void addTask(TaskList tasks, Task task, Storage storage, Ui ui) {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showAdded(task, tasks.size());
    }
}
