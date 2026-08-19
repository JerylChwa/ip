import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PDD {
    private static final String LINE = "____________________________________________________________";

    private enum Command { LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT }

    public static void main(String[] args) {
        String banner = " ____  ____  ____  \n"
                + "|  _ \\|  _ \\|  _ \\ \n"
                + "| |_) | | | | | | |\n"
                + "|  __/| |_| | |_| |\n"
                + "|_|   |____/|____/ \n";

        System.out.println(LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm PDD.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }
            System.out.println(LINE);
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
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    break;
                case MARK: {
                    int index = parseTaskIndex(commandArgs, tasks.size());
                    Task task = tasks.get(index);
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                    break;
                }
                case UNMARK: {
                    int index = parseTaskIndex(commandArgs, tasks.size());
                    Task task = tasks.get(index);
                    task.markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                    break;
                }
                case DELETE: {
                    int index = parseTaskIndex(commandArgs, tasks.size());
                    Task removed = tasks.remove(index);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case TODO:
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    addTask(tasks, new Todo(commandArgs));
                    break;
                case DEADLINE: {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String[] parts = commandArgs.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! A deadline needs a description and a '/by' "
                                + "date/time, e.g. deadline return book /by Sunday");
                    }
                    addTask(tasks, new Deadline(parts[0].trim(), parts[1].trim()));
                    break;
                }
                case EVENT: {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of an event cannot be empty.");
                    }
                    String[] parts = commandArgs.split(" /from ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! An event needs a description, a '/from' and a "
                                + "'/to' time, e.g. event meeting /from Mon 2pm /to 4pm");
                    }
                    String[] fromTo = parts[1].split(" /to ", 2);
                    if (fromTo.length < 2 || fromTo[0].trim().isEmpty() || fromTo[1].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! An event needs a description, a '/from' and a "
                                + "'/to' time, e.g. event meeting /from Mon 2pm /to 4pm");
                    }
                    addTask(tasks, new Event(parts[0].trim(), fromTo[0].trim(), fromTo[1].trim()));
                    break;
                }
                default:
                    throw new PDDException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (PDDException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(LINE);
        }
        scanner.close();
    }

    private static int parseTaskIndex(String args, int taskCount) throws PDDException {
        int number;
        try {
            number = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            throw new PDDException("OOPS!!! Please provide a valid task number, e.g. mark 2");
        }
        if (number < 1 || number > taskCount) {
            throw new PDDException("OOPS!!! Task number " + number + " does not exist. "
                    + "You have " + taskCount + " task(s) in the list.");
        }
        return number - 1;
    }

    private static void addTask(List<Task> tasks, Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}
