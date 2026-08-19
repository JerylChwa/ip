import java.util.Scanner;

public class PDD {
    private static final String LINE = "____________________________________________________________";

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

        Task[] tasks = new Task[100];
        int taskCount = 0;
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
                if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (command.equals("mark")) {
                    int index = parseTaskIndex(commandArgs, taskCount);
                    Task task = tasks[index];
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                } else if (command.equals("unmark")) {
                    int index = parseTaskIndex(commandArgs, taskCount);
                    Task task = tasks[index];
                    task.markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                } else if (command.equals("todo")) {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    taskCount = addTask(tasks, taskCount, new Todo(commandArgs));
                } else if (command.equals("deadline")) {
                    if (commandArgs.isEmpty()) {
                        throw new PDDException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String[] parts = commandArgs.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new PDDException("OOPS!!! A deadline needs a description and a '/by' "
                                + "date/time, e.g. deadline return book /by Sunday");
                    }
                    taskCount = addTask(tasks, taskCount, new Deadline(parts[0].trim(), parts[1].trim()));
                } else if (command.equals("event")) {
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
                    taskCount = addTask(tasks, taskCount, new Event(parts[0].trim(), fromTo[0].trim(), fromTo[1].trim()));
                } else {
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

    private static int addTask(Task[] tasks, int taskCount, Task task) {
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        return taskCount;
    }
}
