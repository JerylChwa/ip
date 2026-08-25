package pdd.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import pdd.task.Task;

/**
 * Handles all interaction with the user: printing the greeting/goodbye
 * messages, reading command lines from standard input, and printing the
 * responses to each command (task added/removed/listed, errors, etc).
 * Keeping all console I/O here means the rest of the program can work
 * with plain data and doesn't need to know how output is formatted.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    /** Prints the divider line shown before and after every command's response. */
    public void showLine() {
        System.out.println(LINE);
    }

    /** Prints the startup banner and greeting. */
    public void showWelcome() {
        String banner = " ____  ____  ____  \n"
                + "|  _ \\|  _ \\|  _ \\ \n"
                + "| |_) | | | | | | |\n"
                + "|  __/| |_| | |_| |\n"
                + "|_|   |____/|____/ \n";
        showLine();
        System.out.println(banner);
        System.out.println("Hello! I'm PDD.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /** Prints the goodbye message shown when the user exits. */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /** Reads one full line of user input. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Prints an error message, e.g. from a caught {@link PDDException}. */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Prints the full task list, numbered from 1. */
    public void showTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Prints the tasks that occur on the given date, numbered from 1. */
    public void showTasksOn(LocalDate date, List<Task> tasks) {
        System.out.println("Here are the tasks on " + date.format(Task.DISPLAY_DATE_FORMAT) + ":");
        int count = 0;
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                count++;
                System.out.println(count + "." + task);
            }
        }
    }

    /** Prints the confirmation shown after a task is marked done. */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /** Prints the confirmation shown after a task is marked not done. */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /** Prints the confirmation shown after a task is deleted. */
    public void showDeleted(Task task, int remainingCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingCount + " tasks in the list.");
    }

    /** Prints the confirmation shown after a task is added. */
    public void showAdded(Task task, int totalCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalCount + " tasks in the list.");
    }

    /** Prints the tasks matching a search keyword, numbered from 1. */
    public void showMatchingTasks(List<Task> matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
    }
}
